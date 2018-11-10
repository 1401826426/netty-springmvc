package com.fei.netty.springmvc.zookeeper.server;

public enum ServerGroupEnum {
	
	ADMIN("admin",1), 
	
	CRAWLER("crawler",2),
	;
	
	private String str ; 
	
	private int id ; 
	
	private ServerGroupEnum(String str,int id){
		this.str = str ; 
		this.id = id ; 
	}
	
	public String str(){
		return str ; 
	}
	
	public int getId(){
		return id ; 
	}
}
