package com.xinlingfamen.app.core.modules;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuff on 2016/9/10.
 */
public class ChildBean implements Parcelable {
    public String fileName, filePath;

    public String fileSize, downTime;

    /**
     * 0 :file
     * 1:mp3
     * 2:video
     */
    public int type=0;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fileName);
        dest.writeString(this.filePath);
        dest.writeString(this.fileSize);
        dest.writeString(this.downTime);
        dest.writeInt(this.type);
    }

    public ChildBean() {
    }

    protected ChildBean(Parcel in) {
        this.fileName = in.readString();
        this.filePath = in.readString();
        this.fileSize = in.readString();
        this.downTime = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<ChildBean> CREATOR = new Parcelable.Creator<ChildBean>() {
        @Override
        public ChildBean createFromParcel(Parcel source) {
            return new ChildBean(source);
        }

        @Override
        public ChildBean[] newArray(int size) {
            return new ChildBean[size];
        }
    };
}
