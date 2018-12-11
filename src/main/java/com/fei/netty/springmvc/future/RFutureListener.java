package com.fei.netty.springmvc.future;

public interface RFutureListener<T> {
	
	public void onComplete(T t) ; 
	
}
