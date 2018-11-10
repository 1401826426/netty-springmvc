package com.fei.netty.springmvc.http;

import javax.servlet.http.Cookie;

public class CookieAdapter extends Cookie{

	private static final long serialVersionUID = 1L;
	
	private io.netty.handler.codec.http.Cookie cookie ; 
	
	
	public CookieAdapter(io.netty.handler.codec.http.Cookie cookie){
		super(cookie.getName(),cookie.getValue()) ; 
		this.cookie = cookie ; 
	}


	@Override
	public void setComment(String purpose) {
		cookie.setComment(purpose);
	}


	@Override
	public String getComment() {
		return cookie.getComment() ; 
	}


	@Override
	public void setDomain(String domain) {
		cookie.setDomain(domain);
	}


	@Override
	public String getDomain() {
		return cookie.getDomain() ; 
	}


	@Override
	public void setMaxAge(int expiry) {
		cookie.setMaxAge(expiry);
	}


	@Override
	public int getMaxAge() {
		return (int) cookie.getMaxAge() ; 
	}


	@Override
	public void setPath(String uri) {
		cookie.setPath(uri);
	}


	@Override
	public String getPath() {
		return cookie.getPath() ; 
	}


	@Override
	public void setSecure(boolean flag) {
		cookie.setSecure(flag);
	}


	@Override
	public boolean getSecure() {
		return cookie.isSecure() ; 
	}


	@Override
	public String getName() {
		return cookie.getName() ; 
	}


	@Override
	public void setValue(String newValue) {
		cookie.setValue(newValue);
	}


	@Override
	public String getValue() {
		return cookie.getValue() ; 
	}


	@Override
	public int getVersion() {
		return cookie.getVersion() ; 
	}


	@Override
	public void setVersion(int v) {
		cookie.setVersion(v);
	}


	@Override
	public Object clone() {
		return super.clone();
	}


	@Override
	public void setHttpOnly(boolean isHttpOnly) {
		cookie.setHttpOnly(isHttpOnly);
	}


	@Override
	public boolean isHttpOnly() {
		return  cookie.isHttpOnly() ;  
	}
	
	
	
	

	

}
