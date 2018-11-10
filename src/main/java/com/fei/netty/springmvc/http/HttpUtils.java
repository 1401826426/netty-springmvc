package com.fei.netty.springmvc.http;

import javax.servlet.http.Cookie;

public class HttpUtils {
	
	public static String generateCookieString(Cookie cookie){
		StringBuilder sb = new StringBuilder("") ;
		sb.append(cookie.getName()+"="+cookie.getValue()); 
		sb.append("; Version="+cookie.getVersion()) ;
		if(cookie.getComment() != null){
			sb.append("; Comment="+cookie.getComment());  
		}
		if(cookie.getDomain() != null){
			sb.append("; Domain="+cookie.getDomain()) ; 
		}
		if(cookie.getMaxAge() >= 0){
			sb.append("; Max-Age="+cookie.getMaxAge()) ; 
		}
		if(cookie.getPath() != null){
			sb.append("; Path="+cookie.getPath()) ; 
		}
		if(cookie.getSecure()){
			sb.append("; Secure") ; 
		}
		if(cookie.isHttpOnly()){
			sb.append("; HttpOnly") ; 
		}
		return sb.toString() ;
	}
	
}








