package com.xinlingfamen.app.core.qiniu;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xuff on 2016/9/2.
 */
public class SharePreferenceUtils
{
    
    private static final String FILE_NAME = "xinlingfamen";
    
    private static SharedPreferences sharedPreferences = null;
    
    private static SharedPreferences getInstance(Context context)
    {
        if (sharedPreferences == null)
        {
            synchronized (SharePreferenceUtils.class)
            {
                if (sharedPreferences == null)
                {
                    sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                }
            }
        }
        return sharedPreferences;
    }
    
    /**
     * 获取默认的整数值
     * 
     * @param context
     * @param key
     * @return
     */
    public static int getIntPreference(Context context, String key)
    {
        getInstance(context);
        return sharedPreferences.getInt(key, 0);
    }
    
    public static void setIntPreference(Context context, String key, int value)
    {
        getInstance(context);
        sharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * 获取默认的字符串值
     *
     * @param context
     * @param key
     * @return
     */
    public static String getStringPreference(Context context, String key)
    {
        getInstance(context);
        return sharedPreferences.getString(key, "");
    }

    public static void setStringPreference(Context context, String key, String value)
    {
        getInstance(context);
        sharedPreferences.edit().putString(key, value).commit();
    }
}
