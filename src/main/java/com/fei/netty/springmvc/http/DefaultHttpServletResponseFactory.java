package com.fei.netty.springmvc.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fei.netty.springmvc.HttpServletResponseFactory;

public class DefaultHttpServletResponseFactory implements HttpServletResponseFactory{

	@Override
	public HttpServletResponse getHttpServletResponse(HttpServletRequest request) {
		if(request instanceof DefaultHttpServletRequest){
			return new DefaultHttpServletResponse() ; 
		}
		return null;
	}

}
