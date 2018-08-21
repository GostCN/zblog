
package com.cchcz.blog.util;

import com.alibaba.fastjson.JSONObject;
import com.cchcz.blog.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Date;

/**
 * 百度站长推送工具类
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Slf4j
public class BaiduUtil extends RestClientUtil {
    /**
     * 自行登录百度站长平台后获取响应的cookie
     */
    private static final String COOKIE = "";

    /**
     * 推送链接到百度站长平台
     *
     * @param urlString 百度站长平台地址
     * @param params    待推送的链接
     * @return api接口响应内容
     */
    public static String doPush(String urlString, String params) {
//        if (StringUtils.isEmpty(COOKIE)) {
//            throw new BlogCommentException("尚未设置百度站长平台的Cookie信息，该功能不可用！");
//        }
        log.info("{} REST url: {}", new Date(), urlString);
        HttpURLConnection connection = null;
        try {
            connection = openConnection(urlString);
            connection.setRequestMethod("POST");
            // (如果不设此项,json数据 ,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Action", "1000");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Connection", "keep-alive");
//            connection.setRequestProperty("Cookie", COOKIE);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 设置连接超时时间，单位毫秒
            connection.setConnectTimeout(10000);
            // 设置读取数据超时时间，单位毫秒
            connection.setReadTimeout(10000);
            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            if (params != null) {
                final OutputStream outputStream = connection.getOutputStream();
                writeOutput(outputStream, params);
                outputStream.close();
            }
            log.info("RestClientUtil response: {} : {}", connection.getResponseCode(), connection.getResponseMessage());
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                return readInput(connection.getInputStream(), "UTF-8");
            } else {
                return readInput(connection.getErrorStream(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("推送到百度站长平台发生异常！", e);
            return "";
        } finally {
            if (null != connection) {
                connection.disconnect();
            }
        }
    }


    public static String getLocationInfo(String url, String field) {
        RedisTemplate redisTemplate = (RedisTemplate) SpringBeanFactory.getBean("redisTemplate");
        String locationJson = (String) redisTemplate.opsForHash().get(CommonConstant.IP_GEO_LOCATION_KEY, field);
        if (locationJson != null) {
            return locationJson;
        }
        locationJson = get(url);
        JSONObject localtionContent = JSONObject.parseObject(locationJson).getJSONObject("content");
        JSONObject addressDetail = localtionContent.getJSONObject("address_detail");
        JSONObject point = localtionContent.getJSONObject("point");
        if (addressDetail != null && point != null) {
            redisTemplate.opsForHash().put(CommonConstant.IP_GEO_LOCATION_KEY, field, locationJson);
        }
        return locationJson;
    }
}
