package com.fei.netty.springmvc.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fei.netty.springmvc.rpc.common.RpcRequest;
import com.fei.netty.springmvc.rpc.common.RpcResponse;

public class RpcLog {
	
    private static Logger logger = LoggerFactory.getLogger("com.fei.interface.log") ; 
	
	public static void logRpcRequest(RpcRequest request){
		StringBuilder sb = new StringBuilder("") ; 
		sb.append(request.getRequestId() + "#" + request.getCommand() + "#" + request.getData() == null ? 0 : request.getData().length) ; 
		logger.info("rpcRequest#"+sb.toString());
	}
	
	public static void logRpcResponse(RpcResponse response){
		StringBuilder sb = new StringBuilder("") ; 
		sb.append(response.getRequestId() + "#" + response.getData().readableBytes()) ; 
		logger.info("rpcResponse#"+sb.toString());
	}
	
}
