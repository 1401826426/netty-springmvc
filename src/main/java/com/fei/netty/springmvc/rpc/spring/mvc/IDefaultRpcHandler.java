package com.fei.netty.springmvc.rpc.spring.mvc;

import com.fei.netty.springmvc.rpc.RpcInterface;
import com.fei.netty.springmvc.zookeeper.server.ServerGroupEnum;

@RpcInterface(value = ServerGroupEnum.ADMIN)
public interface IDefaultRpcHandler {
	
	public boolean doNoRpcHandler() ; 
	
}
