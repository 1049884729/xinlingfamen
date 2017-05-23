package com.gobeike.radioapp.music;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuff on 17-3-24.
 */

public class MusicInfo implements Parcelable
{
    
    public String title;
    
    public int totalLength, currentLength;
    public Bitmap bitmap;
    public String img;
    public String KEY_ARTIST,KEY_ALBUM,KEY_TITLE;
    public boolean isMusic = true;// isMusic is true;
    /**
     * 本地媒体存放路径
     */
    public String localMediaPath;
    public boolean isVideo = false;
    
    public MusicInfo()
    {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.totalLength);
        dest.writeInt(this.currentLength);
        dest.writeParcelable(this.bitmap, flags);
        dest.writeString(this.img);
        dest.writeString(this.KEY_ARTIST);
        dest.writeString(this.KEY_ALBUM);
        dest.writeString(this.KEY_TITLE);
        dest.writeByte(this.isMusic ? (byte) 1 : (byte) 0);
        dest.writeString(this.localMediaPath);
        dest.writeByte(this.isVideo ? (byte) 1 : (byte) 0);
    }

    protected MusicInfo(Parcel in) {
        this.title = in.readString();
        this.totalLength = in.readInt();
        this.currentLength = in.readInt();
        this.bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.img = in.readString();
        this.KEY_ARTIST = in.readString();
        this.KEY_ALBUM = in.readString();
        this.KEY_TITLE = in.readString();
        this.isMusic = in.readByte() != 0;
        this.localMediaPath = in.readString();
        this.isVideo = in.readByte() != 0;
    }

    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel source) {
            return new MusicInfo(source);
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };
}
