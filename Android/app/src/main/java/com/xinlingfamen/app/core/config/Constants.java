package com.xinlingfamen.app.core.config;

/**
 * Created by xuff on 2016/9/1.
 */
public class Constants
{
    
    // 微官网文件名
    private static final String weiOrgNet = "WeiOrgNet";
    
    /**
     * 微官网更新地址
     */
    public static final String URL_WEIORGNET = "http://oci3ugbk0.bkt.clouddn.com/" + weiOrgNet + ".json";
    
    private static final String updateAppInfo = "UpdateAppInfo";
    
    public static final String URL_APP_UPDATE = "http://oci3ugbk0.bkt.clouddn.com/" + updateAppInfo + ".json";
    
    public static class SharePreKeys
    {
        /**
         * 微官网地址版本号，有更新的话，版本号要大一个数值
         */
        public static final String weiOrgNet_VERSION = weiOrgNet + "_version";
        
        public static final String weiOrgNet_VALUE = weiOrgNet + "_value";
        
        public static final String updateAppInfo_VERSION = updateAppInfo + "_version";
        
        public static final String updateAppInfo_VALUE = updateAppInfo + "_value";
    }
}
