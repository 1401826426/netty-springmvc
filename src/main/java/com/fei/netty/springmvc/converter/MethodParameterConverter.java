package com.fei.netty.springmvc.converter;

import java.lang.reflect.Method;

public interface MethodParameterConverter{
	
	public byte[] serialize(Method method,Object... args) throws ConverterException ; 
	
	public Object[] deserialize(Method method,byte[] value) throws ConverterException;
	
}
