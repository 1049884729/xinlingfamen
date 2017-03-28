package com.gobeike.radioapp.home.music;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.gobeike.radioapp.config.Constants;
import com.gobeike.radioapp.config.utils.IntentUtils;

/**
 * Created by xuff on 17-3-27.
 */

public class MusicOperation implements IMusicOperation {

    private Context context;

    public MusicOperation(Context context) {
        this.context = context;
    }

    @Override
    public void playMusic(String url,boolean stateBtn) {
        Intent intent =getmIntent();
        intent.putExtra(Constants.PLAY_BUTTON_URL, url);
        intent.putExtra(Constants.MUSIC_ACTION, Constants.ACTION_pointToplayMusic);
        intent.putExtra(Constants.PLAY_BUTTON_STATE, stateBtn);
        context.startService(intent);
    }

    @Override
    public void onSeekBarChange(int progress) {
        Intent intent =getmIntent();
        intent.putExtra(Constants.MUSIC_ACTION, Constants.ACTION_seekbarToMusic);
        intent.putExtra(Constants.MUSIC_SEEKBAR_VALUE, progress);
        context.startService(intent);
    }



    private Intent getmIntent() {
        return IntentUtils.getExplicitIapIntent(context, context.getPackageName() + ".music.service");
    }
}
