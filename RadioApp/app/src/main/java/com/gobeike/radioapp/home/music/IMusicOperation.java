package com.gobeike.radioapp.home.music;

/**
 * Created by xuff on 17-3-27.
 */

public interface IMusicOperation {

    void playMusic(String url,boolean stateBtn);

    void onSeekBarChange(int progress);
}
