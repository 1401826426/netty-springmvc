package com.fei.netty.springmvc.rpc;

import com.fei.netty.springmvc.rpc.data.request.RpcHttpRequest;
import com.fei.netty.springmvc.rpc.data.request.RpcHttpRequestPool;
import com.fei.netty.springmvc.rpc.data.request.RpcRequest;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class RpcRequestHandler extends ChannelHandlerAdapter{
	
	private RpcHttpRequestPool pool ; 
	
	
	
	public RpcRequestHandler(RpcHttpRequestPool pool) {
		super();
		this.pool = pool;
	}



	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof RpcRequest){
			RpcRequest rpcRequest = (RpcRequest)msg ;
			RpcHttpRequest httpRequest = null ; 
			try{
				httpRequest = (RpcHttpRequest)pool.borrowObject() ;
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
