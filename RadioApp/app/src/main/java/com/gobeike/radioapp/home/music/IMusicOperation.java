package com.gobeike.radioapp.home.music;

/**
 * Created by xuff on 17-3-27. 界面有关的音乐操作
 */

public interface IMusicOperation
{
    
    void playMusic(String url, boolean stateBtn);
    
    void onSeekBarChange(int progress);


    
    void pauseMusic();
    
    /**
     * 界面的下一曲操作
     * 
     * @param url
     */
    void nextMusic(String url);
    
    /**
     * 界面的上一曲操作
     * 
     * @param url
     */
    void preMusic(String url);
}
