package com.fei.netty.springmvc.asyn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynExecuteSupport {
	
	private ExecutorService executorService; 
	
	//TODO
	public ExecutorService getExecutorService(){
		if(this.executorService == null){
			this.executorService = Executors.newFixedThreadPool(5) ; 
		}
		return executorService ; 
	}
	
}
