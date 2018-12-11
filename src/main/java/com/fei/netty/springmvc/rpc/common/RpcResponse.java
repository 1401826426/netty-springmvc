package com.fei.netty.springmvc.rpc.common;

import io.netty.buffer.ByteBuf;

public class RpcResponse {
	
	private int requestId; 
	
	private ByteBuf data ;
	
	
	
	public RpcResponse(int requestId, ByteBuf data) {
		super();
		this.requestId = requestId;
		this.data = data;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public ByteBuf getData() {
		return data;
	}

	public void setData(ByteBuf data) {
		this.data = data;
	}

	
	
	
}
