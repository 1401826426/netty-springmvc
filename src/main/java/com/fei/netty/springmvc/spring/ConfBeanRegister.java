package com.fei.netty.springmvc.spring;

import java.util.HashMap;
import java.util.Map;

public class ConfBeanRegister{
	
	private Map<Class<?>,Object> registers = new HashMap<>(); 
	
	public void register(Class<?> clazz , Object obj){
		registers.put(clazz, obj) ; 
	}
	
	
	
}

















