package com.xinlingfamen.app.core.modules;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuff on 2016/9/10.
 */
public class GroupBean implements Parcelable {
    public String groupName;
    public String localPath;

    public int groupChildSize;

    public GroupBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupName);
        dest.writeString(this.localPath);
        dest.writeInt(this.groupChildSize);
    }

    protected GroupBean(Parcel in) {
        this.groupName = in.readString();
        this.localPath = in.readString();
        this.groupChildSize = in.readInt();
    }

    public static final Creator<GroupBean> CREATOR = new Creator<GroupBean>() {
        @Override
        public GroupBean createFromParcel(Parcel source) {
            return new GroupBean(source);
        }

        @Override
        public GroupBean[] newArray(int size) {
            return new GroupBean[size];
        }
    };
}
