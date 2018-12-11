package com.fei.netty.springmvc.rpc.socketrpc.sender;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fei.netty.springmvc.future.DefaultRPromise;
import com.fei.netty.springmvc.future.RFuture;
import com.fei.netty.springmvc.future.RFutureListener;
import com.fei.netty.springmvc.log.ErrorLog;
import com.fei.netty.springmvc.rpc.common.RpcRequest;
import com.fei.netty.springmvc.rpc.common.RpcResponse;
import com.fei.netty.springmvc.rpc.exception.ConnectionException;
import com.fei.netty.springmvc.rpc.handler.client.RpcClientShakeHand;
import com.fei.netty.springmvc.rpc.sender.DefaultRequest;
import com.fei.netty.springmvc.rpc.sender.DefaultResponse;
import com.fei.netty.springmvc.rpc.sender.Request;
import com.fei.netty.springmvc.rpc.sender.Response;
import com.fei.netty.springmvc.rpc.sender.Sender2;
import com.fei.netty.springmvc.rpc.sender.SenderManager;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.Attribute;

public class NettySenderV2 implements Sender2{
	
	private static Logger logger = LoggerFactory.getLogger(NettySenderV2.class) ;  
	
	private Channel channel ; 
	
	private Bootstrap bootstrap ; 
	
	private InetSocketAddress address ; 
	
	private volatile boolean run = true ;  
	
	private SenderManager senderManager ;
	
	private Object channelLock = new Object() ; 
	
	
	public NettySenderV2(Bootstrap bootstrap, InetSocketAddress address, SenderManager senderManager) {
		super();
		this.bootstrap = bootstrap;
		this.address = address;
		this.senderManager = senderManager;
	}
	
	public NettySenderV2(Bootstrap bootstrap, String host,int port, SenderManager senderManager) {
		super();
		this.bootstrap = bootstrap;
		this.address = new InetSocketAddress(host, port) ; 
		this.senderManager = senderManager;
	}

	private Channel openChannel(){
		while(run){
			try {
				ChannelFuture future = bootstrap.connect(address) ;
				future.get() ; 
				channel = future.channel() ;
				boolean active = channel.isActive() ;
				logger.info("acvtive channel" + channel);
				if(active){
					Attribute<ChannelFuture> attribute = channel.attr(RpcClientShakeHand.SHAKE_HAND_FUTURE) ;
					ChannelFuture shakeHandFuture = null ; 
					synchronized (attribute) {
						shakeHandFuture = attribute.get() ;
					}
					shakeHandFuture.sync() ;
					if(shakeHandFuture.isSuccess()){						
						return channel ; 
					}
				}
			} catch (Throwable e) {
				ErrorLog.error(e);
			} 
		}
		ConnectionException e = new ConnectionException("can't connect") ; 
		ErrorLog.error(e);
		throw e ; 
	}
	
	private Channel getChannel(){
		if(this.channel != null && this.channel.isActive()){
			return this.channel ; 
		}
		synchronized (channelLock) {
			if(this.channel != null && this.channel.isActive()){
				return this.channel ; 
			}
			this.channel = openChannel() ; 
			return this.channel; 
		}
	}
	
	public RFuture<RpcResponse> sendRequest(RpcRequest rpcRequest){
		Channel channel = getChannel();
		Request request = new DefaultRequest(channel,rpcRequest) ; 
		RFuture<Response> future = senderManager.send(request) ;
		DefaultRPromise<RpcResponse> result = new DefaultRPromise<>() ;
		future.addListener(new RFutureListener<RFuture<Response>>() {
			
			@Override
			public void onComplete(RFuture<Response> t) {
				if(t.isSuccess()){
					Response response = t.getNow() ; 
					if(response == null){
						result.setFailure(t.cause());
					}
					if(response instanceof DefaultResponse){
						DefaultResponse defaultResponse = (DefaultResponse)response ; 
						result.setSuccess(defaultResponse.getRpcResponse());
					}else{
						result.setFailure(new RuntimeException("illegal response"));
					}
				}else{
					result.setFailure(t.cause());
				}
			}
		});
		return  result ; 
	}
	
}