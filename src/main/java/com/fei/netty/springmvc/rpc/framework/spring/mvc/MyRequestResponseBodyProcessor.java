package com.fei.netty.springmvc.rpc.framework.spring.mvc;

import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.fei.netty.springmvc.rpc.framework.RpcInterface;

public class MyRequestResponseBodyProcessor extends RequestResponseBodyMethodProcessor {
	
	public MyRequestResponseBodyProcessor(List<HttpMessageConverter<?>> converters) {
		super(converters);
	}
	

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
//		messageConverters
		return AnnotatedElementUtils.hasAnnotation(parameter.getContainingClass(), RpcInterface.class) ;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), RpcInterface.class) ;
	}
	
	protected boolean checkRequired(MethodParameter parameter) {
		return !parameter.isOptional();
	}
	
	
}
