package com.xinlingfamen.app.core.modules;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuff on 2016/9/2.
 * app 更新的接口对象
 */
public class AppUpdateBean implements Parcelable {
    /**
     * { "updateVersion":2, "downloadapk":"http://www.so.com", "downloadInfo":"更新说明 ，版本号" }
     */
    public int updateVersion;
    
    public String downloadapk, downloadInfo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.updateVersion);
        dest.writeString(this.downloadapk);
        dest.writeString(this.downloadInfo);
    }

    public AppUpdateBean() {
    }

    protected AppUpdateBean(Parcel in) {
        this.updateVersion = in.readInt();
        this.downloadapk = in.readString();
        this.downloadInfo = in.readString();
    }

    public static final Parcelable.Creator<AppUpdateBean> CREATOR = new Parcelable.Creator<AppUpdateBean>() {
        @Override
        public AppUpdateBean createFromParcel(Parcel source) {
            return new AppUpdateBean(source);
        }

        @Override
        public AppUpdateBean[] newArray(int size) {
            return new AppUpdateBean[size];
        }
    };
}
