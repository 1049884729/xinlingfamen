package com.xinlingfamen.app.db;

import android.content.Context;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by xff on
 */
public class DownloadResourceIDao {

    private OrmliteHelper dbHelper = null;

    private Context context;
    private static DownloadResourceIDao DownloadResourceIDao = null;
    private Dao<DownloadResource, Long> dao;

    public static DownloadResourceIDao getInstance(Context context) {
        if (DownloadResourceIDao == null) {
            synchronized (DownloadResourceIDao.class) {
                if (DownloadResourceIDao == null) {
                    DownloadResourceIDao = new DownloadResourceIDao(context);
                }
            }
        }
        return DownloadResourceIDao;
    }

    private DownloadResourceIDao(Context context) {
        this.context = context;
        dbHelper = OrmliteHelper.getInstance(context);
        getDao();
    }

    private boolean getDao() {
        try {
            dao = dbHelper.getDao(DownloadResource.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dao == null) return true;
        return false;
    }

    public boolean add(DownloadResource downloadResource) {
        if (getDao()) return false;
        int result = -1;
        try {
            DownloadResource oldUser = isExistUser(downloadResource);
            if (oldUser == null) {
//                DownloadResource.setP_id(oldUser.getP_id());
//                dao.update(DownloadResource);
//            } else {
                result = dao.create(downloadResource);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        }
        return result > 0;
    }

    public boolean update(DownloadResource DownloadResource) {
        if (getDao()) return false;
        int result = -1;
        try {
            DownloadResource oldUser = isExistUser(DownloadResource);
            if (oldUser != null) {
                DownloadResource.id = oldUser.id;
                result = dao.update(DownloadResource);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        }
        return result > 0;
    }

    public DownloadResource isExistUser(DownloadResource downloadResource) {
        if (getDao()) return null;
        QueryBuilder builder = dao.queryBuilder();
        Where<DownloadResource, String> where = builder.where();

        try {
            where.eq(DownloadResource._netPathUrl, downloadResource.netPathUrl);
            builder.setWhere(where);
            PreparedQuery<DownloadResource> preparedQuery = builder.prepare();

            List<DownloadResource> DownloadResources = dao.query(preparedQuery);
            if (DownloadResources != null && DownloadResources.size() > 0) {
                return DownloadResources.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public boolean deleteUser() {
        if (getDao()) return false;
        DeleteBuilder builder = dao.deleteBuilder();
        Where<DownloadResource, String> where = builder.where();

        try {
            where.isNotNull(DownloadResource._ID);
            builder.setWhere(where);
            PreparedDelete<DownloadResource> preparedQuery = builder.prepare();
            dao.delete(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

    public DownloadResource queryFirstAccount() {
        if (getDao()) return null;
        QueryBuilder builder = dao.queryBuilder();
        Where<DownloadResource, String> where = builder.where();

        try {
            where.isNotNull(DownloadResource._ID);

            PreparedQuery<DownloadResource> preparedQuery = builder.prepare();
            return dao.queryForFirst(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DownloadResource queryByAccount(String account) {
        if (getDao()) return null;
        QueryBuilder builder = dao.queryBuilder();
        Where<DownloadResource, String> where = builder.where();

        try {
            where.like(DownloadResource._localUrl, account);
//        where.or();
//        where.like(DownloadResource.TAGS,values);
//        where.or();
//        where.like(DownloadResource.USERID,values);
            PreparedQuery<DownloadResource> preparedQuery = builder.prepare();
            return dao.queryForFirst(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DownloadResource> queryAllUser() {
        if (getDao()) return null;
        try {
            List<DownloadResource> DownloadResources = dao.queryForAll();
            return DownloadResources;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数据库过滤
     *
     * @param prefix
     * @return
     */
    public List<DownloadResource> filterResults(String prefix) {
        QueryBuilder builder = dao.queryBuilder();
        Where<DownloadResource, String> where = builder.where();

        try {
            where.like(DownloadResource._res_type, prefix);

            PreparedQuery<DownloadResource> preparedQuery = builder.prepare();
            return dao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
