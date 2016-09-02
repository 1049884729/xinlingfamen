package com.xinlingfamen.app.core.modules;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuff on 2016/9/1.
 */
public class WeiOrgNetBean implements Parcelable {
    /**
     * {
     "updateVersion":0,
     "newUrl":"http://www.so.com"
     }
     */
    public int updateVersion;//第一版的值为0，第二版的值为1，一次累加
    public String newUrl;

    public WeiOrgNetBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.updateVersion);
        dest.writeString(this.newUrl);
    }

    protected WeiOrgNetBean(Parcel in) {
        this.updateVersion = in.readInt();
        this.newUrl = in.readString();
    }

    public static final Creator<WeiOrgNetBean> CREATOR = new Creator<WeiOrgNetBean>() {
        @Override
        public WeiOrgNetBean createFromParcel(Parcel source) {
            return new WeiOrgNetBean(source);
        }

        @Override
        public WeiOrgNetBean[] newArray(int size) {
            return new WeiOrgNetBean[size];
        }
    };
}
