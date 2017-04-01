package com.gobeike.radioapp.music;

import java.io.IOException;

import com.gobeike.radioapp.config.Constants;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service implements Runnable, IMusicAction
{
    public MusicService()
    {
    }
    
    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        return new MyBindler();
    }
    
    public class MyBindler extends Binder
    {
        
        public MusicService getService()
        {
            return MusicService.this;
        }
        
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        new Thread(this).start();
    }
    
    private String musicPath;
    
    private boolean playBtnState;
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        if (intent != null && intent.hasExtra(Constants.MUSIC_ACTION))
        {
            
            int action = intent.getIntExtra(Constants.MUSIC_ACTION, 0);
            switch (action)
            {
                
                case Constants.ACTION_playMusic:
                    playMusic();
                    break;
                case Constants.ACTION_pauseMusic:
                    pauseMusic();
                    break;
                case Constants.ACTION_seekbarToMusic:
                    seekbarToMusic(intent.getIntExtra(Constants.MUSIC_SEEKBAR_VALUE, 0));
                    
                    break;
                case Constants.ACTION_nextMusic:
                    musicPath = intent.getStringExtra(Constants.PLAY_BUTTON_URL);
                    
                    nextMusic(musicPath);
                    break;
                case Constants.ACTION_preMusic:
                    musicPath = intent.getStringExtra(Constants.PLAY_BUTTON_URL);
                    
                    preMusic(musicPath);
                    break;
                case Constants.ACTION_downloadMusic:
                    downloadMusic();
                    break;
                case Constants.ACTION_pointToplayMusic:
                    musicPath = intent.getStringExtra(Constants.PLAY_BUTTON_URL);
                    Log.e("PATH", musicPath);
                    playBtnState = intent.getBooleanExtra(Constants.PLAY_BUTTON_STATE, false);
                    if (playBtnState)
                    {
                        pointToplayMusic(musicPath);
                    }
                    else
                    {
                        pauseMusic();
                    }
                    break;
                default:
                    break;
            }
            
        }
        
        return Service.START_STICKY;
    }
    
    @Override
    public void run()
    {
        
    }
    
    private MediaPlayer mediaPlayer;
    
    private MusicInfo musicInfo;
    
    public MusicInfo getMusicInfo()
    {
        if (musicInfo != null && mediaPlayer != null)
        {
            musicInfo.currentLength = mediaPlayer.getCurrentPosition();
            
        }
        return musicInfo;
    }
    
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
        if (musicInfo == null)
            musicInfo = new MusicInfo();
        musicInfo.totalLength = mediaPlayer.getDuration();
        
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                /**
                 * 发广播播放下一个
                 */
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener()
        {
            @Override
            public void onSeekComplete(MediaPlayer mp)
            {
                
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
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try
            {
                mediaPlayer.setDataSource(url);
                currentPath = url;
                mediaPlayer.prepareAsync();// 本地文件使用
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                {
                    @Override
                    public void onPrepared(MediaPlayer mp)
                    {
                        
                        playMusic();
                    }
                });
                // mediaPlayer.setNextMediaPlayer();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        // playMusic();
    }
    
    @Override
    public void preMusic(String url)
    {
        if (mediaPlayer == null)
            return;
        if (url == null || url.length() == 0)
            return;
        
        if (currentPath == null || !currentPath.equals(url))
        {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try
            {
                mediaPlayer.setDataSource(url);
                currentPath = url;
                mediaPlayer.prepareAsync();// 本地文件使用
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
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try
            {
                mediaPlayer.setDataSource(path);
                currentPath = path;
                // mediaPlayer.prepare();// 本地文件使用
                mediaPlayer.prepareAsync();// 本地文件使用
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
        
    }
}
