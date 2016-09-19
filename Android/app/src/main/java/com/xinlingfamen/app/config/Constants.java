package com.xinlingfamen.app.config;

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

    /**
     * http://oci3ugbk0.bkt.clouddn.com/MyConfig.json
     * 个人配置页面
     */
    private static final String myConfig = "MyConfig";

    public static final String URL_myConfig= "http://oci3ugbk0.bkt.clouddn.com/" + myConfig + ".json";


    
    public static class SharePreKeys
    {
        public static final String sharePreXml="xlfmXml";
        /**
         * 微官网地址版本号，有更新的话，版本号要大一个数值
         */
        public static final String weiOrgNet_VERSION = weiOrgNet + "_version";
        
        public static final String weiOrgNet_VALUE = weiOrgNet + "_value";
        
        public static final String myConfig_VERSION = myConfig + "_version";
        
        public static final String myConfig_VALUE = myConfig + "_value";
        public static final String USEID_VALUE = "_userID";
    }


    public static String MYCONFIG_CONTENT="{\n" +
            "\"configVersion\":1,\n" +
            "\"publicInfo\":\"目前，该功能是想公示一些各地学佛群的联系方式，暂未收集，后期会更新\",\n" +
            "\"helpContent\":\"目前，该版本仅做了简单的功能，在微官网下载内容或者直接查看内容，在已下载模块下拉刷新一下，即可看到新下载的资源，并且使用系统软件打开该文件。\\n \\t\\t 新的版本会持续更新中……，有更新会检测到，安装即可！请诸位佛友、师兄放心使用\",\n" +
            "\"returnToemail\":\"如果有好的建议或者使用过程中遇到了问题，可直接发送邮件到zouchengxufei@163.com即可，为防止被删除，请备注好主题————软件使用反馈，\",\n" +
            "\"about\":\"关于该项目：如果您是一位开发人员，并且有能力和时间去为佛学的传播出一份力，可以参与到该项目中：https://github.com/1049884729/xinlingfamen  \\n 关于作者：联系方式：QQ：891733943\"\n" +
            "}";


}
