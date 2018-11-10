package com.fei.netty.springmvc.rpc.framework.sender;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.fei.netty.springmvc.conf.RpcConf;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

public class SenderManager {
	
    private HashedWheelTimer timer ;
	
	private Map<Integer,RFuture> futures = new ConcurrentHashMap<>() ;
	
	private RpcConf conf ; 
	
	public RFuture send(Request request){
		RFuture future = new RFuture(request) ;
		futures.put(request.getId(),future) ; 
		timer.newTimeout(new TimerTask() {
			
			@Override
			public void run(Timeout timeout) throws Exception {
				futures.remove(request.getId()) ;
			}
		}, conf.getResponseTimeOut(), TimeUnit.SECONDS) ;
		request.send() ; 
		return future ; 
	}

	public void receive(Response response){
		RFuture future = futures.remove(response.getId());
		if(future != null){
			future.success(response) ; 
		}
	}

	
}
