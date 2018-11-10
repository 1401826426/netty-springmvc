package com.fei.netty.springmvc.rpc.data.request;

import org.apache.commons.pool.BasePoolableObjectFactory;

public class RpcHttpRequestFactory extends BasePoolableObjectFactory{

	@Override
	public Object makeObject() throws Exception {
		return new RpcHttpRequest();
	}

	@Override
	public void destroyObject(Object obj) throws Exception {
		RpcHttpRequest request = (RpcHttpRequest)obj ; 
		request.release() ; 
	}

	@Override
	public void activateObject(Object obj) throws Exception {
		RpcHttpRequest request = (RpcHttpRequest)obj ;
		request.release();  
	}


	@Override
	public boolean validateObject(Object obj) {
		return obj instanceof RpcHttpRequest;
	}
	
	
	
}
