package com.xinlingfamen.app.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinlingfamen.app.BaseActivity;
import com.xinlingfamen.app.R;

/**
 * Created by xuff on 2016/9/12.
 */
public class ContentTxtActivity extends BaseActivity
{
    private TextView titleView, contentView;
    
    public static final String KEY_TITLE = "keyTitle";
    
    public static final String KEY_CONTENT = "keyContent";
    
    public static final String KEY_URITYPE = "keyUriType";// 请求类型
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_txt);
        setActionBarTitleBackup("");
        titleView = (TextView)findViewById(R.id.tv_title);
        contentView = (TextView)findViewById(R.id.tv_content);
        setData(getIntent());
        
    }
    
    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setData(intent);
    }
    
    private void setData(Intent intent) {
        if (intent.hasExtra(KEY_TITLE)) {
            titleView.setText(intent.getStringExtra(KEY_TITLE));


        }

        if (intent.hasExtra(KEY_CONTENT)) {
            contentView.setText(intent.getStringExtra(KEY_CONTENT));
        } else if (intent.hasExtra(KEY_URITYPE)) {
            titleView.setText(intent.getStringExtra(KEY_URITYPE));
        }

    }
}
