package com.xinlingfamen.app.core.home;

import com.golshadi.majid.core.DownloadManagerPro;
import com.golshadi.majid.database.DatabaseHelper;
import com.golshadi.majid.database.TasksDataSource;
import com.golshadi.majid.report.listener.DownloadManagerListener;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xinlingfamen.app.BaseFragment;
import com.xinlingfamen.app.R;
import com.xinlingfamen.app.config.Constants;
import com.xinlingfamen.app.core.modules.WeiOrgNetBean;
import com.xinlingfamen.app.core.welcome.WelcomeActivity;
import com.xinlingfamen.app.qiniu.Auth;
import com.xinlingfamen.app.utils.DialogUtils;
import com.xinlingfamen.app.utils.FilesUtils;
import com.xinlingfamen.app.utils.HttpUtil;
import com.xinlingfamen.app.utils.MyHandleDownload;
import com.xinlingfamen.app.utils.SharePrefenceUtils;
import com.xinlingfamen.app.utils.StringUtils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import im.delight.android.webview.AdvancedWebView;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class WeiOrgNetFragment extends BaseFragment implements AdvancedWebView.Listener, WeiOrgNetFragmentBack{

    private AdvancedWebView mWebView;

    private static final String LOG_NAME = "WeiOrgNetFragment";

    boolean preventCaching = true;

    private Auth auth = new Auth();

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        mWebView = (AdvancedWebView) rootView.findViewById(R.id.webview);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        mWebView.setListener(getActivity(), this);
        mWebView.setMixedContentAllowed(true);
        progressBar.setProgress(0);
        String netUrl =
                SharePrefenceUtils.getInstance(mContext).getStringPreference(Constants.SharePreKeys.weiOrgNet_VALUE);
        if (netUrl == null || netUrl.length() == 0) {
            netUrl = "http://xlcihang.info/b/";
        }
        mWebView.loadUrl(netUrl, preventCaching);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onPermissionRequest(final PermissionRequest request) {

                getActivity().runOnUiThread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                    }// run
                });// MainActivity

            }// onPermissionRequest
        });// setWebChrome
        initCheckUri();
        return rootView;
    }

    private void initCheckUri() {
        HttpUtil.get(auth.privateDownloadUrl(Constants.URL_WEIORGNET), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody, "UTF-8");
                    Gson gson = new Gson();
                    WeiOrgNetBean weiOrgNetBean = gson.fromJson(res, WeiOrgNetBean.class);
                    int currentVersion = SharePrefenceUtils.getInstance(mContext)
                            .getIntPreference(Constants.SharePreKeys.weiOrgNet_VERSION);
                    if (weiOrgNetBean.updateVersion != currentVersion) {
                        SharePrefenceUtils.getInstance(mContext)
                                .setStringPreference(Constants.SharePreKeys.weiOrgNet_VALUE, weiOrgNetBean.newUrl);
                        SharePrefenceUtils.getInstance(mContext)
                                .setIntPreference(Constants.SharePreKeys.weiOrgNet_VERSION, weiOrgNetBean.updateVersion);
                        mWebView.loadUrl(weiOrgNetBean.newUrl, preventCaching);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
    }

    @Override
    public void onPageFinished(String url) {
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength,
                                    String contentDisposition, String userAgent) {
        // some file is available for download
        // either handle the download yourself or use the code below
        if (isMNC()) {
            if (getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                tempDownLoadUrl=url;
                   requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE},REQUEST_WRITE);
            }else {
                downFile(url);

            }

        }else {
            downFile(url);

        }

    }

    private String tempDownLoadUrl=null;
    public boolean isMNC() {
        /*
         TODO: In the Android M Preview release, checking if the platform is M is done through
         the codename, not the version code. Once the API has been finalised, the following check
         should be used: */
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

    }

    private static final int REQUEST_READ = 0;
    private static final int REQUEST_WRITE = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
           if (requestCode==REQUEST_WRITE){
               int size=permissions.length;
               for (int grant=0;grant<size;grant++){
                   if (grantResults[grant]==PackageManager.PERMISSION_GRANTED){
                       downFile(tempDownLoadUrl);
                   }else {
                       Toast.makeText(mContext,"没有权限使用存储卡",Toast.LENGTH_LONG).show();
                   }
               }
           }
    }


    @Override
    public void onExternalPageRequest(String url) {
        if (isMNC()) {
            if (getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                tempDownLoadUrl=url;
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE},REQUEST_WRITE);
            }else {
                downFile(url);

            }

        }else {
            downFile(url);

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dm = new DownloadManagerPro(mContext);
        tasksDataSource=new TasksDataSource();
        tasksDataSource.openDatabase(new DatabaseHelper(mContext));

    }

    DownloadManagerPro dm ;
    TasksDataSource tasksDataSource;
    private void downFile(String url) {

        if (StringUtils.isGrantDownload(url)) {
            String fileName = StringUtils.getFileNameFromUrl(url);

            if (MyHandleDownload.handleDownload(mContext, url, fileName)) {
                // download successfully handled
                String destFile="save to /sdcard/";
                if (url.endsWith("mp3")){
                    destFile+=FilesUtils.DOWNLOAD_MP3;

                }else if (StringUtils.isVideoFile(url)){
                    destFile+=FilesUtils.DOWNLOAD_VIDEO;

                }else if (StringUtils.isTxtPdfFile(url)){
                    destFile+=FilesUtils.DOWNLOAD_FILE;
                }else {
                    destFile+=FilesUtils.DOWNLOAD_OTHER;
                }
                Toast.makeText(mContext, "下载文件"+destFile + fileName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return false;
        }
        // ...
        return true;
    }


}
