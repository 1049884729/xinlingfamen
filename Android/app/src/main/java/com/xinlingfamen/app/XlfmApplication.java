package com.xinlingfamen.app;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.xinlingfamen.app.db.OrmliteHelper;
import com.xinlingfamen.app.utils.FilesUtils;

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
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.enableEncrypt(true);// 6.0.0版本及以后
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
    
    private void initDatabase()
    {
        OrmliteHelper.getInstance(context);
    }
}
