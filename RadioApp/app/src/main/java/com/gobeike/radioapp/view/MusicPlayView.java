package com.gobeike.radioapp.view;

import java.util.List;
import java.util.SimpleTimeZone;

import com.gobeike.radioapp.R;
import com.gobeike.radioapp.config.Constants;
import com.gobeike.radioapp.config.utils.IntentUtils;
import com.gobeike.radioapp.home.music.IMusicOperation;
import com.gobeike.radioapp.home.music.MusicOperation;
import com.gobeike.radioapp.music.MusicService;
import com.gobeike.radioapp.music.PlayerMusicList;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import hirondelle.date4j.DateTime;

/**
 * Created by xuff on 17-3-27.
 */

public class MusicPlayView extends FrameLayout
{
    
    private final String TAG = getClass().getSimpleName();
    
    public MusicPlayView(@NonNull Context context)
    {
        super(context);
        init(context);
    }
    
    public MusicPlayView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
        
    }
    
    public MusicPlayView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
        
    }
    
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MusicPlayView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr,
        @StyleRes int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        
        init(context);
        
    }
    
    private IMusicOperation iMusicOperation;
    
    private Context context;
    
    private View childView;
    
    private TextView currentTime, totalTime;
    
    private ImageButton playerBtn, preBtn, nextBtn;
    
    private SeekBar progress;
    
    /**
     * 音乐播放列表
     */
    private PlayerMusicList playerMusicList;
    
    private void init(final Context context)
    {
        iMusicOperation = new MusicOperation(context);
        playerMusicList = new PlayerMusicList();
        this.context = context;
        childView = LayoutInflater.from(context).inflate(R.layout.view_music_play_common, null);
        playerBtn = (ImageButton)childView.findViewById(R.id.btn_play);
        preBtn = (ImageButton)childView.findViewById(R.id.btn_pre);
        nextBtn = (ImageButton)childView.findViewById(R.id.btn_next);
        progress = (SeekBar)childView.findViewById(R.id.progress);
        currentTime = (TextView)childView.findViewById(R.id.current_time);
        totalTime = (TextView)childView.findViewById(R.id.total_time);
        playerBtn.setTag(false);
        addView(childView);
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                
            }
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                iMusicOperation.onSeekBarChange(seekBar.getProgress());
                handler.sendEmptyMessage(UPDATE_VIEW);
                
            }
        });
        playerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                playerBtn.setTag(!Boolean.parseBoolean(playerBtn.getTag().toString()));
                updatePlayBtn();
                iMusicOperation.playMusic(playerMusicList.getCurrentPlayMusic(),
                    Boolean.parseBoolean(playerBtn.getTag().toString()));
                
            }
        });
        
        nextBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
                Toast.makeText(context, "next", Toast.LENGTH_SHORT).show();
                iMusicOperation.preMusic(playerMusicList.getNextPlayMusic());
                
            }
        });
        preBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                iMusicOperation.preMusic(playerMusicList.getPrePlayMusic());
                Toast.makeText(context, "pre", Toast.LENGTH_SHORT).show();
                
            }
        });
    }
    
    private void updatePlayBtn()
    {
        if (playerBtn != null)
        {
            
            boolean stateBtn = Boolean.parseBoolean(playerBtn.getTag().toString());
            if (stateBtn)
            {
                playerBtn.setImageResource(android.R.drawable.ic_media_pause);
            }
            else
            {
                playerBtn.setImageResource(android.R.drawable.ic_media_play);
                
            }
            
        }
    }
    
    private void playMusicState()
    {
        if (!Boolean.parseBoolean(playerBtn.getTag().toString()))
        {
            handler.sendEmptyMessage(CONTINUE);
            
        }
        else
        {
            handler.sendEmptyMessage(CLEAR);
            
        }
        updatePlayBtn();
        
    }
    
    private ServiceConnection connection = new ServiceConnection()
    {
        
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            myBindler = ((MusicService.MyBindler)service).getService();
        }
        
        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            
        }
    };
    
    private MusicService myBindler;
    
    /**
     * 设置音乐播放清单
     */
    public void setPlayList(List<String> musicPlayList)
    {
        playerMusicList.setPlayMusicLists(musicPlayList);
        playerMusicList.setPlayListRecycle(true);
        
    }
    
    public void onResume()
    {
        getContext().bindService(
            new Intent(
                IntentUtils.getExplicitIapIntent(getContext(), getContext().getPackageName() + ".music.service")),
            connection,
            Context.BIND_AUTO_CREATE);
        handler.sendEmptyMessage(CONTINUE);
        
    }
    
    public void onPause()
    {
        getContext().unbindService(connection);
        
    }
    
    private static final int CONTINUE = 0x11;
    
    private static final int CLEAR = 0x33;
    
    private static final int UPDATE_VIEW = 0x44;
    
    private DateTime tempTime = null;
    
    private String timeFormat = "hh:mm:ss";
    
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case CLEAR:
                    handler.removeMessages(CONTINUE);
                    myBindler.pauseMusic();
                    break;
                case CONTINUE:
                    updateView();
                    handler.sendEmptyMessageDelayed(CONTINUE, 1000);
                    
                    break;
                case UPDATE_VIEW:
                    updateView();
                    break;
            }
            
        }
        
        private void updateView()
        {
            
            if (myBindler != null && myBindler.getMusicInfo() != null)
            {
                timeFormat = getFormat(myBindler.getMusicInfo().totalLength);
                tempTime = DateTime.forInstant(myBindler.getMusicInfo().totalLength, new SimpleTimeZone(0, "GMT"));
                totalTime.setText(tempTime.format(timeFormat));
                timeFormat = getFormat(myBindler.getMusicInfo().currentLength);
                tempTime = DateTime.forInstant(myBindler.getMusicInfo().currentLength, new SimpleTimeZone(0, "GMT"));
                currentTime.setText(tempTime.format(timeFormat));
                progress.setMax(myBindler.getMusicInfo().totalLength);
                progress.setProgress(myBindler.getMusicInfo().currentLength);
                
            }
        }
        
    };
    
    private String getFormat(int time)
    {
        String temp = null;
        if (time < 3600000)
        {
            temp = "mm:ss";
        }
        else
        {
            temp = "hh:mm:ss";
            
        }
        return temp;
    }
    
    /**
     * 负责接收l来自MusicService中的操作变化
     */
    public class ReceiverFromMusicServer extends BroadcastReceiver
    {
        
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getAction().equals(Constants.ReceiverFromMusicAction))
            {
                int acionValue = intent.getIntExtra(Constants.MUSIC_ACTION, 0);
                switch (acionValue)
                {
                    case Constants.ACTION_nextMusic:
                        // playMusic(playerMusicList.getNextPlayMusic());
                        break;
                }
                
            }
        }
    }
    
}
