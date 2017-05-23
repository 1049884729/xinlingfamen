package com.gobeike.radioapp.music;

import java.io.File;
import java.io.IOException;

import com.gobeike.radioapp.ICallback;
import com.gobeike.radioapp.IMusicOperationAidl;
import com.gobeike.radioapp.utils.RadioFIleUtils;
import com.litesuits.android.log.Log;
import com.litesuits.common.io.FilenameUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import okhttp3.Call;

/**
 * Created by xuff on 17-4-8.
 */

public class MusicOperationImp extends IMusicOperationAidl.Stub
{
    private MediaPlayer mediaPlayer;
    
    private MusicInfo musicInfo;
    
    private MediaMetadataRetriever mediaMetadataRetriever;
    
    private Context mContext;
    
    public MusicOperationImp(Context context)
    {
        mediaPlayer = new MediaPlayer();
        musicInfo = new MusicInfo();
        mediaMetadataRetriever = new MediaMetadataRetriever();
        this.mContext = context;
    }
    
    @Override
    public MusicInfo getMusicInfo()
    {
        if (musicInfo != null && mediaPlayer != null)
        {
            musicInfo.currentLength = mediaPlayer.getCurrentPosition();
            
        }
        return musicInfo;
    }
    
    @Override
    public boolean isPlayer()
    {
        if (mediaPlayer != null)
        {
            return mediaPlayer.isPlaying();
        }
        return false;
    }
    
    @Override
    public void playMusic()
    {
        if (mediaPlayer == null)
            return;
        mediaPlayer.start();
        
        musicInfo.totalLength = mediaPlayer.getDuration();
        
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                /**
                 * 发广播播放下一个
                 */
                String nextPath = getNextMusicPath();
                if (nextPath != null)
                    pointToplayMusic(nextPath);
                
            }
        });
        
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener()
        {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra)
            {
                return false;
            }
        });
    }
    
    @Override
    public void pauseMusic()
    {
        if (mediaPlayer == null)
            return;
        mediaPlayer.pause();
        
    }
    
    @Override
    public void seekbarToMusic(int processbar)
    {
        
        if (mediaPlayer == null)
            return;
        mediaPlayer.seekTo(processbar);
        if (musicInfo != null && mediaPlayer != null)
        {
            musicInfo.currentLength = processbar;
        }
        
        // mediaPlayer.start();
    }
    
    @Override
    public void nextMusic(String url)
    {
        if (mediaPlayer == null)
            return;
        if (url == null || url.length() == 0)
            return;
        
        if (currentPath == null || !currentPath.equals(url))
        {
            setMediaPlayerPath(url);
            
        }
        // playMusic();
    }
    
    @Override
    public void preMusic(String path)
    {
        if (mediaPlayer == null)
            return;
        if (path == null || path.length() == 0)
            return;
        
        if (currentPath == null || !currentPath.equals(path))
        {
            setMediaPlayerPath(path);
            
        }
        // playMusic();
    }
    
    @Override
    public void downloadMusic()
    {
        
    }
    
    private String currentPath = null;
    
    @Override
    public void pointToplayMusic(String path)
    {
        if (mediaPlayer == null)
            return;
        if (path == null || path.length() == 0)
            return;
        
        if (currentPath == null || !currentPath.equals(path))
        {
            setMediaPlayerPath(path);
        }
        else
        {
            /**
             * 暂停后，继续播放音乐
             */
            playMusic();
            
        }
        
    }
    
    private void setMediaPlayerPath(String url)
    {
        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
        currentPath = url;
        
        if (getLocalMusicPath(url, musicInfo))
        {
            startPlayLocalMusic(musicInfo.localMediaPath);
            
        }
        else
        {
            OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(RadioFIleUtils.getCacheMusicDirPath(mContext), FilenameUtils.getName(url))
                {
                    @Override
                    public void inProgress(float progress, long total, int id)
                    {
                        super.inProgress(progress, total, id);
                        Log.e("Music", progress);
                        
                    }
                    
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Log.e("Music:onError", e);
                        
                    }
                    
                    @Override
                    public void onResponse(File response, int id)
                    {
                        Log.e("Music", "onResponse");
                        startPlayLocalMusic(response.getPath());
                        
                    }
                });
        }
        
    }
    
    private void startPlayLocalMusic(String path)
    {
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try
        {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();// 本地文件使用
            getMusicDetailInfo(path);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {
                @Override
                public void onPrepared(MediaPlayer mp)
                {
                    
                    playMusic();
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private RemoteCallbackList<ICallback> mCallbacks = new RemoteCallbackList<>();
    
    @Override
    public void registerCallback(ICallback ic)
        throws RemoteException
    
    {
        if (ic != null)
            mCallbacks.register(ic);
    }
    
    @Override
    public void unRegisterCallback(ICallback ic)
        throws RemoteException
    {
        if (ic != null)
            mCallbacks.unregister(ic);
    }
    
    /**
     *
     * @return 获取下一首音乐播放的路径
     */
    private String getNextMusicPath()
    {
        int n = mCallbacks.beginBroadcast();
        String musicPath = null;
        try
        {
            for (int i = 0; i < n; i++)
            {
                musicPath = mCallbacks.getBroadcastItem(i).getNextMusicPath();
            }
        }
        catch (RemoteException e)
        {
        }
        mCallbacks.finishBroadcast();
        return musicPath;
    }
    
    /**
     * 判断音乐是否存在本地,如果存在则设置MusicInfo对象的路径,否则返回false
     * 
     * @param url
     * @param musicInfo
     * @return
     */
    private boolean getLocalMusicPath(String url, MusicInfo musicInfo)
    {
        String fileName = FilenameUtils.getName(url);
        File tempFile = new File(RadioFIleUtils.getCacheMusicDirPath(mContext) + fileName);
        if (tempFile.exists())
        {
            musicInfo.localMediaPath = tempFile.getPath();
            return true;
        }
        else
        {
            tempFile = new File(RadioFIleUtils.getDownloadExternalPath() + fileName);
            if (tempFile.exists())
            {
                musicInfo.localMediaPath = tempFile.getPath();
                return true;
            }
        }
        return false;
    }
    
    private void getMusicDetailInfo(String path)
    {
        mediaMetadataRetriever.setDataSource(path);
        String value = null;
        value = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        musicInfo.KEY_ARTIST = value;
        
        value = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        musicInfo.KEY_ALBUM = value;
        value = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        musicInfo.KEY_TITLE = value;
        
        try
        {
            byte[] embedPic = mediaMetadataRetriever.getEmbeddedPicture(); // 得到字节型数据
            musicInfo.bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length); // 转换为图片
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        
    }
    
    /**
     * @Description 获取专辑封面
     * @param filePath 文件路径，like XXX/XXX/XX.mp3
     * @return 专辑封面bitmap
     */
    public Bitmap createAlbumArt(String filePath)
    {
        Bitmap bitmap = null;
        // 能够获取多媒体文件元数据的类
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try
        {
            retriever.setDataSource(filePath); // 设置数据源
            byte[] embedPic = retriever.getEmbeddedPicture(); // 得到字节型数据
            bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length); // 转换为图片
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                retriever.release();
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }
}
