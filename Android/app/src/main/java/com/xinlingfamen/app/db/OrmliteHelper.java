package com.xinlingfamen.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by xuff on 2016/9/7.
 */
public class OrmliteHelper extends OrmLiteSqliteOpenHelper
{
    
    // name of the database file for your application -- change to something appropriate for your app

    private static final String databaseNameFinal = "xinlingfamen.db";
    private static final int databaseVersion = 1;
    private Context context;
    private static OrmliteHelper ormliteHelper = null;

    public static OrmliteHelper getInstance(Context context) {
        if (ormliteHelper == null) {
            synchronized (OrmliteHelper.class) {
                if (ormliteHelper == null)
                    ormliteHelper = new OrmliteHelper(context, databaseNameFinal);
            }
        }
        return ormliteHelper;
    }

    public OrmliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                         int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    public OrmliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        this(context, name, null, databaseVersion);

    }

    public OrmliteHelper(Context context, String name) {
        this(context, databaseNameFinal, null);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, DownloadResource.class);
//            TableUtils.createTable(connectionSource, JourneyBean.class);
//            TableUtils.createTable(connectionSource, LoginUserPsd.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

        try {

            TableUtils.dropTable(connectionSource, DownloadResource.class, false);
//            TableUtils.dropTable(connectionSource, JourneyBean.class, false);
//            TableUtils.dropTable(connectionSource, LoginUserPsd.class, false);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
