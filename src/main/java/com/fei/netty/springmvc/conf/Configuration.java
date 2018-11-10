package com.fei.netty.springmvc.conf;

public class Configuration {
	
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
	
	
	
}
