package com.fei.netty.springmvc.redis.script;

public interface JedisScript {
	
	public Object createUser(long userId,String name,int followers,int following); 
	
}
