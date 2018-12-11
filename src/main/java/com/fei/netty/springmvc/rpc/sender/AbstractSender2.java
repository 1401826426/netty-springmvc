package com.fei.netty.springmvc.rpc.sender;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fei.netty.springmvc.future.DefaultRPromise;
import com.fei.netty.springmvc.future.RFuture;
import com.fei.netty.springmvc.future.RFutureListener;
import com.fei.netty.springmvc.future.RPromise;
import com.fei.netty.springmvc.log.ErrorLog;
import com.fei.netty.springmvc.rpc.common.RpcRequest;
import com.fei.netty.springmvc.rpc.common.RpcResponse;
import com.fei.netty.springmvc.rpc.exception.ConnectionException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public abstract class AbstractSender2 implements Sender2{
	
	private static Logger logger = LoggerFactory.getLogger(AbstractSender2.class) ;  
	
	private Channel channel ; 
	
	private Bootstrap bootstrap ; 
	
	private InetSocketAddress address ; 
	
	private volatile boolean run = true ;  
	
	private SenderManager senderManager ;
	
	private Object channelLock = new Object() ;
	
	public AbstractSender2(Bootstrap bootstrap, InetSocketAddress address, SenderManager senderManager) {
		super();
		this.bootstrap = bootstrap;
		this.address = address;
		this.senderManager = senderManager;
	}
	
	public AbstractSender2(Bootstrap bootstrap, String host,int port, SenderManager senderManager) {
		this(bootstrap,new InetSocketAddress(host, port),senderManager) ; 
	}
	
	protected Channel openChannel(){
		while(run){
			try {
				ChannelFuture future = bootstrap.connect(address) ;
				future.sync() ;  
				Channel channel = future.channel() ;
				boolean active = channel.isActive() ;
				logger.info("acvtive channel" + channel);
				if(active){					
					doAfterChannelActive(channel) ;
					return channel ; 
				}
			} catch (Throwable e) {
				ErrorLog.error(e);
			} 
		}
		ConnectionException e = new ConnectionException("can't connect") ; 
		ErrorLog.error(e);
		throw e ; 
	}
	
	protected void doAfterChannelActive(Channel channel) {
		
	}

	protected Channel getChannel(){
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

	@Override
	public RFuture<RpcResponse> sendRequest(RpcRequest rpcRequest) {
		RPromise<RpcResponse> promise = new DefaultRPromise<>() ;  
		try{
			Request request = buildRequest(rpcRequest) ;  
			RFuture<Response> future = senderManager.send(request); 
			future.addListener(new RFutureListener<RFuture<Response>>() {
				@Override
				public void onComplete(RFuture<Response> t) {
					if(t.isSuccess()){
						Response response = t.getNow() ; 
						if(response == null){
							promise.setFailure(new RuntimeException("no response"));
						}
						promise.setSuccess(response.getRpcResponse());
					}else{
						promise.setFailure(t.cause());
					}
				}
			});
 		}catch(Exception e){
			promise.setFailure(e);
		}
		return null;
	}

	protected abstract Request buildRequest(RpcRequest rpcRequest) ;
	
	


}










