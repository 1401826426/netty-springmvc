package com.fei.netty.springmvc.rpc.spring.mvc;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fei.netty.springmvc.converter.Converter;
import com.fei.netty.springmvc.converter.fackson.FackJsonConverter;
import com.fei.netty.springmvc.rpc.RpcInterface;
import com.fei.netty.springmvc.rpc.common.RpcRequest;
import com.fei.netty.springmvc.rpc.common.RpcResponse;
import com.fei.netty.springmvc.rpc.socketrpc.RpcHttpServletRequest;
import com.fei.netty.springmvc.rpc.spring.SpringConstants;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;

public class RpcHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler{
	
	private Converter converter ;  
	
	public RpcHandlerMethodReturnValueHandler() {
		super();
		this.converter = new FackJsonConverter();
	}

	public RpcHandlerMethodReturnValueHandler(Converter converter){
		this.converter = converter;
	}
	
	@Override
	public boolean supportsReturnType(MethodParameter parameter) { 
		return AnnotatedElementUtils.hasAnnotation(parameter.getContainingClass(), RpcInterface.class) ;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		byte[] bytes = converter.writeValue(returnValue) ;
		ByteBuf byteBuf = new UnpooledByteBufAllocator(false).buffer(bytes.length); 
		byteBuf.writeBytes(bytes) ; 
		RpcHttpServletRequest rpcHttpServletRequest = webRequest.getNativeRequest(RpcHttpServletRequest.class) ; 
		RpcRequest rpcRequest =rpcHttpServletRequest.getRpcRequest() ;
		int requestId = rpcRequest.getRequestId() ;
		RpcResponse rpcResponse = new RpcResponse(requestId, byteBuf) ; 
		mavContainer.getModel().addAttribute(SpringConstants.RPC_RESPONSE, rpcResponse) ;
		mavContainer.setViewName(SpringConstants.RPC_VIEW_NAME);
	}

}








