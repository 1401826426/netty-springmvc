package com.fei.netty.springmvc.rpc.handler.server;

import java.util.List;

import com.fei.netty.springmvc.rpc.socketrpc.RpcServletHttpResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class HttpToRpcResponseHandler extends MessageToMessageEncoder<RpcServletHttpResponse>{

	@Override
	protected void encode(ChannelHandlerContext ctx, RpcServletHttpResponse msg, List<Object> out) throws Exception {
		RpcServletHttpResponse response = (RpcServletHttpResponse)msg ; 
		out.add(response.getRpcResponse()) ; 
	}

}
