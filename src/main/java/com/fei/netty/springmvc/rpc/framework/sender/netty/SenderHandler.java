package com.fei.netty.springmvc.rpc.framework.sender.netty;

import com.fei.netty.springmvc.rpc.framework.common.RpcByteRequest;
import com.fei.netty.springmvc.rpc.framework.common.RpcByteResponse;
import com.fei.netty.springmvc.rpc.framework.common.RpcManager;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

public class SenderHandler extends ChannelHandlerAdapter{

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if(msg instanceof RpcByteRequest){
			RpcByteRequest rpcRequest = (RpcByteRequest)msg ;
//			URL url = rpcRequest.getUrl() ;  
			FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.POST,rpcRequest.getPath()) ; 
			request.headers().set(HttpHeaders.Names.CONTENT_TYPE,"application/json") ;
			request.headers().set("requestId",rpcRequest.getRequestId()) ; 
			byte[] bytes = rpcRequest.packBytes() ; 
			request.headers().set(HttpHeaders.Names.CONTENT_LENGTH,bytes.length) ;
			request.headers().set(HttpHeaders.Names.HOST,rpcRequest.getHost()) ; 
			request.content().writeBytes(bytes) ; 
			ctx.write(request, promise);
			return ; 
		}
		super.write(ctx, msg, promise);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof DefaultFullHttpResponse){
			DefaultFullHttpResponse response = (DefaultFullHttpResponse)msg ;
			RpcByteResponse rpcResponse = new RpcByteResponse() ;
			ByteBuf content = response.content() ; 
			if(content != null){
				byte[] bytes = new byte[content.readableBytes()] ; 
				content.readBytes(bytes) ; 
				rpcResponse.setData(bytes);
			}
			rpcResponse.setStatus(response.getStatus().code()); 
			String requestId = response.headers().get("requestId") ; 
			if(requestId == null){
				System.err.println(response.getStatus() + " " + response.getProtocolVersion()+ " "+response);
				return ; 
			}
			try{				
				rpcResponse.setRequestId(Integer.parseInt(requestId)) ;
				RpcManager.getInstance().putResponse(rpcResponse);
			}catch (Exception e) {
				//TODO log
				e.printStackTrace(); 
			}
			return ; 
		}
		super.channelRead(ctx, msg);
	}
	
	
	
	
	
	
	
}
