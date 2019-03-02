package com.fei.netty.springmvc.rpc.spring.mvc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import com.fei.netty.springmvc.converter.MethodParameterConverter;
import com.fei.netty.springmvc.converter.fackson.DataNodeMethodParameterConverter;
import com.fei.netty.springmvc.rpc.RpcInterface;
import com.fei.netty.springmvc.rpc.common.RpcRequest;
import com.fei.netty.springmvc.rpc.socketrpc.RpcHttpServletRequest;

public class RpcHandlerMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver implements InitializingBean{
	
	private MethodParameterConverter methodParameterConverter ; 

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return AnnotatedElementUtils.hasAnnotation(parameter.getContainingClass(), RpcInterface.class) ;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		NamedValueInfo info = new NamedValueInfo("@Value", true, null) ; 
		return info;
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		RpcHttpServletRequest rpcHttpServletRequest = request.getNativeRequest(RpcHttpServletRequest.class) ; 
		RpcRequest rpcRequest =rpcHttpServletRequest.getRpcRequest() ;
		Object[] args = rpcRequest.getArgs() ; 
		if(args == null){
			args = methodParameterConverter.deserialize(parameter.getMethod(), rpcRequest.getData()) ; 
			rpcRequest.setArgs(args);
		}
		return args[parameter.getParameterIndex()] ; 
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(methodParameterConverter == null){
			this.methodParameterConverter = new DataNodeMethodParameterConverter() ; 
		}
	}

}
