package com.fei.netty.springmvc.conf;

import com.fei.netty.springmvc.converter.MethodParameterConverter;

import io.netty.buffer.ByteBufAllocator;

public class Configuration implements Initializer{
	
	private NettyConf nettyConf;
	
	private SpringConf springConf ;
	
	private RpcConf rpcConf ; 

	public NettyConf getNettyConf() {
		return nettyConf;
	}

	public void setNettyConf(NettyConf nettyConf) {
		this.nettyConf = nettyConf;
	}

	public SpringConf getSpringConf() {
		return springConf;
	}

	public void setSpringConf(SpringConf springConf) {
		this.springConf = springConf;
	}

	public RpcConf getRpcConf() {
		return rpcConf;
	}

	public void setRpcConf(RpcConf rpcConf) {
		this.rpcConf = rpcConf;
	} 
	
	public ByteBufAllocator getAllocator(){
		return nettyConf.getByteBufAllocator() ; 
	}

	@Override
	public void ini() {
		this.nettyConf.ini();  
		this.rpcConf.ini();  
		this.springConf.ini();
	}

	
}
