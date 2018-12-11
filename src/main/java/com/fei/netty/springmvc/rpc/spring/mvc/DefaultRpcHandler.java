package com.fei.netty.springmvc.rpc.spring.mvc;

public class DefaultRpcHandler implements IDefaultRpcHandler{
	
	//这里有一个坑点事RequestMappingHandlerAdapter的returnValueHandlers
	//的顺序是不能调整的,而且ViewNameMethodReturnValueHandler是排在自定义的HandlerMethodReturnValueHandler前面的
	//这样对于rpc调用的方法如果返回值是Void.class必定是ViewNameMethodReturnValueHandler来处理的
	public boolean doNoRpcHandler(){
		System.out.println("no rpc handler");
		return true; 
	}
	
	
}
