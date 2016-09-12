package com.xinlingfamen.app.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.j256.ormlite.stmt.query.In;
import com.xinlingfamen.app.BaseActivity;
import com.xinlingfamen.app.R;

/**
 * Created by xuff on 2016/9/12.
 */
public class ContentTxtActivity extends AppCompatActivity {
    private TextView titleView,contentView;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
       setContentView(R.layout.activity_content_txt);
        titleView= (TextView) findViewById(R.id.tv_title);
        contentView= (TextView) findViewById(R.id.tv_content);
        setData(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setData(intent);
    }
    private void setData(Intent intent){

    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
