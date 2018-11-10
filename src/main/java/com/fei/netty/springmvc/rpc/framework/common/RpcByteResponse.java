package com.fei.netty.springmvc.rpc.framework.common;

public class RpcByteResponse {
	
	private int status ; 
	
	private byte[] data ;

	private int requestId ; 
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId ; 
	} 
}
