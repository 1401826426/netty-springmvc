package com.fei.netty.springmvc.rpc.sender;

import com.fei.netty.springmvc.rpc.common.RpcResponse;

import io.netty.channel.Channel;

public class DefaultResponse implements Response{
	
	private RpcResponse rpcResponse;
	
	private Channel channel ;
	
	public DefaultResponse(RpcResponse rpcResponse, Channel channel) {
		super();
		this.rpcResponse = rpcResponse;
		this.channel = channel;
	}

	
	public RpcResponse getRpcResponse() {
		return rpcResponse;
	}
	
	public void setRpcResponse(RpcResponse rpcResponse) {
		this.rpcResponse = rpcResponse;
	}



	public Channel getChannel() {
		return channel;
	}



	public void setChannel(Channel channel) {
		this.channel = channel;
	}


	@Override
	public int getId() {
		return rpcResponse.getRequestId();
	}

}
