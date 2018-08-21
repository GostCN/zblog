package com.cchcz.blog.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cchcz.blog.model.entity.NetEaseMusic;
import com.cchcz.blog.model.enums.ResponseStatus;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.constant.MusicConstant;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网易云音乐API接口工具
 */
public class NetEaseApi {

    /**
     * 默认歌单ID
     */
    private static final String DEFAULT_PLAYID = "448927692";

    /**
     * 获取默认歌单信息
     */
    public static List<NetEaseMusic> getDefaultPlaylist() {
        return getPlaylist(DEFAULT_PLAYID);
    }

    /**
     * 获取指定歌单信息
     *
     * @param playId 歌单id
     */
    public static List<NetEaseMusic> getPlaylist(String playId) {
        if (StringUtils.isEmpty(playId)) {
            return getDefaultPlaylist();
        }
        List<NetEaseMusic> musicList = new ArrayList<>();
        String musicListJson = RestClientUtil.get(UrlBuildUtils.getWangyiMusicPlayListUrl(playId));
        if (StringUtils.isEmpty(musicListJson)) {
            return musicList;
        }
        JSONObject obj = JSONObject.parseObject(musicListJson);
        int code = obj.getIntValue("code");
        try {
            if (code == MusicConstant.HTTP_OK) {
                JSONArray tracks = obj.getJSONObject("result").getJSONArray("tracks");
                NetEaseMusic music = null;
                for (int i = 0, len = tracks.size(); i < len; i++) {
                    music = new NetEaseMusic();
                    obj = tracks.getJSONObject(i);
                    music.setId(obj.getString("id"));
                    music.setName(obj.getString("name"));
                    music.setUrl(UrlBuildUtils.getWangyiMusicUrl(music.getId()));

                    // 加载演唱者信息
                    loadArtists(obj, music);

                    // 加载专辑信息
                    loadAlbum(obj, music);

                    musicList.add(music);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musicList;
    }

    /**
     * 加载演唱者信息
     *
     * @param obj   歌单列表JSON
     * @param music 网易云音乐对象
     */
    private static void loadArtists(JSONObject obj, NetEaseMusic music) {
        //演唱者
        JSONArray artistsArray = obj.getJSONArray("artists");
        if (artistsArray != null && !artistsArray.isEmpty()) {
            JSONObject object = artistsArray.getJSONObject(0);
            music.setAuthor(object.getString("name"));
            music.setAuthorImg(object.getString("picUrl"));
        }
    }

    /**
     * 加载专辑信息
     *
     * @param obj   歌单列表JSON
     * @param music 网易云音乐对象
     */
    private static void loadAlbum(JSONObject obj, NetEaseMusic music) {
        //所属专辑
        JSONObject albumObj = obj.getJSONObject("album");
        if (albumObj != null) {
            music.setAlbumName(albumObj.getString("name"));
            music.setAlbumImg(albumObj.getString("picUrl"));
        }
    }

    /**
     * 获取歌曲歌词
     *
     * @param musicId 歌曲id
     */
    public static ResponseVO getLyric(String musicId) {
        String lyricJson = RestClientUtil.get(UrlBuildUtils.getWangyiLrcUrl(musicId));
        if (StringUtils.isEmpty(lyricJson)) {
            return ResultUtil.error(ResponseStatus.ERROR);
        }
        JSONObject obj = JSONObject.parseObject(lyricJson);
        int code = obj.getIntValue("code");
        if (code != MusicConstant.HTTP_OK) {
            return ResultUtil.error(ResponseStatus.ERROR);
        }
        Boolean uncollected = obj.getBoolean("uncollected");
        if (uncollected != null && uncollected) {
            return ResultUtil.error(ResponseStatus.NOT_FOUND);
        }
        obj = obj.getJSONObject("lrc");
        if (obj == null) {
            return ResultUtil.error(ResponseStatus.NOT_FOUND);
        }
        String lyric = obj.getString("lyric");
        if (lyric != null) {
            lyric = lyric.replaceAll("(\\.[0-9]*)", "");
            lyric = lyric.replaceAll("\n", "\n.");
        }
        return ResultUtil.success("成功", lyric);
    }
}
