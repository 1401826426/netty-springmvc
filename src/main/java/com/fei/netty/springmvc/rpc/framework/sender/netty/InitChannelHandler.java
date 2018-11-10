package com.fei.netty.springmvc.rpc.framework.sender.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class InitChannelHandler extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline() ; 
		pipeline.addLast("codec",new HttpClientCodec()) ;
		ch.pipeline().addLast(new HttpObjectAggregator(65536));
		pipeline.addLast("senderHandler",new SenderHandler()) ; 
		
	}



}
