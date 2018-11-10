package com.fei.netty.springmvc.rpc;

import java.util.List;

import com.fei.netty.springmvc.rpc.data.response.RpcHttpResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class RpcResponseHandler extends MessageToMessageEncoder<RpcHttpResponse>{

	@Override
	protected void encode(ChannelHandlerContext ctx, RpcHttpResponse msg, List<Object> out) throws Exception {
		RpcHttpResponse response = (RpcHttpResponse)msg ; 
		out.add(response.getRpcResponse()) ; 
	}

}
