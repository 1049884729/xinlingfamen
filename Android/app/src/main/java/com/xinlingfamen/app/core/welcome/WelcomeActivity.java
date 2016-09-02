package com.xinlingfamen.app.core.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xinlingfamen.app.core.config.Constants;
import com.xinlingfamen.app.core.modules.AppUpdateBean;
import com.xinlingfamen.app.core.modules.WeiOrgNetBean;
import com.xinlingfamen.app.core.qiniu.Auth;
import com.xinlingfamen.app.core.utils.DialogUtils;
import com.xinlingfamen.app.core.utils.HttpUtil;
import com.xinlingfamen.app.core.utils.StringUtils;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;


public class WelcomeActivity extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        HttpUtil.get(new Auth().privateDownloadUrl(Constants.URL_APP_UPDATE), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody,"UTF-8");
                    Gson gson=new Gson();
                    AppUpdateBean appUpdateBean=gson.fromJson(res, AppUpdateBean.class);

                    if (appUpdateBean.updateVersion<= StringUtils.getVersion(context)){
                        startMainPage();
                    }else {
                        DialogUtils.dialogFragment(getSupportFragmentManager(), appUpdateBean.downloadInfo, new DialogUtils.DialogOkDeal() {
                            @Override
                            public void deal() {

                            }
                        });
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                startMainPage();
            }
        });

    }

    private void startMainPage(){
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//              Intent intent=new Intent(getPackageName()+".main");
                Intent intent=new Intent(getPackageName()+".HomeTab");
                startActivity(intent);
                finish();
            }
        }.sendEmptyMessageDelayed(0,2000);
    }
    /**
     * 进入启动页后不能返回
     */
    @Override
    public void onBackPressed() {
    }
}
