package com.fei.test;

import com.fei.netty.springmvc.rpc.RpcInterface;
import com.fei.netty.springmvc.zookeeper.server.ServerGroupEnum;

@RpcInterface(ServerGroupEnum.ADMIN)
public interface RpcTest {
	
	void test() ; 
	
}
