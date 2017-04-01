package com.gobeike.radioapp;

import com.gobeike.radioapp.config.Constants;
import com.gobeike.radioapp.view.MusicPlayView;

import android.app.Application;
import android.content.IntentFilter;

/**
 * Created by xuff on 17-4-1.
 */

public class RadipApplication extends Application
{
    private MusicPlayView.ReceiverFromMusicServer musicServerBroad;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter(Constants.MUSIC_ACTION);
        registerReceiver(musicServerBroad, intentFilter);
    }
    
    @Override
    public void onTerminate()
    {
        super.onTerminate();
        unregisterReceiver(musicServerBroad);
    }
}
