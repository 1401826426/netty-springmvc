package com.fei.netty.springmvc.rpc.sender;

import com.fei.netty.springmvc.conf.RpcConf;
import com.fei.netty.springmvc.rpc.handler.InitHandlerV2;
import com.fei.netty.springmvc.rpc.socketrpc.sender.NettySenderV2;
import com.fei.netty.springmvc.zookeeper.server.Server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.HashedWheelTimer;

public class NettySenderFactory {
	
	private Bootstrap bootstrap ; 
	
	private SenderManager senderManager ;
	
	private RpcConf conf ; 
	
	public NettySenderFactory(RpcConf conf){
		this.conf = conf ; 
		ini() ; 
	}

	private void ini() {
		HashedWheelTimer timer = new HashedWheelTimer() ; 
		this.senderManager = new SenderManager(timer,conf)  ; 
		
		NioEventLoopGroup group = new NioEventLoopGroup() ; 
		this.bootstrap = new Bootstrap() ; 
		bootstrap.group(group)
		         .channel(NioSocketChannel.class)
		         .handler(new InitHandlerV2(senderManager)) ;
		
	}
	
	public NettySenderV2 getSender(Server server){
		return new NettySenderV2(bootstrap, server.getHost(),server.getPort(),senderManager) ; 
	}
}
