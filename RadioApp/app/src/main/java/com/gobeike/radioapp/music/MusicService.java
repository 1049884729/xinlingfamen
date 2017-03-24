package com.gobeike.radioapp.music;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.gobeike.radioapp.config.Constants;

import java.io.IOException;

public class MusicService extends Service implements Runnable {
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBindler();
    }

    public class MyBindler extends Binder {

        public MusicService getService(){
            return MusicService.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        new Thread(this).start();
    }


    private String musicPath;
    private boolean playBtnState;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            musicPath = intent.getStringExtra(Constants.PLAY_BUTTON_URL);
            Log.e("PATH", musicPath);

            playBtnState = intent.getBooleanExtra(Constants.PLAY_BUTTON_STATE, false);
            if (playBtnState) {
                startPlayer(musicPath);
            }
        }

        return Service.START_STICKY;
    }

    @Override
    public void run() {

    }

    private MediaPlayer mediaPlayer;

    private MusicInfo musicInfo;

    public MusicInfo getMusicInfo() {
        if (musicInfo!=null&&mediaPlayer!=null){
            musicInfo.currentLength = mediaPlayer.getCurrentPosition();
            musicInfo.totalLength = mediaPlayer.getDuration();
        }
        return musicInfo;
    }
    public boolean isPlayer() {
        if (mediaPlayer!=null){
           return mediaPlayer.isPlaying();
        }
        return false;
    }
    /**
     * 播放音乐
     *
     * @param path
     */
    public void startPlayer(String path) {
        if (mediaPlayer == null) return;
        if (path == null || path.length() == 0) return;
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();//本地文件使用
            mediaPlayer.start();
            if (musicInfo==null)
            musicInfo = new MusicInfo();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });
            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {

                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPlayer() {
        if (mediaPlayer == null) return;
        mediaPlayer.stop();
        mediaPlayer.release();


    }
}
