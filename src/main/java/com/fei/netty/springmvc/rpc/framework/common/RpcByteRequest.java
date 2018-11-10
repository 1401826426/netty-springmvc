package com.fei.netty.springmvc.rpc.framework.common;

import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicInteger;

public class RpcByteRequest {
	
	private final static AtomicInteger REQUEST_ID = new AtomicInteger(1) ; 
	
	private int requestId ; 
	
	private byte[] content ; 

	private Object lock = new Object() ; 
	
	private String path ; 
	
	private String host ; 
	
	private int port ; 
	
	public RpcByteRequest(String host,int port ,String path, byte[] content) throws MalformedURLException {
		super();
		this.requestId = REQUEST_ID.getAndIncrement() ; 
		this.path = path ; 
		this.content = content ;
		this.host = host ; 
		this.port = port ; 
	}

	public int getRequestId() {
		return requestId;
	}
	
	public String getHost(){
		return host;  
	}
	
	public int getPort(){
		return port ;  
	}
	
	
	public String getPath(){
		return path ; 
	}
	
	public byte[] packBytes(){
		 return content ; 
	}
	
	public Object getLock(){
		return this.lock ; 
	}
	
	
}






