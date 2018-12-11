package com.fei.netty.springmvc.rpc.common;

import java.util.concurrent.atomic.AtomicInteger;

public class DefaultRequestIdGenerator implements RequestIdGenerator{
	
	private AtomicInteger id = new AtomicInteger(1) ; 
	
	public int getRequestId(){
		return id.getAndIncrement() ; 
	}
	
}
