package com.fei.netty.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpServletResponseFactory {
	
	public HttpServletResponse getHttpServletResponse(HttpServletRequest request) ; 
	
}
