package com.example.demo.utils;

import org.apache.commons.lang3.StringUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
	    Map<String, Cookie> cookieMap = ReadCookieMap(request);
	    if (cookieMap.containsKey(name)) {
	        Cookie cookie = (Cookie) cookieMap.get(name);
	        return cookie;
	    } else {
	        return null;
	    }
	}

	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
	    Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if (null != cookies) {
	        for (Cookie cookie : cookies) {
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}

	public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value, int time, String domain) {
	    Cookie cookie = new Cookie(name, value);
	    cookie.setPath("/");
	    if(StringUtils.isNotBlank(domain)){
	    	cookie.setDomain(domain);
	    }
	    
	    try {
	        URLEncoder.encode(value, "utf-8");
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    cookie.setMaxAge(time);
	    response.addCookie(cookie);
	    return response;
	}
	
	public static String getValue(HttpServletRequest request, String valueKey){
		//获取参数值，支持head、cookie、urlParam三种方式
		String key = null;
		// 1、urlParam
		if(StringUtils.isBlank(key)){
			key = request.getParameter(valueKey);
		}
		// 2、head
		if(StringUtils.isBlank(key)){
			key = request.getHeader(valueKey);
		}
		// 3、cookie
		if(StringUtils.isBlank(key)){
			Cookie cookie = getCookieByName(request, valueKey);
			if(cookie != null){
				key = cookie.getValue();
			}
		}
		return key;
	}

}
