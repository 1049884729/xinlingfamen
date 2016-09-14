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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.golshadi.majid.core.DownloadManagerPro;
import com.golshadi.majid.report.listener.DownloadManagerListener;
import com.google.gson.Gson;
import com.j256.ormlite.stmt.query.In;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xinlingfamen.app.BaseFragment;
import com.xinlingfamen.app.R;
import com.xinlingfamen.app.config.Constants;
import com.xinlingfamen.app.core.ContentTxtActivity;
import com.xinlingfamen.app.core.modules.AppUpdateBean;
import com.xinlingfamen.app.qiniu.Auth;
import com.xinlingfamen.app.utils.FilesUtils;
import com.xinlingfamen.app.utils.HttpUtil;
import com.xinlingfamen.app.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements DownloadManagerListener
{
    
    public MineFragment()
    {
        // Required empty public constructor
    }
    
    private ListView listview;
    
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_mine, container, false);
        listview = (ListView)parentView.findViewById(R.id.listview);
        listview.setAdapter(new MyListAdapter(mContext.getResources().getStringArray(R.array.mine_list)));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (i == 1)
                {
                    checkUpdateApp();
                }
                else
                {
                    String title = null, content = null, urlType = null;
                    switch (i)
                    {
                        case 0:// 信息公告
                            title = "信息公告";
                            content =
                                "中央军委联勤保障部队成立大会今天在北京八一大楼隆重举行。中共中央总书记、国家主席、中央军委主席习近平向武汉联勤保障基地和无锡、桂林、西宁、沈阳、郑州联勤保障中心授予军旗并致训词，代表党中央和中央军委向联勤保障部队全体指战员致以热烈的祝贺。他强调，要牢记使命、勇挑重担，以党在新形势下的强军目标为引领，深入贯彻新形势下军事战略方针，推进政治建军、改革强军、依法治军，按照联合作战、联合训练、联合保障的要求加快部队建设，努力建设一支强大的现代化联勤保障部队。";
                            break;
                        case 2:// 帮助说明
                            title = "帮助说明";
                            
                            break;
                        case 3:// 意见反馈
                            title = "意见反馈";
                            content ="email@163.com";
                            break;
                        case 4:
                            title = "关于";
                            
                            break;
                    }
                    detailIntent(title, content, urlType);
                }
                
            }
        });
        return parentView;
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
    
    private class MyListAdapter extends BaseAdapter
    {
        private String[] strings;
        
        public MyListAdapter(String[] strings)
        {
            this.strings = strings;
        }
        
        @Override
        public int getCount()
        {
            return strings.length;
        }
        
        @Override
        public Object getItem(int i)
        {
            return strings[i];
        }
        
        @Override
        public long getItemId(int i)
        {
            return i;
        }
        
        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_listview, null);
            TextView tvName = (TextView)view.findViewById(R.id.tv_Name);
            tvName.setText(strings[i]);
            return view;
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
}
