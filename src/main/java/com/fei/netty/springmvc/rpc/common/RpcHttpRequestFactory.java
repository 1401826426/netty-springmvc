package com.fei.netty.springmvc.rpc.common;

import org.apache.commons.pool.BasePoolableObjectFactory;

import com.fei.netty.springmvc.rpc.socketrpc.RpcHttpServletRequest;

public class RpcHttpRequestFactory extends BasePoolableObjectFactory{

	@Override
	public Object makeObject() throws Exception {
		return new RpcHttpServletRequest();
	}

	@Override
	public void destroyObject(Object obj) throws Exception {
		RpcHttpServletRequest request = (RpcHttpServletRequest)obj ; 
		request.release() ; 
	}

	@Override
	public void activateObject(Object obj) throws Exception {
		RpcHttpServletRequest request = (RpcHttpServletRequest)obj ;
		request.release();  
	}


	@Override
	public boolean validateObject(Object obj) {
		return obj instanceof RpcHttpServletRequest;
	}
	
	
	
}
