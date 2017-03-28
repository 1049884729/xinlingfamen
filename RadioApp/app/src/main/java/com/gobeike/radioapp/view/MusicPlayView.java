package com.gobeike.radioapp.view;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gobeike.radioapp.R;
import com.gobeike.radioapp.config.utils.IntentUtils;
import com.gobeike.radioapp.home.music.IMusicOperation;
import com.gobeike.radioapp.home.music.MusicOperation;
import com.gobeike.radioapp.music.MusicService;

import java.util.SimpleTimeZone;

import hirondelle.date4j.DateTime;

/**
 * Created by xuff on 17-3-27.
 */

public class MusicPlayView extends FrameLayout {
    public MusicPlayView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MusicPlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public MusicPlayView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MusicPlayView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);

    }

    private IMusicOperation iMusicOperation;

    private View childView;
    private TextView currentTime, totalTime;
    Button playerBtn;
    private SeekBar progress;

    private void init(Context context) {
        iMusicOperation = new MusicOperation(context);

        childView = LayoutInflater.from(context).inflate(R.layout.view_music_play_common, null);
        playerBtn = (Button) childView.findViewById(R.id.btn_play);
        progress = (SeekBar) childView.findViewById(R.id.progress);
        currentTime = (TextView) childView.findViewById(R.id.current_time);
        totalTime = (TextView) childView.findViewById(R.id.total_time);
        playerBtn.setTag(false);
        addView(childView);
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                iMusicOperation.onSeekBarChange(seekBar.getProgress());

            }
        });
        playerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String pathUrl=Constants.music_List[new Random().nextInt(4)];
                String pathLocal = Environment.getExternalStorageDirectory() + "/Music/test.mp3";
                if (!Boolean.parseBoolean(playerBtn.getTag().toString())) {
                    handler.sendEmptyMessage(CONTINUE);

                } else {
                    handler.sendEmptyMessage(CLEAR);

                }
                playerBtn.setTag(!Boolean.parseBoolean(playerBtn.getTag().toString()));

                iMusicOperation.playMusic(pathLocal, Boolean.parseBoolean(playerBtn.getTag().toString()));
            }
        });
    }


    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBindler = ((MusicService.MyBindler) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private MusicService myBindler;

    public void onResume() {
        getContext().bindService(new Intent(IntentUtils.getExplicitIapIntent(getContext(), getContext().getPackageName() + ".music.service")), connection, Context.BIND_AUTO_CREATE);
        handler.sendEmptyMessage(CONTINUE);
    }

    public void onPause() {
        getContext().unbindService(connection);

    }

    private static final int CONTINUE = 0x11;
    private static final int CLEAR = 0x33;
    private DateTime tempTime = null;
    private String timeFormat = "hh:mm:ss";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CLEAR:
                    handler.removeMessages(CONTINUE);
                    myBindler.pauseMusic();
                    break;
                case CONTINUE:
                    if (myBindler != null && myBindler.getMusicInfo() != null) {
                        timeFormat = getFormat(myBindler.getMusicInfo().totalLength);
                        tempTime = DateTime.forInstant(myBindler.getMusicInfo().totalLength, new SimpleTimeZone(0, "GMT"));
                        totalTime.setText(tempTime.format(timeFormat));
                        timeFormat = getFormat(myBindler.getMusicInfo().currentLength);
                        tempTime = DateTime.forInstant(myBindler.getMusicInfo().currentLength, new SimpleTimeZone(0, "GMT"));
                        currentTime.setText(tempTime.format(timeFormat));
                        progress.setMax(myBindler.getMusicInfo().totalLength);
                        progress.setProgress(myBindler.getMusicInfo().currentLength);

                    }
                    handler.sendEmptyMessageDelayed(CONTINUE, 1000);

                    break;
            }
        }
    };

    private String getFormat(int time) {
        String temp = null;
        if (time < 3600000) {
            temp = "mm:ss";
        } else {
            temp = "hh:mm:ss";

        }
        return temp;
    }

}
