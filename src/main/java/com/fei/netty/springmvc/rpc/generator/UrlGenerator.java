package com.fei.netty.springmvc.rpc.generator;

import java.lang.reflect.Method;

public interface UrlGenerator {
	
	public String generate(Method method) ; 
	
}
