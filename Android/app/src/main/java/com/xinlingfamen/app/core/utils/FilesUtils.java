package com.xinlingfamen.app.core.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by xuff on 2016/9/1.
 */
public class FilesUtils {

    public static final String FOLDER_NAME = "XinLingfamen";
    public static final String DOWNLOAD_MP3 = FOLDER_NAME+File.separator+"mp3s";
    public static final String DOWNLOAD_VIDEO = FOLDER_NAME+File.separator+"videos";


    /**
     * 获取存贮文件的文件夹路径
     *
     * @return
     */
    public static File createFolders(String childDir)
    {
        File  baseDir = Environment.getExternalStorageDirectory();

        File aviaryFolder = new File(baseDir, childDir);
        if (aviaryFolder.exists())
            return aviaryFolder;
        if (aviaryFolder.isFile())
            aviaryFolder.delete();
        if (aviaryFolder.mkdirs())
            return aviaryFolder;
        return Environment.getExternalStorageDirectory();
    }


}
