package com.fei.netty.springmvc.deprecated;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class InitChannelHandler extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
//		ChannelPipeline pipeline = ch.pipeline() ; 
//		pipeline.addLast("codec",new HttpClientCodec()) ;
//		ch.pipeline().addLast(new HttpObjectAggregator(65536));
//		pipeline.addLast("senderHandler",new SenderHandler()) ; 
		
	}



}
