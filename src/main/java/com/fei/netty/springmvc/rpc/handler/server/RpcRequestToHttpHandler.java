package com.fei.netty.springmvc.rpc.handler.server;

import com.fei.netty.springmvc.rpc.common.RpcHttpRequestPool;
import com.fei.netty.springmvc.rpc.common.RpcRequest;
import com.fei.netty.springmvc.rpc.socketrpc.RpcHttpServletRequest;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class RpcRequestToHttpHandler extends ChannelHandlerAdapter{
	
	private RpcHttpRequestPool pool ; 

	
	
	public RpcRequestToHttpHandler(RpcHttpRequestPool pool) {
		super();
		this.pool = pool;
	}



	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof RpcRequest){
			RpcRequest rpcRequest = (RpcRequest)msg ;
			RpcHttpServletRequest httpRequest = null ; 
			try{
				httpRequest = (RpcHttpServletRequest)pool.borrowObject() ;
				httpRequest.setRpcRequest(rpcRequest);
				ctx.fireChannelRead(httpRequest) ;
				return ; 
			}finally {
				if(httpRequest != null){					
					pool.returnObject(httpRequest);
				}
			}
		}
		ctx.fireChannelRead(msg) ; 
	}
	
	
	
}
