package com.xinlingfamen.app.core.modules;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuff on 2016/9/10.
 */
public class GroupBean implements Parcelable {
    public String groupName;

    public int groupChildSize;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupName);
        dest.writeInt(this.groupChildSize);
    }

    public GroupBean() {
    }

    protected GroupBean(Parcel in) {
        this.groupName = in.readString();
        this.groupChildSize = in.readInt();
    }

    public static final Parcelable.Creator<GroupBean> CREATOR = new Parcelable.Creator<GroupBean>() {
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
