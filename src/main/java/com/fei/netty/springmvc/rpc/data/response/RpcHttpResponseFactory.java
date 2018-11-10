package com.fei.netty.springmvc.rpc.data.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fei.netty.springmvc.HttpServletResponseFactory;
import com.fei.netty.springmvc.rpc.data.request.RpcHttpRequest;

public class RpcHttpResponseFactory implements HttpServletResponseFactory{

	@Override
	public HttpServletResponse getHttpServletResponse(HttpServletRequest request) {
		if(request instanceof RpcHttpRequest){			
			return new RpcHttpResponse() ;
		}
		return null ; 
	}

	

}
