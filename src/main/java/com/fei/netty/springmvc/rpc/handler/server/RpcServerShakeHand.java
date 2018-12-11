package com.fei.netty.springmvc.rpc.handler.server;

import com.fei.netty.springmvc.conf.RpcConf;
import com.fei.netty.springmvc.rpc.common.RpcHttpRequestPool;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;

public class RpcServerShakeHand {
	
	private static CharSequence RPC_SHAKE_HAND = "rpcShakeHand" ; 
	
	private  RpcHttpRequestPool pool ; 
	
	private RpcConf conf ; 
	
	public RpcServerShakeHand(RpcHttpRequestPool pool, RpcConf conf) {
		super();
		this.pool = pool;
		this.conf = conf;
	}

	private DefaultFullHttpResponse getSuccessResponse(){
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK) ;
		HttpHeaders.addHeader(response, Names.CONTENT_TYPE, "application/json");
		HttpHeaders.addHeader(response, Names.CONTENT_LENGTH, 0);
		return response;  
	}
	
	public ChannelFuture handshake(ChannelHandlerContext ctx){
		Channel channel = ctx.channel() ; 
		ChannelPromise promise = new DefaultChannelPromise(channel) ;
		ChannelPipeline pipeLine = channel.pipeline() ; 
		ChannelHandlerContext requestCtx ;
		final ChannelHandlerContext responseEncoderCtx ;
		if(pipeLine.context(HttpRequestDecoder.class)  != null){
			requestCtx = pipeLine.context(HttpRequestDecoder.class) ; 
			responseEncoderCtx = pipeLine.context(HttpResponseEncoder.class) ;
			if(responseEncoderCtx == null){
				promise.setFailure(new RuntimeException("no requestDecoder")) ;
				return promise; 
			}
		}else{
			responseEncoderCtx = requestCtx = pipeLine.context(HttpServerCodec.class) ;
			if(requestCtx == null){
				promise.setFailure(new RuntimeException("no requestDecoder")) ;
				return promise ; 
			}
		}
		ctx.writeAndFlush(getSuccessResponse()).addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()){
					try{
						pipeLine.addBefore(responseEncoderCtx.name(), "rpcEncoder", new RpcResponseEncoder()) ;
						pipeLine.addBefore(requestCtx.name(), "rpcDecoder", new RpcRequestDecoder(conf)) ;
						pipeLine.addAfter("rpcDecoder", "rpcRequestToHttpHandler", new RpcRequestToHttpHandler(pool));
						pipeLine.addAfter("rpcEncoder", "httpToRpcResponseHandler", new HttpToRpcResponseHandler()) ; 
						future.channel().pipeline().remove(responseEncoderCtx.handler()) ;
						future.channel().pipeline().remove(requestCtx.handler()) ;
						HttpObjectAggregator aggregator = future.channel().pipeline().get(HttpObjectAggregator.class) ; 
						if(aggregator != null){
							future.channel().pipeline().remove(aggregator) ; 
						}
						promise.setSuccess() ;
					}catch(Exception e){
						promise.setFailure(e) ; 
					}
				}else{
					promise.setFailure(future.cause()) ; 
				}
			}
		}) ;
		return promise ; 
	}

	public boolean valid(FullHttpRequest request) {
		if(request.headers().contains(RPC_SHAKE_HAND)){
			return true; 
		}
		return false;
	}
	
}
