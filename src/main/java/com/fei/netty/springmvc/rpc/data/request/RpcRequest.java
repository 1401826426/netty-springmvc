package com.fei.netty.springmvc.rpc.data.request;

import io.netty.buffer.ByteBuf;

public class RpcRequest {
	
	private int requestId ; 
	
	private String command ; 
	
	private ByteBuf data ;	
	
	public RpcRequest(int requestId, String command, ByteBuf data) {
		super();
		this.requestId = requestId;
		this.command = command;
		this.data = data;
	}

	
	public int getRequestId() {
		return requestId;
	}


	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}


	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public ByteBuf getData() {
		return data;
	}

	public void setData(ByteBuf data) {
		this.data = data;
	}

	
	
	
	
	
}
