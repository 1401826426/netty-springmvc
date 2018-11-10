package com.fei.netty.springmvc.rpc.framework.sender.netty;

import com.fei.netty.springmvc.rpc.framework.common.RpcByteRequest;
import com.fei.netty.springmvc.rpc.framework.common.RpcByteResponse;
import com.fei.netty.springmvc.rpc.framework.common.RpcManager;
import com.fei.netty.springmvc.rpc.framework.sender.AbstractSender;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettySender extends AbstractSender{
	
	private Bootstrap  bootstrap ; 
	
	public void ini(){
		this.bootstrap = new Bootstrap(); 
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup() ;
		bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new InitChannelHandler()) ; 
	}
	
	public Channel getChannel(String host,int port){
		try {
			return bootstrap.connect(host,port).sync().channel() ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		throw new RuntimeException("网络连接出错") ; 
	}

	@Override
	public RpcByteResponse send(RpcByteRequest request) {
		Channel channel = getChannel(request.getHost(),request.getPort());
		channel.writeAndFlush(request);
		RpcByteResponse response = RpcManager.getInstance().getResponse(request) ; 
		return response ; 
	}

	public static void main(String[] args) {
//		User user = new User() ; 
//		user.setName("245");
		
//		user.setPassword("123");
//		TestDto testDto = new TestDto() ; 
//		testDto.setName("234");
//		testDto.setPassword("456");
//		RpcByteRequest request = new RpcByteRequest(1,"http://localhost:8080/testRpc",new Object[]{testDto})  ;
//		Sender sender = new NettySender() ; 
//		sender.send(request) ; 
//		UserDto user
		
	}	
}
