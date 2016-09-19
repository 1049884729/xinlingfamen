package com.xinlingfamen.app;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.xinlingfamen.app.config.Constants;
import com.xinlingfamen.app.db.OrmliteHelper;
import com.xinlingfamen.app.utils.FilesUtils;
import com.xinlingfamen.app.utils.SharePrefenceUtils;

import java.util.UUID;

/**
 * Created by Administrator on 2016/8/17.
 */
public class XlfmApplication extends Application
{
    private Context context;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
        FilesUtils.createFolders(FilesUtils.FOLDER_NAME);
        FilesUtils.createFolders(FilesUtils.DOWNLOAD_MP3);
        FilesUtils.createFolders(FilesUtils.DOWNLOAD_VIDEO);
        FilesUtils.createFolders(FilesUtils.DOWNLOAD_OTHER);
        FilesUtils.createFolders(FilesUtils.DOWNLOAD_FILE);
        uemngInit();
        initDatabase();
    }
    
    private void uemngInit()
    {
        /**
         * 使用人数统计
         */
        String userId=SharePrefenceUtils.getInstance(context).getStringPreference(Constants.SharePreKeys.USEID_VALUE);
       if (userId==null||userId.length()==0){
           userId= UUID.randomUUID().toString();
           SharePrefenceUtils.getInstance(context).setStringPreference(Constants.SharePreKeys.USEID_VALUE,userId);
       }
        MobclickAgent.onProfileSignIn(userId);

        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.enableEncrypt(true);// 6.0.0版本及以后
        MobclickAgent.setDebugMode( true );
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }
    
    private void initDatabase()
    {
        OrmliteHelper.getInstance(context);
    }
}
