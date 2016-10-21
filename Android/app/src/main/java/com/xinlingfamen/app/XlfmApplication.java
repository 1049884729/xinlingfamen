package com.xinlingfamen.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.xinlingfamen.app.config.Constants;
import com.xinlingfamen.app.db.OrmliteHelper;
import com.xinlingfamen.app.utils.FilesUtils;
import com.xinlingfamen.app.utils.SharePrefenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Administrator on 2016/8/17.
 */
public class XlfmApplication extends Application
{
    private Context context;
    /**
     * if  false, is debug apk;测试版本
     * else true, is release pak;发布版本
     */
    private final boolean ISRELEASE=true;
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
        String userId = SharePrefenceUtils.getInstance(context).getStringPreference(Constants.SharePreKeys.USEID_VALUE);
        if (userId == null || userId.length() == 0)
        {
            userId = UUID.randomUUID().toString();
            SharePrefenceUtils.getInstance(context).setStringPreference(Constants.SharePreKeys.USEID_VALUE, userId);
        }
        MobclickAgent.onProfileSignIn(userId);
        
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.enableEncrypt(true);// 6.0.0版本及以后
        MobclickAgent.setDebugMode(!ISRELEASE);
        if (ISRELEASE){
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(this);
        }

        PushAgent mPushAgent = PushAgent.getInstance(this);
        // 注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback()
        {
            
            @Override
            public void onSuccess(String deviceToken)
            {
                // 注册成功会返回device token
            }
            
            @Override
            public void onFailure(String s, String s1)
            {
                
            }
        });
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler()
        {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg)
            {
                JSONObject object = msg.getRaw();
                if (!object.isNull("extra"))
                {
                    try
                    {
                        object = object.getJSONObject("extra");
                    }
                    catch (JSONException e1)
                    {
                        e1.printStackTrace();
                        return;
                    }
                    if (!object.isNull("notification"))
                    {
                        try
                        {
                            Bundle bundle = new Bundle();
                            Intent intent;
                            int notification = object.getInt("notification");
                            switch (notification)
                            {
                                // case AppConfig.TYPE0:
                                // bundle.putString(AppConfig.CANDIDATEID, object.getString("cddId"));
                                // bundle.putString(AppConfig.CANDIDATENAME, object.getString("name"));
                                // bundle.putString(AppConfig.NOTIFICATION, AppConfig.TYPE0+"");
                                // intent=new Intent(MyApplication.this,CandidateActivity.class);
                                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                // intent.putExtras(bundle);
                                // startActivity(intent);
                                // break;
                                default:
                                    break;
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
            
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }
    
    private void initDatabase()
    {
        OrmliteHelper.getInstance(context);
    }
}
