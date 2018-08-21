package com.cchcz.blog.constant;

public interface MusicConstant {

    /**
     * 网易云音乐歌单url
     */
    public static final String WANGYI_MUSIC_PLAYLIST_URL = "http://music.163.com/api/playlist/detail";
    /**
     * 网易云音乐获取歌词
     */
    public static final String WANGYI_LRC_URL = "https://music.163.com/api/song/lyric";
    /**
     * 网易云音乐获取歌曲链接
     */
    public static final String WANGYI_MUSIC_URL = "http://music.163.com/song/media/outer/url";
    /**
     * 网易云音乐封面图片
     */
    public static final String WANGYI_PIC_URL = "http://music.163.com/api/song/detail/";
    /**
     * http请求成功响应码
     */
    public static final int HTTP_OK = 200;
    /**
     * 默认的接口成功响应状态码
     */
    public static final int DEFAULT_RESPONSE_OK = 200;

    /**
     * 默认的接口失败响应状态码
     */
    public static final int DEFAULT_RESPONSE_ERROR = 500;

}
