package com.fei.netty.springmvc.rpc.common;

import io.netty.buffer.ByteBuf;

public class RpcRequest {
	
	private int requestId ; 
	
	private String command ; 
	
	private byte[] data ;	
	
	private Object[] args ; 
	
	public RpcRequest(int requestId, String command, byte[] data) {
		super();
		this.requestId = requestId;
		this.command = command;
		this.data = data;
	}

	
	public RpcRequest(int requestId, String command, ByteBuf data) {
		super();
		this.requestId = requestId;
		this.command = command;
		this.data = new byte[data.readableBytes()];
		data.readBytes(this.data) ; 
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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public Object[] getArgs() {
		return args;
	}
	
	public void setArgs(Object[] args) {
		this.args = args;
	}

	public Object getArgs(int index){
		return args[index] ; 
	}


	public byte[] getBytes() {
		return data;
	}
	
	
	
	
}
