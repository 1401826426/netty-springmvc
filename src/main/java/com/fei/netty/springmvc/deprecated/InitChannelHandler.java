package com.fei.netty.springmvc.deprecated;

import org.springframework.web.servlet.DispatcherServlet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class InitChannelHandler extends ChannelInitializer<Channel>{
	
	private DispatcherServlet dispatcherServlet ; 
	
	public InitChannelHandler(DispatcherServlet dispatcherServlet){
		this.dispatcherServlet = dispatcherServlet ;
	}
	
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast(new HttpServerCodec()) ; 
	    ch.pipeline().addLast(new HttpObjectAggregator(65536));
		ch.pipeline().addLast(new HttpServerHandler(dispatcherServlet)) ; 
		
	}
	
}
