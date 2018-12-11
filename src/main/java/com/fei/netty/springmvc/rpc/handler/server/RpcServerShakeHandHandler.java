package com.fei.netty.springmvc.rpc.handler.server;

import com.fei.netty.springmvc.conf.RpcConf;
import com.fei.netty.springmvc.log.ErrorLog;
import com.fei.netty.springmvc.rpc.common.RpcHttpRequestPool;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class RpcServerShakeHandHandler extends ChannelHandlerAdapter{ 
	
	private RpcServerShakeHand shakeHand ; 
	
	public RpcServerShakeHandHandler(RpcConf conf){
		RpcHttpRequestPool pool = new RpcHttpRequestPool(); 
		shakeHand = new RpcServerShakeHand(pool, conf) ; 
	}
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof FullHttpRequest){
			FullHttpRequest request = (FullHttpRequest)msg ;
			if(!shakeHand.valid(request)){
				ctx.fireChannelRead(msg) ; 
			}else{
				ChannelFuture future = shakeHand.handshake(ctx) ; 
				if(!future.isSuccess()){
					ErrorLog.error(future.cause());
					ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.NOT_ACCEPTABLE));
				}
			}
			return ; 
		}
		ctx.fireChannelRead(msg) ; 
	}
	
	
	
}











