package com.xinlingfamen.app;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/8/17.
 */
public class XlfmApplication extends Application {
    private Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        uemngInit();
    }
    private void uemngInit(){
        MobclickAgent.setScenarioType( context, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.enableEncrypt(true);//6.0.0版本及以后
    }
}
