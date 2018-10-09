package com.fei.netty.springmvc.conf;

public class NettyConf {
	
	private int port  ;

	private int sessionInvalidTime ; 
	
	private int sessionCheckInterval; 
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getSessionInvalidTime() {
		return sessionInvalidTime;
	}

	public void setSessionInvalidTime(int sessionInvalidTime) {
		this.sessionInvalidTime = sessionInvalidTime;
	}

	public int getSessionCheckInterval() {
		return sessionCheckInterval;
	}

	public void setSessionCheckInterval(int sessionCheckInterval) {
		this.sessionCheckInterval = sessionCheckInterval;
	}

	
	
	
}
