package com.gobeike.radioapp.utils;

import java.io.File;
import java.io.IOException;

import com.litesuits.common.io.FileUtils;

import android.content.Context;
import android.os.Environment;

/**
 * Created by xuff on 17-4-15.
 */

public class RadioFIleUtils
{
    /**
     * 下载目录
     */
    private static final String DOWNLOAD_DIR = "MyRadioApp";
    
    /**
     * 缓存目录
     */
    public static final String CACHEMUSIC_DIR = "cacheMusic";
    
    public static String getDownloadExternalPath()
    {
        return Environment.getExternalStorageDirectory().getPath() + File.separator + DOWNLOAD_DIR + File.separator;
    }


    /**
     * :/data/user/0/com.gobeike.radioapp/cache/cacheMusic #
     * @param context
     * @return
     */
    public static String getCacheMusicDirPath(Context context)
    {
        return context.getCacheDir().getPath() + File.separator + CACHEMUSIC_DIR + File.separator;
    }
    
    /**
     * 
     * @param file 放在缓存目录中的文件
     * @return
     */
    public static boolean copyFromCacheToSdcard(File file)
    {
        File outFile = new File(getDownloadExternalPath() + file.getName());
        try
        {
            FileUtils.copyFileToDirectory(file, outFile);
            file.delete();
            return true;
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }


}
