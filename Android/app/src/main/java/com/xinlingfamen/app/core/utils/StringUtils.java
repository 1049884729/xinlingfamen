package com.xinlingfamen.app.core.utils;

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
     * @param url
     * @return true 允许下载，否则false，不允许下载
     */
    public static boolean isGrantDownload(String url){
        if (url.endsWith(".mp3"))return true;
        if (isVideoFile(url))return true;
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
}
