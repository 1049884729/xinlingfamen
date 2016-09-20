package com.xinlingfamen.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xinlingfamen.app.config.Constants;

/**
 * Created by xuff on 2016/9/7.
 */
public class SharePrefenceUtils
{
    
    private Context context;
    
    private SharedPreferences sharedPreferences = null;
    
    private SharePrefenceUtils(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.SharePreKeys.sharePreXml, Context.MODE_PRIVATE);
    }
    
    private static SharePrefenceUtils sharePrefenceUtils = null;
    
    public static SharePrefenceUtils getInstance(Context context)
    {
        if (sharePrefenceUtils == null)
        {
            synchronized (SharePrefenceUtils.class)
            {
                if (sharePrefenceUtils == null)
                {
                    sharePrefenceUtils = new SharePrefenceUtils(context);
                }
            }
        }
        return sharePrefenceUtils;
    }
    
    /**
     * 获取默认的整数值
     *
     * @param context
     * @param key
     * @return
     */
    public int getIntPreference(String key)
    {
        
        return sharedPreferences.getInt(key, 0);
    }
    
    public void setIntPreference(String key, int value)
    {
        
        sharedPreferences.edit().putInt(key, value).commit();
    }
    
    /**
     * 获取默认的字符串值
     *
     * @param context
     * @param key
     * @return
     */
    public String getStringPreference(String key)
    {
        
        return sharedPreferences.getString(key, "");
    }
    
    public void setStringPreference(String key, String value)
    {
        
        sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * 获取默认的字符串值
     *
     * @param context
     * @param defaultvalue
     * @return
     */
    public boolean getBooleanPreference(String key,boolean defaultvalue)
    {

        return sharedPreferences.getBoolean(key, defaultvalue);
    }

    public void setBooleanPreference(String key, boolean value)
    {

        sharedPreferences.edit().putBoolean(key, value).commit();
    }
}
