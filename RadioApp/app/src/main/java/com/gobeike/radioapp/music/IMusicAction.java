package com.gobeike.radioapp.music;

/**
 * Created by xuff on 17-3-26.
 * 播放音乐得各种动作操作
 */

public interface IMusicAction {

    void playMusic();
    void pauseMusic();

    /**
     *进度条拖动，快进或者快退音乐
     * @param processbar
     */
    void seekbarToMusic(int processbar);

    void nextMusic();
    void preMusic();

    void downloadMusic();


    /**
     * 从列表中指定播放源
     * @param url
     */
    void pointToplayMusic(String url);
}
