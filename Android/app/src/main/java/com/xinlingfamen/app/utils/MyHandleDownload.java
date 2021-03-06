package com.xinlingfamen.app.utils;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import im.delight.android.webview.AdvancedWebView;

/**
 * Created by xuff on 2016/9/1.
 */
public class MyHandleDownload
{
    public static boolean handleDownload(final Context context, final String fromUrl, final String toFilename)
    {
        if (Build.VERSION.SDK_INT < 9)
        {
            throw new RuntimeException("Method requires API level 9 or above");
        }
        
        final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fromUrl));
        if (Build.VERSION.SDK_INT >= 11)
        {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        if (fromUrl.endsWith("mp3")){
            request.setDestinationInExternalPublicDir(FilesUtils.DOWNLOAD_MP3, toFilename);

        }else if (StringUtils.isVideoFile(fromUrl)){
            request.setDestinationInExternalPublicDir(FilesUtils.DOWNLOAD_VIDEO, toFilename);

        }else if (StringUtils.isTxtPdfFile(fromUrl)){
            request.setDestinationInExternalPublicDir(FilesUtils.DOWNLOAD_FILE, toFilename);
        }else {
            request.setDestinationInExternalPublicDir(FilesUtils.DOWNLOAD_OTHER, toFilename);
        }

        final DownloadManager dm = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        try
        {
            try
            {
                dm.enqueue(request);
            }
            catch (SecurityException e)
            {
                if (Build.VERSION.SDK_INT >= 11)
                {
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                }
                dm.enqueue(request);
            }
            
            return true;
        }
        // if the download manager app has been disabled on the device
        catch (IllegalArgumentException e)
        {
            // show the settings screen where the user can enable the download manager app again
            openAppSettings(context, AdvancedWebView.PACKAGE_NAME_DOWNLOAD_MANAGER);
            
            return false;
        }
    }
    
    @SuppressLint("NewApi")
    private static boolean openAppSettings(final Context context, final String packageName)
    {
        if (Build.VERSION.SDK_INT < 9)
        {
            throw new RuntimeException("Method requires API level 9 or above");
        }
        
        try
        {
            final Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            
            context.startActivity(intent);
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
