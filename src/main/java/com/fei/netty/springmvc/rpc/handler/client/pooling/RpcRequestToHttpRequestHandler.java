package com.fei.netty.springmvc.rpc.handler.client.pooling;

import java.util.List;

import com.fei.netty.springmvc.rpc.common.RpcRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

public class RpcRequestToHttpRequestHandler extends MessageToMessageEncoder<RpcRequest>{
	
	public final static String REQUEST_ID_HEAD = "requestId" ; 
	
	@Override
	protected void encode(ChannelHandlerContext ctx, RpcRequest rpcRequest, List<Object> out) throws Exception {
		String path = rpcRequest.getCommand();  
		FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.POST,path) ; 
		request.headers().set(HttpHeaders.Names.CONTENT_TYPE,"application/json") ;
		request.headers().set(REQUEST_ID_HEAD,rpcRequest.getRequestId()) ; 
		byte[] bytes = rpcRequest.getData(); 
		request.headers().set(HttpHeaders.Names.CONTENT_LENGTH,bytes.length) ; 
		request.content().writeBytes(rpcRequest.getData()) ;  
		out.add(request) ; 
		return ; 
	}

	

}
