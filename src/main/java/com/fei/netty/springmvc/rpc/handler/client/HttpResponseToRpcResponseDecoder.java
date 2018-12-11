package com.fei.netty.springmvc.rpc.handler.client;

import java.util.List;

import com.fei.netty.springmvc.rpc.common.RpcResponse;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;

public class HttpResponseToRpcResponseDecoder extends MessageToMessageDecoder<DefaultFullHttpResponse>{
	
	public final static String REQUEST_ID_HEAD = "requestId" ; 
	
	private ByteBufAllocator allocator ; 
	
	@Override
	protected void decode(ChannelHandlerContext ctx, DefaultFullHttpResponse msg, List<Object> out) throws Exception {
		int requestId = HttpHeaders.getIntHeader(msg, REQUEST_ID_HEAD, -1) ;
		byte[] result = msg.content().array() ; 
		ByteBuf data = allocator.heapBuffer(result.length) ; 
		data.writeBytes(data) ; 
		RpcResponse response = new RpcResponse(requestId,data) ;
		out.add(response) ; 
	}

}








