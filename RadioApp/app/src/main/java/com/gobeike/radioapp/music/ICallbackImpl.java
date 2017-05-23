package com.gobeike.radioapp.music;

import android.os.RemoteException;

import com.gobeike.radioapp.ICallback;

/**
 * Created by xuff on 17-4-15.
 */

public class ICallbackImpl extends ICallback.Stub {
    @Override
    public String getNextMusicPath() throws RemoteException {
        return null;
    }

    @Override
    public void showCacheProgress(String desc, float percent) throws RemoteException {

    }
}
