package com.fei.netty.springmvc.rpc.handler;

import com.fei.netty.springmvc.rpc.handler.client.RpcClientResponseHandler;
import com.fei.netty.springmvc.rpc.handler.client.RpcClientShakeHandler;
import com.fei.netty.springmvc.rpc.sender.SenderManager;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class InitHandlerV2 extends ChannelInitializer<Channel>{

	private SenderManager senderManager ; 
	
	public InitHandlerV2(SenderManager senderManager) {
		this.senderManager = senderManager ; 
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast("httpClientCodec" , new HttpClientCodec()) ; 
		ch.pipeline().addLast("httpObjectAggregator" , new HttpObjectAggregator(8192)) ;
		ch.pipeline().addLast("rpcClientShakeHandler",new RpcClientShakeHandler()) ;
		ch.pipeline().addLast("rpcClientResponseHandler",new RpcClientResponseHandler(senderManager)) ;  
	}

	
}
