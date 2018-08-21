package com.cchcz.blog.model.entity;

import lombok.Data;

/**
 * 网易云音乐实体类
 */
@Data
public class NetEaseMusic {
    /**
     * 歌曲id
     */
    private String id;
    /**
     * 歌曲名
     */
    private String name;
    /**
     * 演唱者
     */
    private String author;
    /**
     * 演唱者封面
     */
    private String authorImg;
    /**
     * 专辑
     */
    private String albumName;
    /**
     * 专辑封面
     */
    private String albumImg;
    /**
     * 歌曲地址
     */
    private String url;

    public NetEaseMusic() {

    }

    public NetEaseMusic(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
