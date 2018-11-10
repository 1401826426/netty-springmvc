package com.fei.netty.springmvc.rpc.framework.spring.mvc;

import java.lang.reflect.Method;

public class RpcMethodMapping {
	
	private Method method ; 
	
	private Class<?> handlerType;

	private String url ; 
	
	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Class<?> getHandlerType() {
		return handlerType;
	}

	public void setHandlerType(Class<?> handlerType) {
		this.handlerType = handlerType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	} 
	
	
	
}
