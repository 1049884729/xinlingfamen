// IMusicOperation.aidl
package com.gobeike.radioapp;

// Declare any non-default types here with import statements
import com.gobeike.radioapp.ICallback;
import com.gobeike.radioapp.music.MusicInfo;

interface IMusicOperationAidl {

   MusicInfo getMusicInfo();

    boolean isPlayer();
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
        void playMusic();

      void pauseMusic();


      /**
       * 进度条拖动，快进或者快退音乐
       *
       * @param processbar
       */
      void seekbarToMusic(int processbar);

      void nextMusic(String url);

      void preMusic(String url);

      void downloadMusic();

      /**
       * 从列表中指定播放源
       *
       * @param url
       */
      void pointToplayMusic(String url);

    void registerCallback(ICallback ic);
    void unRegisterCallback(ICallback ic);
}
