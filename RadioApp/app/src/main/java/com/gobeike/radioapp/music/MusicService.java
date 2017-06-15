package com.gobeike.radioapp.music;

import com.gobeike.radioapp.config.Constants;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service implements Runnable
{
    public MusicService()
    {
    }
    
    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        return musicOperationImp;
    }
    
    private MusicOperationImp musicOperationImp = null;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        musicOperationImp = new MusicOperationImp(getApplicationContext());
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
                    musicOperationImp.playMusic();
                    break;
                case Constants.ACTION_pauseMusic:
                    musicOperationImp.pauseMusic();
                    break;
                case Constants.ACTION_seekbarToMusic:
                    musicOperationImp.seekbarToMusic(intent.getIntExtra(Constants.MUSIC_SEEKBAR_VALUE, 0));
                    
                    break;
                case Constants.ACTION_nextMusic:
                    musicPath = intent.getStringExtra(Constants.PLAY_BUTTON_URL);
                    
                    musicOperationImp.nextMusic(musicPath);
                    break;
                case Constants.ACTION_preMusic:
                    musicPath = intent.getStringExtra(Constants.PLAY_BUTTON_URL);
                    
                    musicOperationImp.preMusic(musicPath);
                    break;
                case Constants.ACTION_downloadMusic:
                    musicOperationImp.downloadMusic();
                    break;
                case Constants.ACTION_pointToplayMusic:
                    musicPath = intent.getStringExtra(Constants.PLAY_BUTTON_URL);
                    Log.e("PATH", musicPath+"");
                    playBtnState = intent.getBooleanExtra(Constants.PLAY_BUTTON_STATE, false);
                    if (playBtnState)
                    {
                        musicOperationImp.pointToplayMusic(musicPath);
                    }
                    else
                    {
                        musicOperationImp.pauseMusic();
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
    
}
