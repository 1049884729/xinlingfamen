package com.gobeike.radioapp.music;

import java.util.List;
import java.util.Random;

/**
 * Created by xuff on 17-3-31.
 * <p>
 * 音乐播放列表清单
 */

public class PlayerMusicList
{
    
    /**
     * 当前音乐播放的位置
     */
    private int currentPosiont = 0;
    
    /**
     * 音乐播放列表
     */
    private List<String> playMusicLists;
    
    /**
     * 是否单曲循环
     */
    private boolean isSingleRecycle = false;
    
    /**
     * 是否播放列表循环
     */
    private boolean isPlayListRecycle = false;
    
    /**
     * 是否随机播放
     */
    private boolean isRandomRecycle = false;
    
    public void setSingleRecycle(boolean singleRecycle)
    {
        if (singleRecycle)
        {
            setPlayListRecycle(false);
            setRandomRecycle(false);
        }
        isSingleRecycle = singleRecycle;
        
    }
    
    public void setPlayListRecycle(boolean playListRecycle)
    {
        if (playListRecycle)
        {
            setSingleRecycle(false);
            setRandomRecycle(false);
        }
        isPlayListRecycle = playListRecycle;
    }
    
    public void setRandomRecycle(boolean randomRecycle)
    {
        isRandomRecycle = randomRecycle;
        if (randomRecycle)
        {
            setSingleRecycle(false);
            setPlayListRecycle(false);
        }
    }
    
    public int getCurrentPosiont()
    {
        return currentPosiont;
    }
    
    public void setCurrentPosiont(int currentPosiont)
    {
        this.currentPosiont = currentPosiont;
    }
    
    public List<String> getPlayMusicLists()
    {
        return playMusicLists;
    }
    
    public void setPlayMusicLists(List<String> playMusicLists)
    {
        this.playMusicLists = playMusicLists;
    }
    
    /**
     * 获取上一首播放的音乐信息
     *
     * @return
     */
    public String getPrePlayMusic()
    {
        if (playMusicLists != null && playMusicLists.size() != 0)
        {
            if (isSingleRecycle)
            {
                return playMusicLists.get(currentPosiont);
            }
            else if (isPlayListRecycle)
            {
                currentPosiont = currentPosiont - 1;
                if (currentPosiont >= 0)
                {
                    return playMusicLists.get(currentPosiont);
                }
                else
                {
                    /**
                     * 当前播放已经完毕
                     */
                    if (isPlayListRecycle)
                    {
                        int size = playMusicLists.size();
                        
                        currentPosiont = size - 1;
                        return playMusicLists.get(currentPosiont);
                    }
                    
                }
            }
            else if (isRandomRecycle)
            {
                int size = playMusicLists.size();
                currentPosiont = new Random().nextInt(size);
                return playMusicLists.get(currentPosiont);
            }
            
        }
        return null;
    }
    
    /**
     * 获取当前要播放的音乐
     * 
     * @return
     */
    public String getCurrentPlayMusic()
    {
        if (playMusicLists != null && playMusicLists.size() != 0)
        {
            return playMusicLists.get(currentPosiont);
        }
        return null;
    }
    
    /**
     * 获取下一首播放的音乐信息
     *
     * @return
     */
    public String getNextPlayMusic()
    {
        if (playMusicLists != null && playMusicLists.size() != 0)
        {
            if (isSingleRecycle)
            {
                return playMusicLists.get(currentPosiont);
            }
            else if (isPlayListRecycle)
            {
                int size = playMusicLists.size();
                currentPosiont = currentPosiont + 1;
                if (currentPosiont < size)
                {
                    return playMusicLists.get(currentPosiont);
                }
                else
                {
                    /**
                     * 当前播放已经完毕
                     */
                    if (isPlayListRecycle)
                    {
                        currentPosiont = 0;
                        return playMusicLists.get(currentPosiont);
                    }
                    
                }
            }
            else if (isRandomRecycle)
            {
                int size = playMusicLists.size();
                currentPosiont = new Random().nextInt(size);
                return playMusicLists.get(currentPosiont);
            }
            
        }
        return null;
    }
}
