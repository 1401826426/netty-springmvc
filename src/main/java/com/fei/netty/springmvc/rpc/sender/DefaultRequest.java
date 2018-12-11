package com.fei.netty.springmvc.rpc.sender;

import com.fei.netty.springmvc.rpc.common.RpcRequest;

import io.netty.channel.Channel;

public class DefaultRequest implements Request{
	
	private Channel channel ;
	
	private RpcRequest rpcRequest ; 
	
	public DefaultRequest(Channel channel, RpcRequest rpcRequest) {
		super();
		this.channel = channel;
		this.rpcRequest = rpcRequest;
	}

	@Override
	public void send() {
		channel.writeAndFlush(rpcRequest) ; 
	}

	@Override
	public int getId() {
		return rpcRequest.getRequestId(); 
	}	
}
