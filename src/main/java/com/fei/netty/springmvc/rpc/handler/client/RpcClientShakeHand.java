package com.fei.netty.springmvc.rpc.handler.client;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fei.netty.springmvc.rpc.exception.ShakeHandException;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class RpcClientShakeHand {
	
	public final static AttributeKey<ChannelFuture> SHAKE_HAND_FUTURE = AttributeKey.<ChannelFuture>valueOf("shakeHandFuture") ; 
	
	private static CharSequence RPC_SHAKE_HAND = "rpcShakeHand" ; 
	
	private static Logger logger = LoggerFactory.getLogger(RpcClientShakeHand.class) ; 
	
	public RpcClientShakeHand() {
		super(); 
	}
	
	public void iniFuture(Channel channel){
		logger.info("===add promise======" + channel);
		Attribute<ChannelFuture> shakeHandAttribute = channel.attr(RpcClientShakeHand.SHAKE_HAND_FUTURE) ;		
		shakeHandAttribute.set(new DefaultChannelPromise(channel));
	}

	public DefaultFullHttpRequest buildShakeHandRequest(Channel channel){
		InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress() ;
		String url = "rpc//" + socketAddress.getHostString() + ":" + socketAddress.getPort() ;   
		DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,url) ;
		HttpHeaders.addIntHeader(request, RPC_SHAKE_HAND, 1);
		HttpHeaders.addHeader(request, Names.CONNECTION, "keep-alive");
		return request ; 
	}


	public ChannelFuture shakeHand(Channel channel,DefaultFullHttpResponse response) {
		Attribute<ChannelFuture> shakeHandAttribute = channel.attr(RpcClientShakeHand.SHAKE_HAND_FUTURE) ;
		ChannelPromise shakeHandFuture = (ChannelPromise)shakeHandAttribute.get() ; 
		ChannelPromise promise = new DefaultChannelPromise(channel) ;
		if(response.getStatus().code() != HttpResponseStatus.OK.code()){
			promise.setFailure(new ShakeHandException("shake hand response error")) ;
			shakeHandFuture.setFailure(new ShakeHandException("shake hand response error")) ; 
			return promise;  
		}
		ChannelHandlerContext ctx = channel.pipeline().context(HttpClientCodec.class) ; 
		if(ctx != null){
			channel.pipeline().addBefore(ctx.name(), "requestEncoder", new RpcRequestEncoder()) ; 
			channel.pipeline().addBefore(ctx.name(), "responseDecoder", new RpcResponseDecoder()) ;
			channel.pipeline().remove(ctx.handler()) ; 
		}else{
			ctx = channel.pipeline().context(HttpRequestEncoder.class); 
			if(ctx == null){
				promise.setFailure(new ShakeHandException("no httpRequestEncoder")) ;
				shakeHandFuture.setFailure(new ShakeHandException("no httpRequestEncoder")) ;
				return promise ; 
			}
			ChannelHandlerContext responseCtx = channel.pipeline().context(HttpResponseDecoder.class) ; 
			if(responseCtx == null){
				promise.setFailure(new ShakeHandException("no httpRespontDecoder")) ;
				shakeHandFuture.setFailure(new ShakeHandException("no httpRespontDecoder")) ;
				return promise ;
			}
			channel.pipeline().addBefore(ctx.name(), "requestEncoder", new RpcRequestEncoder()) ; 
			channel.pipeline().addBefore(ctx.name(), "responseDecoder", new RpcResponseDecoder()) ;
			channel.pipeline().remove(ctx.handler()) ; 
			channel.pipeline().remove(responseCtx.handler()) ; 
		}
		shakeHandFuture.setSuccess() ; 
		promise.setSuccess() ; 
		return promise;
	}
	
}

