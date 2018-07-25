package com.fei.netty.springmvc.conf;

public class Configuration {
	
	private NettyConf nettyConf;
	
	private SpringConf springConf ;

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
	
}
