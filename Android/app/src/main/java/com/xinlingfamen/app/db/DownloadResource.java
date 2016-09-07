package com.xinlingfamen.app.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.types.DateLongType;
import com.j256.ormlite.field.types.DateTimeType;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by xuff on 2016/9/7.
 * 下载资源的存储数据库
 */
@DatabaseTable(tableName = "downloadresource")
public class DownloadResource implements Parcelable {
    public static final String _ID = "id";
    
    public static final String _localUrl = "localUrl";
    
    public static final String _res_type = "res_type";
    
    public static final String _dateTimeType = "dateTimeType";
    
    public static final String _netPathUrl = "netPathUrl";
    
    @DatabaseField (columnName =_localUrl)
    public String localUrl;// 本地资源路径
    
    @DatabaseField (columnName =_res_type,dataType = DataType.INTEGER)
    public int res_type;// 资源类型：1 。mp3 ， 2. 视频 3.文本文件
    
    @DatabaseField(columnName = _dateTimeType, dataType = DataType.DATE_TIME)
    
    public Date dateTimeType;// 下载时间
    
    @DatabaseField (columnName =_netPathUrl)

    public String netPathUrl;// 网络路径
    
    @DatabaseField(generatedId = true, id = true, columnName = _ID)
    public long id;

    public DownloadResource() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.localUrl);
        dest.writeInt(this.res_type);
        dest.writeLong(this.dateTimeType != null ? this.dateTimeType.getTime() : -1);
        dest.writeString(this.netPathUrl);
        dest.writeLong(this.id);
    }

    protected DownloadResource(Parcel in) {
        this.localUrl = in.readString();
        this.res_type = in.readInt();
        long tmpDateTimeType = in.readLong();
        this.dateTimeType = tmpDateTimeType == -1 ? null : new Date(tmpDateTimeType);
        this.netPathUrl = in.readString();
        this.id = in.readLong();
    }

    public static final Creator<DownloadResource> CREATOR = new Creator<DownloadResource>() {
        @Override
        public DownloadResource createFromParcel(Parcel source) {
            return new DownloadResource(source);
        }

        @Override
        public DownloadResource[] newArray(int size) {
            return new DownloadResource[size];
        }
    };
}
