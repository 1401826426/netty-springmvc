package com.fei.netty.springmvc.rpc.framework.generator;

import java.lang.reflect.Method;

public interface UrlGenerator {
	
	public String generate(Method method) ; 
	
}
