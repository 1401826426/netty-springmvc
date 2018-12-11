package com.fei.netty.springmvc.redis;

public enum Channel {
	
	LOG("log") , 
	;
	
	String channel ; 
	
	private Channel(String channel){
		this.channel = channel ; 
	}
	
	public String getChannel(){
		return channel;
	}
	
}
