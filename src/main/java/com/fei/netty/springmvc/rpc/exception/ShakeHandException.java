package com.fei.netty.springmvc.rpc.exception;

public class ShakeHandException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ShakeHandException() {
		super();

	}

	public ShakeHandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public ShakeHandException(String message, Throwable cause) {
		super(message, cause);

	}

	public ShakeHandException(String message) {
		super(message);

	}

	public ShakeHandException(Throwable cause) {
		super(cause);

	}
	
	
	
}
