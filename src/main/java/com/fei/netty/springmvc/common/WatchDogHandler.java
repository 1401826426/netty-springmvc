package com.fei.netty.springmvc.common;

import java.net.SocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class WatchDogHandler extends ChannelHandlerAdapter{
	
	private SocketAddress address ; 
	
	private Bootstrap bootstrap ; 
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if(bootstrap.group().isShuttingDown()){
			return ; 
		}
		ctx.connect(address) ; 
	}
	
	
	
}




















