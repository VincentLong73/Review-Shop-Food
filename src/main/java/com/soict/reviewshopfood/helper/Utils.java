package com.soict.reviewshopfood.helper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class Utils {
    public Utils () {}
    public Cookie createCookie(String name, String value, boolean httpOnly, Long age) {
        Cookie newCookie = new Cookie(name, value);
        newCookie.setMaxAge(Math.toIntExact(age));
        newCookie.setHttpOnly(httpOnly);
        newCookie.setPath("/");
        return newCookie;
    }

    public Cookie getCookie(HttpServletRequest request, String name) {
        Cookie [] cookies = request.getCookies();
        Cookie cookie = null;
        if(cookies != null) {
            for(Cookie c : cookies){
                if(c.getName().equals(name)){
                    cookie = c;
                }
            }
        }
        return cookie;
    }
    public Cookie deleteCookie(HttpServletRequest request, String name) {
        Cookie [] cookies = request.getCookies();
        Cookie cookie = null;
        if(cookies != null) {
            for(Cookie c : cookies){
                if(c.getName().equals(name)){
                    c.setMaxAge(0);
                    cookie = c;
                }
            }
        }
        return cookie;
    }
    
    public boolean checkCookies(Cookie[] cookies) {
    	if(cookies != null) {
    		for(Cookie cookie : cookies) {
    			if(cookie.getName().equals("Authorization")) {
    				return true;
    			}
        	}
    	}

    	return false;
    }
    	
}

