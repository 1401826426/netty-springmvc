package com.fei.netty.springmvc.rpc.sender;

import com.fei.netty.springmvc.future.RFuture;
import com.fei.netty.springmvc.rpc.common.RpcRequest;
import com.fei.netty.springmvc.rpc.common.RpcResponse;

public interface Sender2 {
	public RFuture<RpcResponse> sendRequest(RpcRequest rpcRequest) ; 
}
