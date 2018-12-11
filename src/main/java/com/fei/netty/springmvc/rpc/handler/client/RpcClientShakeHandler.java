package com.fei.netty.springmvc.rpc.handler.client;

import java.net.SocketAddress;

import com.fei.netty.springmvc.log.ErrorLog;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

public class RpcClientShakeHandler extends ChannelHandlerAdapter {

	private RpcClientShakeHand clientShakeHand;

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		this.clientShakeHand = new RpcClientShakeHand();
		this.clientShakeHand.iniFuture(ctx.channel());
		super.connect(ctx, remoteAddress, localAddress, promise);
	}

	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		DefaultFullHttpRequest request = clientShakeHand.buildShakeHandRequest(ctx.channel());
		ctx.pipeline().writeAndFlush(request);
	}



	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof DefaultFullHttpResponse) {
			DefaultFullHttpResponse response = (DefaultFullHttpResponse) msg;
			Channel channel = ctx.channel();
			ChannelFuture future = clientShakeHand.shakeHand(channel, response);
			if (!future.isSuccess()) {
				ErrorLog.error(future.cause());
				ctx.close();
			}
			return;
		}
		ctx.fireChannelRead(msg);
	}

}
