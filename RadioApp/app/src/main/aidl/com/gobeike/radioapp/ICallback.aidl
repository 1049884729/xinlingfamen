// ICallback.aidl
package com.gobeike.radioapp;
import com.gobeike.radioapp.music.MusicInfo;

// Declare any non-default types here with import statements

interface ICallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String getNextMusicPath();


   void showCacheProgress(String desc,float percent);
}
