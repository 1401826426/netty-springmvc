package com.fei.netty.springmvc.rpc.handler.server;

import java.util.List;

import com.fei.netty.springmvc.conf.RpcConf;
import com.fei.netty.springmvc.log.RpcLog;
import com.fei.netty.springmvc.rpc.common.RpcRequest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RpcRequestDecoder extends ByteToMessageDecoder{
	
	private int dataLen = 0;
	
	private RpcConf conf ; 

	
	public RpcRequestDecoder(RpcConf conf) {
		super();
		this.conf = conf;
	}



	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		while(in.readableBytes() > dataLen){
			if(dataLen == 0){
				dataLen = in.readInt() ;
			}
			if(dataLen < 36){
				throw new RuntimeException("error package dataLen="+dataLen);
			}
			if(dataLen > conf.getMaxDataLen()){
				throw new RuntimeException("package to large dataLen="+dataLen + ",maxLen=" + conf.getMaxDataLen()); 
			}
			if(in.readableBytes() < dataLen){
				return  ; 
			}
			int requestId = in.readInt() ; 
			byte[] cmdBytes = in.readBytes(32).array() ;
			String cmd = new String(cmdBytes) ;
			ByteBuf data = in.readBytes(dataLen-36) ; 
			RpcRequest rpcRequest = new RpcRequest(requestId,cmd,data) ; 
			RpcLog.logRpcRequest(rpcRequest);
			out.add(rpcRequest) ;
			dataLen = 0 ; 
		}
		
	}

	
	
	
	
}
