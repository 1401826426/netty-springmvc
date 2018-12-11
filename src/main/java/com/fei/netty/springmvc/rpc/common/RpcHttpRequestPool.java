package com.fei.netty.springmvc.rpc.common;

import org.apache.commons.pool.impl.GenericObjectPool;

public class RpcHttpRequestPool extends GenericObjectPool{

	public RpcHttpRequestPool(){
		super(new RpcHttpRequestFactory()) ;
		setMinIdle(0);
		setMaxIdle(Integer.MAX_VALUE);
		setTestOnBorrow(false);
		setTestOnReturn(false);
		setMaxActive(Integer.MAX_VALUE);
		
	}
	
}
