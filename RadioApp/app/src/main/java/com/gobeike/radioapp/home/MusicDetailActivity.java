package com.gobeike.radioapp.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.gobeike.radioapp.R;
import com.gobeike.radioapp.view.MusicPlayView;

public class MusicDetailActivity extends AppCompatActivity {

    private MusicPlayView musicPlayView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
      imageView= (ImageView) findViewById(R.id.music_img);
        musicPlayView= (MusicPlayView) findViewById(R.id.musicPlayView);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        musicPlayView.onResume();
//        if (musicPlayView.getMyBindler().getMusicInfo().bitmap!=null)
//    imageView.setImageBitmap(musicPlayView.getMyBindler().getMusicInfo().bitmap);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        musicPlayView.onPause();

    }
}
