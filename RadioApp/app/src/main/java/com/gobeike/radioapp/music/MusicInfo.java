package com.gobeike.radioapp.music;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuff on 17-3-24.
 */

public class MusicInfo implements Parcelable
{
    
    public String title;
    
    public int totalLength, currentLength;
    
    public boolean isMusic = true;// isMusic is true;
    
    public boolean isVideo = false;
    
    public MusicInfo()
    {
    }
    
    @Override
    public int describeContents()
    {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.title);
        dest.writeInt(this.totalLength);
        dest.writeInt(this.currentLength);
        dest.writeByte(this.isMusic ? (byte)1 : (byte)0);
        dest.writeByte(this.isVideo ? (byte)1 : (byte)0);
    }
    
    protected MusicInfo(Parcel in)
    {
        this.title = in.readString();
        this.totalLength = in.readInt();
        this.currentLength = in.readInt();
        this.isMusic = in.readByte() != 0;
        this.isVideo = in.readByte() != 0;
    }
    
    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>()
    {
        @Override
        public MusicInfo createFromParcel(Parcel source)
        {
            return new MusicInfo(source);
        }
        
        @Override
        public MusicInfo[] newArray(int size)
        {
            return new MusicInfo[size];
        }
    };
}
