package com.xinlingfamen.app.core.welcome;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(){
          @Override
          public void handleMessage(Message msg) {
              super.handleMessage(msg);
              Intent intent=new Intent(getPackageName()+".main");
              startActivity(intent);
              finish();
          }
      }.sendEmptyMessageDelayed(0,2000);
    }

    /**
     * 进入启动页后不能返回
     */
    @Override
    public void onBackPressed() {
    }
}
