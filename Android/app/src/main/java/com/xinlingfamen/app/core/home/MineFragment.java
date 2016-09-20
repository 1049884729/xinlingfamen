package com.xinlingfamen.app.core.home;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.golshadi.majid.core.DownloadManagerPro;
import com.golshadi.majid.report.listener.DownloadManagerListener;
import com.google.gson.Gson;
import com.j256.ormlite.stmt.query.In;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;
import com.xinlingfamen.app.BaseFragment;
import com.xinlingfamen.app.R;
import com.xinlingfamen.app.config.Constants;
import com.xinlingfamen.app.core.ContentTxtActivity;
import com.xinlingfamen.app.core.modules.AppUpdateBean;
import com.xinlingfamen.app.core.modules.MyConfigBean;
import com.xinlingfamen.app.core.modules.WeiOrgNetBean;
import com.xinlingfamen.app.qiniu.Auth;
import com.xinlingfamen.app.utils.FilesUtils;
import com.xinlingfamen.app.utils.HttpUtil;
import com.xinlingfamen.app.utils.SharePrefenceUtils;
import com.xinlingfamen.app.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements DownloadManagerListener, View.OnClickListener
{
    
    public MineFragment()
    {
        // Required empty public constructor
    }
    
    private MyConfigBean myConfigBean;
    
    private ToggleButton toggleButton;
    
    private RelativeLayout rl_exit, rl_about, rl_returnback, rl_help, rl_checkAppUpdate, rl_commonInfo;
    
    private SharePrefenceUtils sharePrefenceUtils;
    
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_mine, container, false);
        toggleButton = (ToggleButton)parentView.findViewById(R.id.toggleButton);
        rl_returnback = (RelativeLayout)parentView.findViewById(R.id.rl_returnback);
        rl_about = (RelativeLayout)parentView.findViewById(R.id.rl_about);
        rl_help = (RelativeLayout)parentView.findViewById(R.id.rl_help);
        rl_commonInfo = (RelativeLayout)parentView.findViewById(R.id.rl_commonInfo);
        rl_checkAppUpdate = (RelativeLayout)parentView.findViewById(R.id.rl_checkAppUpdate);
        rl_exit = (RelativeLayout)parentView.findViewById(R.id.rl_exit);
        rl_about.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
        rl_returnback.setOnClickListener(this);
        rl_help.setOnClickListener(this);
        rl_commonInfo.setOnClickListener(this);
        rl_checkAppUpdate.setOnClickListener(this);
        sharePrefenceUtils = SharePrefenceUtils.getInstance(mContext);
        setToggleButtonValue(sharePrefenceUtils.getBooleanPreference(Constants.SharePreKeys.ToggleButton_VALUE, true));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                setToggleButtonValue(b);
            }
        });
        getData();
        
        return parentView;
    }
    
    private void setToggleButtonValue(boolean check)
    {
        toggleButton.setChecked(check);
        sharePrefenceUtils.setBooleanPreference(Constants.SharePreKeys.ToggleButton_VALUE, check);
        if (check)
        {
            PushAgent.getInstance(mContext).enable(new IUmengCallback()
            {
                @Override
                public void onSuccess()
                {
                    
                }
                
                @Override
                public void onFailure(String s, String s1)
                {
                    
                }
            });
        }
        else
        {
            PushAgent.getInstance(mContext).disable(new IUmengCallback()
            {
                @Override
                public void onSuccess()
                {
                    
                }
                
                @Override
                public void onFailure(String s, String s1)
                {
                    
                }
            });
            
        }
    }
    
    private void detailIntent(String title, String content, String urlType)
    {
        Intent intent = new Intent(mContext, ContentTxtActivity.class);
        intent.putExtra(ContentTxtActivity.KEY_TITLE, title);
        if (content != null && content.length() > 0)
            intent.putExtra(ContentTxtActivity.KEY_CONTENT, content);
        if (urlType != null && urlType.length() > 0)
            
            intent.putExtra(ContentTxtActivity.KEY_URITYPE, urlType);
        mContext.startActivity(intent);
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
    
    @Override
    public void onClick(View view)
    {
        
        String title = null, content = null, urlType = null;
        switch (view.getId())
        {
            case R.id.rl_exit:
                getActivity().finish();
                System.gc();
                break;
            case R.id.rl_commonInfo:
                title = "信息公告";
                if (myConfigBean != null)
                    content = myConfigBean.publicInfo;
                detailIntent(title, content, urlType);
                break;
            case R.id.rl_checkAppUpdate:
                checkUpdateApp();
                break;
            case R.id.rl_help:
                title = "帮助说明";
                if (myConfigBean != null)
                    content = myConfigBean.helpContent;
                detailIntent(title, content, urlType);
                break;
            case R.id.rl_returnback:
                title = "意见反馈";
                if (myConfigBean != null)
                    content = myConfigBean.returnToemail;
                detailIntent(title, content, urlType);
                break;
            case R.id.rl_about:
                title = "关于";
                if (myConfigBean != null)
                    content = myConfigBean.about;
                detailIntent(title, content, urlType);
                break;
        }
    }
    
    private AlertDialog.Builder alertDialog;
    
    private ProgressDialog progressDialog;
    
    private String downLoadFileName = "";
    
    private void checkUpdateApp()
    {
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
                    
                    if (appUpdateBean.updateVersion <= StringUtils.getVersion(mContext))
                    {
                        Toast.makeText(mContext, "没有任何更新", Toast.LENGTH_SHORT).show();
                        
                    }
                    else
                    {
                        alertDialog = new AlertDialog.Builder(mContext);
                        alertDialog.setCancelable(true);
                        alertDialog.setTitle("提示");
                        alertDialog.setMessage(appUpdateBean.downloadInfo);
                        alertDialog.setNegativeButton(R.string.umeng_common_action_cancel,
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    dialogInterface.dismiss();
                                }
                            });
                        alertDialog.setPositiveButton(R.string.umeng_common_action_Ok,
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(final DialogInterface dialogInterface, int i)
                                {
                                    DownloadManagerPro dm = new DownloadManagerPro(mContext);
                                    dialogInterface.dismiss();
                                    
                                    dm.init(FilesUtils.DOWNLOAD_OTHER, 1, MineFragment.this);
                                    downLoadFileName = "xinlingfamen" + appUpdateBean.updateVersion;
                                    int taskToekn = dm.addTask(downLoadFileName, appUpdateBean.downloadapk, true, true);
                                    try
                                    {
                                        dm.startDownload(taskToekn);
                                        progressDialog = new ProgressDialog(mContext);
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
                Toast.makeText(mContext, "没有任何更新", Toast.LENGTH_SHORT).show();
            }
        });
        
    }
    
    private Auth auth = new Auth();
    
    private void getData()
    {
        HttpUtil.get(auth.privateDownloadUrl(Constants.URL_myConfig), new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                try
                {
                    String res = new String(responseBody, "UTF-8");
                    Gson gson = new Gson();
                    myConfigBean = gson.fromJson(res, MyConfigBean.class);
                    int currentVersion = SharePrefenceUtils.getInstance(mContext)
                        .getIntPreference(Constants.SharePreKeys.myConfig_VERSION);
                    if (myConfigBean.configVersion != currentVersion)
                    {
                        SharePrefenceUtils.getInstance(mContext)
                            .setStringPreference(Constants.SharePreKeys.myConfig_VALUE, res);
                        SharePrefenceUtils.getInstance(mContext)
                            .setIntPreference(Constants.SharePreKeys.myConfig_VERSION, myConfigBean.configVersion);
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
                
                Gson gson = new Gson();
                int currentVersion =
                    SharePrefenceUtils.getInstance(mContext).getIntPreference(Constants.SharePreKeys.myConfig_VERSION);
                if (0 != currentVersion)
                {
                    myConfigBean = gson.fromJson(SharePrefenceUtils.getInstance(mContext)
                        .getStringPreference(Constants.SharePreKeys.myConfig_VALUE), MyConfigBean.class);
                }
                else
                {
                    myConfigBean = gson.fromJson(Constants.MYCONFIG_CONTENT, MyConfigBean.class);
                }
            }
        });
    }
    
    private final String TAG = MineFragment.class.getSimpleName();
    
    public void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart(TAG); // 统计页面，"MainScreen"为页面名称，可自定义
    }
    
    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }
    
}
