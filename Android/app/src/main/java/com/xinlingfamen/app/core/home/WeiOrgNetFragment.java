package com.xinlingfamen.app.core.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.widget.Toast;

import com.umeng.update.UmengUpdateAgent;
import com.xinlingfamen.app.BaseFragment;
import com.xinlingfamen.app.R;

import im.delight.android.webview.AdvancedWebView;

public class WeiOrgNetFragment extends BaseFragment implements AdvancedWebView.Listener {

    private AdvancedWebView mWebView;

    boolean preventCaching = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        mWebView = (AdvancedWebView) rootView.findViewById(R.id.webview);
        mWebView.setListener(getActivity(), this);
        mWebView.setMixedContentAllowed(true);
        mWebView.addPermittedHostname("xlcihang.info");
        mWebView.loadUrl("http://xlcihang.info/b/",preventCaching);
        // ...

        return rootView;
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
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        // some file is available for download
        // either handle the download yourself or use the code below

        if (AdvancedWebView.handleDownload(mContext, url, suggestedFilename)) {
            // download successfully handled
            Toast.makeText(mContext,"download successfully",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mContext,"user has disabled download manager app on the device",Toast.LENGTH_SHORT).show();

            // download couldn't be handled because user has disabled download manager app on the device
            // TODO show some notice to the user
        }
    }

    @Override
    public void onExternalPageRequest(String url) {
    }


}
