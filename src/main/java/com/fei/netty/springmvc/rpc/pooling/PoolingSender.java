package com.fei.netty.springmvc.rpc.pooling;

import java.net.InetSocketAddress;

import com.fei.netty.springmvc.rpc.common.RpcRequest;
import com.fei.netty.springmvc.rpc.sender.AbstractSender2;
import com.fei.netty.springmvc.rpc.sender.Request;
import com.fei.netty.springmvc.rpc.sender.SenderManager;
import io.netty.bootstrap.Bootstrap;

public class PoolingSender extends AbstractSender2{

	public PoolingSender(Bootstrap bootstrap, InetSocketAddress address, SenderManager senderManager) {
		super(bootstrap, address, senderManager);
	}

	@Override
	protected Request buildRequest(RpcRequest rpcRequest) {
//		Channel channel 
		return null;
	}

	

}
