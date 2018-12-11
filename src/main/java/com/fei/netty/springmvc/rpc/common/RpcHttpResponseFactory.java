package com.fei.netty.springmvc.rpc.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fei.netty.springmvc.HttpServletResponseFactory;
import com.fei.netty.springmvc.rpc.socketrpc.RpcHttpServletRequest;
import com.fei.netty.springmvc.rpc.socketrpc.RpcServletHttpResponse;

public class RpcHttpResponseFactory implements HttpServletResponseFactory{

	@Override
	public HttpServletResponse getHttpServletResponse(HttpServletRequest request) {
		if(request instanceof RpcHttpServletRequest){			
			return new RpcServletHttpResponse() ;
		}
		return null ; 
	}

	

}
