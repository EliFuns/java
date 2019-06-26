package com.example.demo.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:RestUtil
 * Description:     获取url接口的数据 => map类型
 */
public class RestUtil {
    public static Map<String, Object> doGet(String url) {
        try {
            URL thisurl = new URL(url); // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) thisurl
                    .openConnection();// 打开连接
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            String s = sb.toString();
            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<String, Object>();
            map = gson.fromJson(s, map.getClass());
            return map;
        } catch (Exception e) {
//            e.printStackTrace();
            // System.out.println("失败!");
            return null;
        }
    }

    //定义方法，用于访问墙外接口
    public static Map<String, Object> visitByVPN(String url) throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", Integer.valueOf("1080")));
        URL realUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection(proxy);
        connection.setRequestProperty("Connection", "keep-alive");// 设置通用的请求属性
        connection.connect();// 建立实际的连接
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.connect();// 连接会话
        // 获取输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {// 循环读取流
            sb.append(line);
        }
        br.close();// 关闭流
        connection.disconnect();// 断开连接
        String s = sb.toString();
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(s, map.getClass());
        return map;
    }
}
