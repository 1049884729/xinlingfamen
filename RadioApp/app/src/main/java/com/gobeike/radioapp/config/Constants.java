package com.gobeike.radioapp.config;

import android.os.Environment;

/**
 * Created by xuff on 17-3-17.
 */

public class Constants
{
    
    public static String PLAY_BUTTON_URL = "playMusic_url";
    
    /**
     * 播放按钮的状态
     */
    public static String PLAY_BUTTON_STATE = "playMusic_state";
    
    public static String[] music_List =
        {"http://192.168.1.15:8080/gaoshanliushui.mp3", "http://192.168.1.15:8080/pipayu.mp3",
            "http://192.168.1.15:8080/yunshui.mp3", "http://192.168.1.15:8080/yuzhouchangwan.mp3"};
    
    public static String[] music_List_local = {Environment.getExternalStorageDirectory() + "/Music/test.mp3"};
    
    public static String MUSIC_SEEKBAR_VALUE = "seekbarValue";
    
    public static String MUSIC_ACTION = "Music_Action";
    
    public final static int ACTION_playMusic = 1;
    
    public final static int ACTION_pauseMusic = 2;
    
    public final static int ACTION_seekbarToMusic = 3;
    
    public final static int ACTION_nextMusic = 4;
    
    public final static int ACTION_preMusic = 5;
    
    public final static int ACTION_downloadMusic = 6;
    
    public final static int ACTION_pointToplayMusic = 7;
    


}
