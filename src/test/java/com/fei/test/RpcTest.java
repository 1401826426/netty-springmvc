package com.fei.test;

import com.fei.netty.springmvc.rpc.RpcCallBack;
import com.fei.netty.springmvc.rpc.RpcInterface;
import com.fei.netty.springmvc.zookeeper.server.ServerGroupEnum;
import com.fei.test.converter.ResultDto;

@RpcInterface(ServerGroupEnum.ADMIN)
public interface RpcTest {
	
	boolean test() ; 
	
	String testStr(String str) ; 
	
	ResultDto testA(TestDto1 dto1,TestDto2 dto2,String c) ; 
	
	interface RpcTestAync extends RpcTest{
		void test(RpcCallBack<Boolean> callback) ; 
	}
	
}
