package com.cchcz.blog.spider.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <Description> </Description>
 * <ClassName> SpiderUrlConnection</ClassName>
 *
 * @author cchcz
 * @date 2018年03月09日 13:20
 */
public class SpiderUrlConnection {

    public static String get(String url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        urlConnection.connect();
        int responseCode = urlConnection.getResponseCode();
        if (200 == responseCode) {
            BufferedReader reader = null;
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            reader.close();
            return resultBuffer.toString();
        } else {
            throw new RuntimeException("请求url异常,responseCode:" + responseCode);
        }
    }

    public static String get(String url, int timeout) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        urlConnection.setReadTimeout(timeout);
        urlConnection.setConnectTimeout(timeout);
        urlConnection.connect();
        int responseCode = urlConnection.getResponseCode();
        if (200 == responseCode) {
            BufferedReader reader = null;
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            reader.close();
            return resultBuffer.toString();
        } else {
            throw new RuntimeException("请求url异常,responseCode:" + responseCode);
        }
    }

    public static void main(String[] args) throws IOException {
        String s = SpiderUrlConnection.get("https://my.oschina.net/xiaomingnevermind/blog/1787269");
        System.out.println(s);
    }
}
