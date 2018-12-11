package com.fei.netty.springmvc.redis.spring;

import org.springframework.beans.factory.FactoryBean;

import com.fei.netty.springmvc.redis.JedisClientManager;

public class JedisInterfaceFactoryBean<T> implements FactoryBean<T>{
	
	Class<T> clazz ;
	
	JedisClientManager jedisClientManager ; 
	
	
	public JedisInterfaceFactoryBean(Class<T> clazz) {
		this.clazz = clazz ; 
	}

	
	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}



	public JedisClientManager getJedisClientManager() {
		return jedisClientManager;
	}

	public void setJedisClientManager(JedisClientManager jedisClientManager) {
		this.jedisClientManager = jedisClientManager;
//		this.jedisClientManager.setInterface(clazz);
	}

	@Override
	public T getObject() throws Exception {
		return (T)jedisClientManager.getInterface(clazz);
	}

	@Override
	public Class<?> getObjectType() {
		return clazz;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
