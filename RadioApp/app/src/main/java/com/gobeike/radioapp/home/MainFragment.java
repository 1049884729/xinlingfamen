package com.gobeike.radioapp.home;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.gobeike.radioapp.R;
import com.gobeike.radioapp.config.Constants;
import com.gobeike.radioapp.config.utils.IntentUtils;
import com.gobeike.radioapp.music.MusicService;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Random;


/**
 * Created by xuff on 17-3-17.
 * 主要功能模块的Fragment
 */

public class MainFragment  extends Fragment  {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public MainFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    Button playerBtn;
    private SeekBar progress;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        playerBtn= (Button) rootView.findViewById(R.id.btn_play);
        progress= (SeekBar) rootView.findViewById(R.id.progress);
        playerBtn.setTag(false);
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent intent= IntentUtils.getExplicitIapIntent(getActivity(),getActivity().getPackageName()+".music.service");
                intent.putExtra(Constants.MUSIC_ACTION, Constants.ACTION_seekbarToMusic);
                intent.putExtra(Constants.MUSIC_SEEKBAR_VALUE, seekBar.getProgress());
                getActivity().startService(intent);

            }
        });
        playerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= IntentUtils.getExplicitIapIntent(getActivity(),getActivity().getPackageName()+".music.service");
//                String pathUrl=Constants.music_List[new Random().nextInt(4)];
                String pathLocal=Environment.getExternalStorageDirectory()+"/Music/test.mp3";

                if (!Boolean.parseBoolean(playerBtn.getTag().toString())){
                    handler.sendEmptyMessage(CONTINUE);

                }else {
                    handler.sendEmptyMessage(CLEAR);

                }
                intent.putExtra(Constants.PLAY_BUTTON_URL, pathLocal);
                intent.putExtra(Constants.MUSIC_ACTION, Constants.ACTION_pointToplayMusic);
                playerBtn.setTag(!Boolean.parseBoolean(playerBtn.getTag().toString()));
                intent.putExtra(Constants.PLAY_BUTTON_STATE, (Boolean) playerBtn.getTag());
                getActivity().startService(intent);
            }
        });

        return rootView;
    }
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBindler=  ((MusicService.MyBindler)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private MusicService myBindler;


    @Override
    public void onResume() {
        super.onResume();
        getActivity().bindService(new Intent(IntentUtils.getExplicitIapIntent(getActivity(),getActivity().getPackageName()+".music.service")),connection, Context.BIND_AUTO_CREATE);
        handler.sendEmptyMessage(CONTINUE);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unbindService(connection);

    }

    private static final int CONTINUE=0x11;
    private static final int CLEAR=0x33;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CLEAR:
                    handler.removeMessages(CONTINUE);
                    myBindler.pauseMusic();
                    break;
                case CONTINUE:
                    if (myBindler!=null&& myBindler.getMusicInfo()!=null){
                        progress.setMax(myBindler.getMusicInfo().totalLength);
                        progress.setProgress(myBindler.getMusicInfo().currentLength);

                    }
                    handler.sendEmptyMessageDelayed(CONTINUE,1000);

                    break;
            }
        }
    };
    //    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
