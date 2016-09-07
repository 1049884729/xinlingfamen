package com.xinlingfamen.app.core.welcome;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.golshadi.majid.core.DownloadManagerPro;
import com.golshadi.majid.report.listener.DownloadManagerListener;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xinlingfamen.app.R;
import com.xinlingfamen.app.config.Constants;
import com.xinlingfamen.app.core.modules.AppUpdateBean;
import com.xinlingfamen.app.qiniu.Auth;
import com.xinlingfamen.app.utils.FilesUtils;
import com.xinlingfamen.app.utils.HttpUtil;
import com.xinlingfamen.app.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class WelcomeActivity extends AppCompatActivity implements DownloadManagerListener
{
    
    private Context context;
    
    private AlertDialog.Builder alertDialog;
    
    private ProgressDialog progressDialog;
    
    private String downLoadFileName = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;
        HttpUtil.get(new Auth().privateDownloadUrl(Constants.URL_APP_UPDATE), new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                try
                {
                    String res = new String(responseBody, "UTF-8");
                    Gson gson = new Gson();
                    final AppUpdateBean appUpdateBean = gson.fromJson(res, AppUpdateBean.class);
                    
                    if (appUpdateBean.updateVersion <= StringUtils.getVersion(context))
                    {
                        startMainPage();
                    }
                    else
                    {
                        alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setCancelable(true);
                        alertDialog.setTitle("提示");
                        alertDialog.setMessage(appUpdateBean.downloadInfo);
                        alertDialog.setNegativeButton(R.string.umeng_common_action_cancel,
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    startMainPage();
                                    dialogInterface.dismiss();
                                }
                            });
                        alertDialog.setPositiveButton(R.string.umeng_common_action_Ok,
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(final DialogInterface dialogInterface, int i)
                                {
                                    DownloadManagerPro dm = new DownloadManagerPro(context);
                                    dialogInterface.dismiss();
                                    
                                    dm.init(FilesUtils.DOWNLOAD_OTHER, 1, WelcomeActivity.this);
                                    downLoadFileName = "xinlingfamen" + appUpdateBean.updateVersion;
                                    int taskToekn = dm.addTask(downLoadFileName, appUpdateBean.downloadapk, true, true);
                                    try
                                    {
                                        dm.startDownload(taskToekn);
                                        progressDialog = new ProgressDialog(context);
                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                        progressDialog.setProgress(0);
                                        progressDialog.setMessage("正在下载……");
                                        progressDialog.setMax(100);
                                        progressDialog.setCancelable(false);
                                        progressDialog.setCanceledOnTouchOutside(false);
                                        progressDialog.show();
                                    }
                                    catch (IOException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        alertDialog.create().show();
                    }
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                startMainPage();
            }
        });
        
    }
    
    private void startMainPage()
    {
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                // Intent intent=new Intent(getPackageName()+".main");
                Intent intent = new Intent(getPackageName() + ".HomeTab");
                startActivity(intent);
                finish();
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }
    
    /**
     * 进入启动页后不能返回
     */
    @Override
    public void onBackPressed()
    {
    }
    
    @Override
    public void OnDownloadStarted(long taskId)
    {
        
    }
    
    @Override
    public void OnDownloadPaused(long taskId)
    {
        
    }
    
    @Override
    public void onDownloadProcess(long taskId, double percent, long downloadedLength)
    {
        progressDialog.setProgress((int)percent);
        
    }
    
    @Override
    public void OnDownloadFinished(long taskId)
    {

    }
    
    @Override
    public void OnDownloadRebuildStart(long taskId)
    {
        
    }
    
    @Override
    public void OnDownloadRebuildFinished(long taskId)
    {
        
    }
    
    @Override
    public void OnDownloadCompleted(long taskId)
    {
        progressDialog.dismiss();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File downloadFile = new File(Environment.getExternalStorageDirectory() + File.separator
                + FilesUtils.DOWNLOAD_OTHER + File.separator + downLoadFileName + ".apk");
        intent.setDataAndType(Uri.fromFile(downloadFile), "application/vnd.android.package-archive");
        startActivity(intent);
    }
    
    @Override
    public void connectionLost(long taskId)
    {
        
    }
}
