package com.xinlingfamen.app.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.update.UmengUpdateAgent;
import com.xinlingfamen.app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengUpdateAgent.update(this);
        setContentView(R.layout.activity_main);
    }
}
