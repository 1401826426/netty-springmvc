package com.fei.netty.springmvc.conf;

public class RpcConf {
	
	private int maxDataLen;
	
	private int responseTimeOut ;//单位,秒 

	public int getMaxDataLen() {
		return maxDataLen;
	}

	public void setMaxDataLen(int maxDataLen) {
		this.maxDataLen = maxDataLen;
	}

	public int getResponseTimeOut() {
		return responseTimeOut;
	}

	public void setResponseTimeOut(int responseTimeOut) {
		this.responseTimeOut = responseTimeOut;
	}

	
	

}
