package com.fei.netty.springmvc.rpc.framework.sender;

import com.fei.netty.springmvc.rpc.framework.common.RpcByteResponse;

public interface RpcSenderCallBack {
	
	public void success(RpcByteResponse response) ;
	
	public void error(Exception e) ; 
	
}
