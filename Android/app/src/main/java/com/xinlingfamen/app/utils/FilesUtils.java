package com.xinlingfamen.app.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xuff on 2016/9/1.
 */
public class FilesUtils {

    public static final String FOLDER_NAME = "XinLingfamen";
    public static final String DOWNLOAD_MP3 = FOLDER_NAME+File.separator+"mp3s";
    public static final String DOWNLOAD_VIDEO = FOLDER_NAME+File.separator+"videos";
    public static final String DOWNLOAD_OTHER= FOLDER_NAME+File.separator+"other";
    public static final String DOWNLOAD_FILE= FOLDER_NAME+File.separator+"file";


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
    public static File create(String folder, String fileName){
        File file = new File(
                address(folder, fileName));
        Log.d("---------------------------------------", "Create file address: " + address(folder, fileName));
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public static void forceCreate(String folder, String fileName){
        File dirs = new File(folder);
        dirs.mkdirs();

        File file = new File(
                address(folder, fileName));

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String folder, String fileName){
        File file = new File(
                address(folder, fileName));
        file.delete();
    }

    public static long size(String folder, String fileName){
        File file = new File(
                address(folder, fileName));
        return file.length();
    }

    public static FileOutputStream getOutputStream(String folder, String fileName){
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(
                    address(folder, fileName));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return fileOut;
    }

    public static FileInputStream getInputStream(String folder, String fileName){
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(
                    address(folder, fileName));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return fileIn;
    }

    public static String address(String folder, String file){
        return folder+"/"+file;
    }

    /**
     * 是否是目录
     * @param file
     * @return
     */
    public static boolean isDirs(File file){
        if (file!=null&&file.exists()){
            if (file.isDirectory())return true;
        }
        return false;
    }

}
