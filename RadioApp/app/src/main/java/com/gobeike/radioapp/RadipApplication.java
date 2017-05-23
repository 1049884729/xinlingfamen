package com.gobeike.radioapp;

import com.gobeike.radioapp.config.Constants;
import com.gobeike.radioapp.view.MusicPlayView;
import com.zhy.http.okhttp.OkHttpUtils;

import android.app.Application;
import android.content.IntentFilter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by xuff on 17-4-1.
 */

public class RadipApplication extends Application
{
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            // .addInterceptor(new LoggerInterceptor("TAG"))
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            // 其他配置
            .build();
        
        OkHttpUtils.initClient(okHttpClient);
    }
    
    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }
}
