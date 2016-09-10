package com.xinlingfamen.app.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xuff on 2016/9/1.
 */
public class StringUtils {

    /**
     * 从Url中获取文件名字
     * @param url
     * @return
     */
    public static String getFileNameFromUrl(String url){
        String tempName=url.substring(url.lastIndexOf("/"));
        return tempName;
    }

    /**
     *
     * @param urlddffdddda
     * @return true 允许下载，否则false，不允许下载
     */
    public static boolean isGrantDownload(String url){
        if (url.endsWith(".mp3"))return true;
        if (isVideoFile(url))return true;
        if (isTxtPdfFile(url))return true;
        return false;
    }

    /**
     * 判断是否是视频文件
     * @param url
     * @return
     */
    public static boolean isVideoFile(String url){
        if (url.toLowerCase().endsWith(".3gp")||url.toLowerCase().endsWith(".mp4")||url.toLowerCase().endsWith(".rmvb"))return true;
        return false;
    }

    /**
     *
     * @param url
     * @return
     */
    public static boolean isTxtPdfFile(String url){
        if (url.toLowerCase().endsWith(".pdf")||url.toLowerCase().endsWith(".txt"))return true;
        return false;
    }
    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static String longToString(long time){
       return simpleDateFormat.format(new Date(time));
    }
    public static String longToSize(long fileS){
        String size = "";

            DecimalFormat df = new DecimalFormat("#.00");
            if (fileS < 1024) {
                size = df.format((double) fileS) + "BT";
            } else if (fileS < 1048576) {
                size = df.format((double) fileS / 1024) + "KB";
            } else if (fileS < 1073741824) {
                size = df.format((double) fileS / 1048576) + "MB";
            } else {
                size = df.format((double) fileS / 1073741824) +"GB";
            }
        return size;
    }
}
