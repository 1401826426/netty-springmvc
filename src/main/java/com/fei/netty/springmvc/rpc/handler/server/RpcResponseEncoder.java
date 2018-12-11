package com.fei.netty.springmvc.rpc.handler.server;

import com.fei.netty.springmvc.log.RpcLog;
import com.fei.netty.springmvc.rpc.common.RpcResponse;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcResponseEncoder extends MessageToByteEncoder<RpcResponse>{

	@Override
	protected void encode(ChannelHandlerContext ctx, RpcResponse msg, ByteBuf out) throws Exception {
		if(msg instanceof RpcResponse){
			ByteBuf data = msg.getData() ;
			int dataLen = data.readableBytes() + 4 ;
			out.writeInt(dataLen) ; 
			out.writeInt(msg.getRequestId()) ; 
			out.writeBytes(data) ;
			RpcLog.logRpcResponse(msg);
		}
	}
	
	
	
}
