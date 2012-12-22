package com.hywang.timeline.utils.web;

import javax.servlet.http.Cookie;


public class CookiesManager {

    public static String getValue(Cookie[] cookies,String key){
        
        String value=null;
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(key)){
                value=cookie.getValue();
                break;
            }
        }
        return value;
    }
}
