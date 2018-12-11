package com.fei.netty.springmvc.rpc.handler.client;

import com.fei.netty.springmvc.rpc.common.RpcResponse;
import com.fei.netty.springmvc.rpc.sender.DefaultResponse;
import com.fei.netty.springmvc.rpc.sender.Response;
import com.fei.netty.springmvc.rpc.sender.SenderManager;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class RpcClientResponseHandler extends ChannelHandlerAdapter{
	
	private SenderManager senderManager ; 
	
	public RpcClientResponseHandler(SenderManager senderManager){
		this.senderManager = senderManager ; 
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof RpcResponse){
			RpcResponse rpcResponse = (RpcResponse)msg ;
			Response response = new DefaultResponse(rpcResponse, ctx.channel()) ; 
			senderManager.receive(response);
			return ;
		}
		ctx.writeAndFlush(msg) ; 
	}
	
	
	
}
