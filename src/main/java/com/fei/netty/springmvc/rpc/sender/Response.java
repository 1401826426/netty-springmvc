package com.fei.netty.springmvc.rpc.sender;

import com.fei.netty.springmvc.rpc.common.RpcResponse;

public interface Response {

	int getId();

	RpcResponse getRpcResponse() ;  
	
}
