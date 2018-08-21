package com.cchcz.blog.util;

import com.cchcz.blog.constant.MusicConstant;

import java.text.MessageFormat;

public class UrlBuildUtils {
    /**
     * 获取网易云音乐歌单列表的链接
     */
    private static final String GET_WANGYI_MUSIC_PLAYLIST_URL = "{0}?id={1}&updateTime=-1";
    /**
     * 获取网易云音乐歌词的链接
     */
    private static final String GET_WANGYI_LRC_URL = "{0}?os=pc&id={1}&lv=-1&kv=-1&tv=-1";
    /**
     * 获取网易云音乐歌曲的链接
     */
    private static final String GET_WANGYI_MUSIC_URL = "{0}?id={1}.mp3";
    /**
     * 获取网易云音乐封面图片的链接
     */
    private static final String GET_WANGYI_PIC_URL = "{0}?id={1}&ids=[{2}]";

    /**
     * 获取网易云音乐歌单列表的链接
     *
     * @param playId 歌单id
     * @return string
     */
    public static String getWangyiMusicPlayListUrl(String playId) {
        return MessageFormat.format(GET_WANGYI_MUSIC_PLAYLIST_URL, MusicConstant.WANGYI_MUSIC_PLAYLIST_URL, playId);
    }

    /**
     * 获取网易云音乐歌词的链接
     *
     * @param id 歌曲id
     * @return string
     */
    public static String getWangyiLrcUrl(String id) {
        return MessageFormat.format(GET_WANGYI_LRC_URL, MusicConstant.WANGYI_LRC_URL, id);
    }

    /**
     * 获取网易云音乐歌曲的链接
     *
     * @param id 歌曲id
     * @return string
     */
    public static String getWangyiMusicUrl(String id) {
        return MessageFormat.format(GET_WANGYI_MUSIC_URL, MusicConstant.WANGYI_MUSIC_URL, id);
    }

    /**
     * 获取网易云音乐封面图片的链接
     *
     * @param id 歌曲id
     * @return string
     */
    public static String getWangyiPicUrl(String id) {
        return MessageFormat.format(GET_WANGYI_PIC_URL, MusicConstant.WANGYI_PIC_URL, id, id);
    }


}
