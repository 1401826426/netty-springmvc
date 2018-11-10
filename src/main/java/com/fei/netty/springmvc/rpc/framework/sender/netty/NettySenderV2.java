package com.fei.netty.springmvc.rpc.framework.sender.netty;

import java.net.SocketAddress;
import java.util.concurrent.LinkedBlockingQueue;

import com.fei.netty.springmvc.rpc.data.request.RpcRequest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettySenderV2 {
	
	private SocketAddress address ; 
	
	private Bootstrap bootstrap ; 
	
	private Channel channel ; 
	
	private LinkedBlockingQueue<RpcRequest> requests = new LinkedBlockingQueue<>() ; 
	
	private void connect(){
		try {
			this.channel = bootstrap.channel(NioSocketChannel.class).connect(address).sync().channel() ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
}
































