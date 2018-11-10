package com.fei.netty.springmvc.rpc;

import com.fei.netty.springmvc.conf.RpcConf;
import com.fei.netty.springmvc.log.ErrorLog;
import com.fei.netty.springmvc.rpc.data.request.RpcHttpRequestPool;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class RpcShakeHandHandler extends ChannelHandlerAdapter{ 
	
	private RpcShakeHand shakeHand ; 
	
	public RpcShakeHandHandler(RpcConf conf){
		RpcHttpRequestPool pool = new RpcHttpRequestPool(); 
		shakeHand = new RpcShakeHand(pool, conf) ; 
	}
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof FullHttpRequest){
			FullHttpRequest request = (FullHttpRequest)msg ;
			if(!shakeHand.valid(request)){
				ctx.fireChannelRead(msg) ; 
			}
			ChannelFuture future = shakeHand.handshake(ctx) ; 
			if(future.isSuccess()){
				ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK)) ; 
			}else{
				ErrorLog.error(future.cause());
				ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.NOT_ACCEPTABLE));
			}
			return ; 
		}
		ctx.fireChannelRead(msg) ; 
	}
	
	
	
}











