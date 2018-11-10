package com.fei.netty.springmvc.rpc;

import java.util.List;

import com.fei.netty.springmvc.rpc.data.response.RpcResponse;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RpcResponseDecoder extends ByteToMessageDecoder{
	
	int dataLen = 0 ; 
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		while(in.readableBytes() > dataLen){
			if(dataLen == 0){
				dataLen = in.readInt() ; 
			}
			if(dataLen < 4){
				throw new RuntimeException("error pacakage dataLen = " + dataLen) ; 
			}
			int requestId = in.readInt() ; 
			ByteBuf data = in.readBytes(dataLen-4) ; 
			RpcResponse response = new RpcResponse(requestId,data) ; 
			out.add(response) ; 
		}
	}

}





