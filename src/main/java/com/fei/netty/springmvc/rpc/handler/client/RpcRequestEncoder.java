package com.fei.netty.springmvc.rpc.handler.client;

import com.fei.netty.springmvc.log.ErrorLog;
import com.fei.netty.springmvc.rpc.common.RpcRequest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcRequestEncoder extends MessageToByteEncoder<RpcRequest>{

	@Override
	protected void encode(ChannelHandlerContext ctx, RpcRequest request, ByteBuf out) throws Exception {
		ByteBuf data = request.getData() ; 
		int dataLen = data.readableBytes() + 36 ; 
		byte[] bytes = request.getCommand().getBytes() ; 
		if(bytes.length > 32){
			ErrorLog.getLogger().error("command to large" + request.getCommand());
			return ; 
		}
		out.writeInt(dataLen) ; 
		out.writeInt(request.getRequestId()) ;
		byte[] buf = new byte[32] ; 
		System.arraycopy(bytes, 0,buf,0, bytes.length) ; 
		out.writeBytes(buf) ; 
		out.writeBytes(data) ; 
 	}

}
