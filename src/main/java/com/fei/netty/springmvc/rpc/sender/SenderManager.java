package com.fei.netty.springmvc.rpc.sender;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.fei.netty.springmvc.conf.RpcConf;
import com.fei.netty.springmvc.future.DefaultRPromise;
import com.fei.netty.springmvc.future.RFuture;
import com.fei.netty.springmvc.future.RPromise;
import com.fei.netty.springmvc.rpc.exception.TimeOutException;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

public class SenderManager {
	
    private HashedWheelTimer timer ;
	
	private Map<Integer,RPromise<Response>> promises = new ConcurrentHashMap<>() ;
	
	private RpcConf conf ;
	
	public SenderManager(HashedWheelTimer timer , RpcConf conf) {
		super();
		this.conf = conf;
		this.timer = timer ; 
	}

	public RFuture<Response> send(Request request){
		RPromise<Response> promise = new DefaultRPromise<>() ; 
		promises.put(request.getId(),promise) ; 
		timer.newTimeout(new TimerTask() {
			
			@Override
			public void run(Timeout timeout) throws Exception {
				RPromise<Response> f = promises.remove(request.getId()) ;
				f.setFailure(new TimeOutException()) ; 
			}
		}, conf.getResponseTimeOut(), TimeUnit.SECONDS) ;
		request.send() ; 
		return promise ; 
	}

	public void receive(Response response){
		RPromise<Response> promise = promises.remove(response.getId());
		if(promise != null){
			promise.setSuccess(response) ;
		}
	}

	
}
