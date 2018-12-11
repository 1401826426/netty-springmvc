package com.fei.netty.springmvc.rpc;

public interface RpcCallBack<T>{
	
	public void success(T obj); 
	
	public void error(Throwable throwable) ; 
	
}
