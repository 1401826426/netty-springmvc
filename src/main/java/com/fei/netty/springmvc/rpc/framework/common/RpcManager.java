package com.fei.netty.springmvc.rpc.framework.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcManager {
	
	private static RpcManager instance = new RpcManager() ; 
	
	private Map<Integer,RpcByteRequest> requests = new ConcurrentHashMap<>() ; 
	
	private Map<Integer,RpcByteResponse> responses = new ConcurrentHashMap<>() ; 
	
	public static RpcManager getInstance(){
		return instance ; 
	}

	public RpcByteResponse getResponse(RpcByteRequest request) {
		requests.put(request.getRequestId(),request) ; 
		synchronized (request.getLock()) {
			try {
				request.getLock().wait();
				RpcByteResponse response = responses.remove(request.getRequestId()) ; 
				requests.remove(request.getRequestId()) ; 
				return response ; 
			} catch (InterruptedException e) {
				throw new RuntimeException(e) ; 
			}  
		}
	}
	
	public void putResponse(RpcByteResponse response){
		RpcByteRequest result = requests.get(response.getRequestId()) ; 
		if(result == null){
			//TODO //log
			return; 
		}
		synchronized (result.getLock()) {
			responses.put(response.getRequestId(),response) ; 
			result.getLock().notifyAll();  
		}
	}
	
}











