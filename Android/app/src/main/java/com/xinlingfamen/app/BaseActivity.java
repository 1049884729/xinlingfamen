package com.xinlingfamen.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.umeng.message.PushAgent;

import java.util.List;

/**
 * BaseActivity ,Base class
 */
public class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        mContext=this;
        PushAgent.getInstance(mContext).onAppStart();

    }

    protected FragmentManager fragmentManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragmentManager != null) {
            List<Fragment> fragmentList = fragmentManager.getFragments();
            if (fragmentList != null && fragmentList.size() > 0) {
                for (Fragment fragment : fragmentList) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    /**
     * 使用该方法，布局文件必须引用
     * <include layout="@layout/toolbar_title_only" android:layout_width="match_parent" android:layout_height=
     * "wrap_content"/> 设置返回按钮
     */
    protected void setActionBarTitleBackup(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView titleView = (TextView) findViewById(R.id.tv_title);
        if (titleView != null)
            titleView.setText(title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }



    /**
     * 使用该方法，布局文件必须引用
     * <include layout="@layout/toolbar_title_only" android:layout_width="match_parent" android:layout_height=
     * "wrap_content"/>
     */
    protected void setActionBarTitleNoBackup(String textTitle) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView titleView = (TextView) findViewById(R.id.tv_title);
        if (titleView != null)
            titleView.setText(textTitle);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
//
        super.onBackPressed();
        super.onSupportNavigateUp();
        return true;
    }

}
