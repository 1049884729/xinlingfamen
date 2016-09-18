package com.xinlingfamen.app.core.modules;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuff on 2016/9/18.
 */
public class MyConfigBean implements Parcelable {

    public String publicInfo,helpContent,returnToemail,about;
    /**
     * 版本配置
     */
    public int configVersion;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.publicInfo);
        dest.writeString(this.helpContent);
        dest.writeString(this.returnToemail);
        dest.writeString(this.about);
        dest.writeInt(this.configVersion);
    }

    public MyConfigBean() {
    }

    protected MyConfigBean(Parcel in) {
        this.publicInfo = in.readString();
        this.helpContent = in.readString();
        this.returnToemail = in.readString();
        this.about = in.readString();
        this.configVersion = in.readInt();
    }

    public static final Parcelable.Creator<MyConfigBean> CREATOR = new Parcelable.Creator<MyConfigBean>() {
        @Override
        public MyConfigBean createFromParcel(Parcel source) {
            return new MyConfigBean(source);
        }

        @Override
        public MyConfigBean[] newArray(int size) {
            return new MyConfigBean[size];
        }
    };
}
