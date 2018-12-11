package com.fei.netty.springmvc.rpc.handler.client.pooling;

import java.util.List;

import com.fei.netty.springmvc.rpc.common.RpcRequest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

public class HttpRequestToRpcRequestHandler extends MessageToMessageDecoder<DefaultFullHttpRequest>{
	
	@Override
	protected void decode(ChannelHandlerContext ctx, DefaultFullHttpRequest msg, List<Object> out) throws Exception {
		int requestId = HttpHeaders.getIntHeader(msg, RpcRequestToHttpRequestHandler.REQUEST_ID_HEAD,-1) ; 
		if(requestId <= 0){
			ByteBuf data = msg.content() ;
			String url = msg.getUri() ; 
			RpcRequest request = new RpcRequest(requestId, url, data) ;
			out.add(request) ; 
		}
	}

}
