package com.fei.netty.springmvc.http.session;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import util.collection.CollectionUtils;

@SuppressWarnings("deprecation")
public class DefaultSession implements HttpSession{

	private Map<String,Object> attributes = new HashMap<>() ; 
	
	private long createTime ; 
	
	private String sessionId ; 
	
	private volatile long lastAccessedTime ; 
	
	private ServletContext servletContext ; 
	
	private int maxInactiveInterval ; 
	
	private boolean isNew ;
	
	private volatile boolean invalid ; 
	
	public DefaultSession(String sessionId) {
		super();
		this.sessionId = sessionId;
		this.createTime = System.currentTimeMillis() ;
		this.isNew = true; 
	}

	@Override
	public long getCreationTime() {
		return createTime;
	}

	@Override
	public String getId() {
		return sessionId;
	}

	@Override
	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		this.maxInactiveInterval = interval ; 
	}

	@Override
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		throw new UnsupportedOperationException() ; 
	}

	@Override
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	@Override
	public Object getValue(String name) {
		throw new UnsupportedOperationException() ;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return CollectionUtils.collectionToEnumeration(attributes.keySet()); 
	}

	@Override
	public String[] getValueNames() {
		throw new UnsupportedOperationException() ;
	}

	@Override
	public void setAttribute(String name, Object value) {
		attributes.put(name, value) ; 
	}

	@Override
	public void putValue(String name, Object value) {
		throw new UnsupportedOperationException() ;
	}

	@Override
	public void removeAttribute(String name) {
		attributes.remove(name) ; 
	}

	@Override
	public void removeValue(String name) {
		
	}

	@Override
	public void invalidate() {
		this.invalid = true ; 
		this.attributes.clear();  
	}

	@Override
	public boolean isNew() {
		return isNew;
	}

	public synchronized void access() {
		if(invalid){
			return ; 
		}
		this.lastAccessedTime = System.currentTimeMillis() ;
		this.isNew = false ; 
	}

}
