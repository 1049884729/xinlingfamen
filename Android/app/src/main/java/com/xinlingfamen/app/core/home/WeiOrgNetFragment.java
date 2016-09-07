package com.xinlingfamen.app.core.home;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xinlingfamen.app.BaseFragment;
import com.xinlingfamen.app.R;
import com.xinlingfamen.app.config.Constants;
import com.xinlingfamen.app.core.modules.WeiOrgNetBean;
import com.xinlingfamen.app.qiniu.Auth;
import com.xinlingfamen.app.utils.HttpUtil;
import com.xinlingfamen.app.utils.MyHandleDownload;
import com.xinlingfamen.app.utils.SharePrefenceUtils;
import com.xinlingfamen.app.utils.StringUtils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import im.delight.android.webview.AdvancedWebView;

public class WeiOrgNetFragment extends BaseFragment implements AdvancedWebView.Listener, WeiOrgNetFragmentBack
{
    
    private AdvancedWebView mWebView;
    
    private static final String LOG_NAME = "WeiOrgNetFragment";
    
    boolean preventCaching = true;
    
    private Auth auth = new Auth();
    
    private ProgressBar progressBar;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        
        mWebView = (AdvancedWebView)rootView.findViewById(R.id.webview);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressbar);
        mWebView.setListener(getActivity(), this);
        mWebView.setMixedContentAllowed(true);
        progressBar.setProgress(0);
        String netUrl =
            SharePrefenceUtils.getInstance(mContext).getStringPreference(Constants.SharePreKeys.weiOrgNet_VALUE);
        if (netUrl == null || netUrl.length() == 0)
        {
            netUrl = "http://xlcihang.info/b/";
        }
        mWebView.loadUrl(netUrl, preventCaching);
        mWebView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
            
            @Override
            public void onPermissionRequest(final PermissionRequest request)
            {
                
                getActivity().runOnUiThread(new Runnable()
                {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run()
                    {
                        request.grant(request.getResources());
                    }// run
                });// MainActivity
                
            }// onPermissionRequest
        });// setWebChrome
        initCheckUri();
        return rootView;
    }
    
    private void initCheckUri()
    {
        HttpUtil.get(auth.privateDownloadUrl(Constants.URL_WEIORGNET), new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                try
                {
                    String res = new String(responseBody, "UTF-8");
                    Gson gson = new Gson();
                    WeiOrgNetBean weiOrgNetBean = gson.fromJson(res, WeiOrgNetBean.class);
                    int currentVersion = SharePrefenceUtils.getInstance(mContext)
                        .getIntPreference(Constants.SharePreKeys.weiOrgNet_VERSION);
                    if (weiOrgNetBean.updateVersion != currentVersion)
                    {
                        SharePrefenceUtils.getInstance(mContext)
                            .setStringPreference(Constants.SharePreKeys.weiOrgNet_VALUE, weiOrgNetBean.newUrl);
                        SharePrefenceUtils.getInstance(mContext)
                            .setIntPreference(Constants.SharePreKeys.weiOrgNet_VERSION, weiOrgNetBean.updateVersion);
                        mWebView.loadUrl(weiOrgNetBean.newUrl, preventCaching);
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
                
            }
        });
    }
    
    @SuppressLint("NewApi")
    @Override
    public void onResume()
    {
        super.onResume();
        mWebView.onResume();
        // ...
    }
    
    @SuppressLint("NewApi")
    @Override
    public void onPause()
    {
        mWebView.onPause();
        // ...
        super.onPause();
    }
    
    @Override
    public void onDestroy()
    {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }
    
    @Override
    public void onPageStarted(String url, Bitmap favicon)
    {
    }
    
    @Override
    public void onPageFinished(String url)
    {
    }
    
    @Override
    public void onPageError(int errorCode, String description, String failingUrl)
    {
    }
    
    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength,
        String contentDisposition, String userAgent)
    {
        // some file is available for download
        // either handle the download yourself or use the code below
        // downFile(url);
        
    }
    
    @Override
    public void onExternalPageRequest(String url)
    {
        downFile(url);
    }
    
    private void downFile(String url)
    {
        if (StringUtils.isGrantDownload(url))
        {
            String fileName = StringUtils.getFileNameFromUrl(url);
            if (MyHandleDownload.handleDownload(mContext, url, fileName))
            {
                // download successfully handled
                Toast.makeText(mContext, "已经开始下载文件" + fileName, Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    @Override
    public boolean onBackPressed()
    {
        if (!mWebView.onBackPressed())
        {
            return false;
        }
        // ...
        return true;
    }
    
}
