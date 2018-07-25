package com.fei.test;

import com.fei.netty.springmvc.NettyBootstrap;

public class TestNettyBootstrap {
	
	public static void main(String[] args) {
		NettyBootstrap bootsrap = new NettyBootstrap("classpath:conf.xml"); 
		bootsrap.start();
	}
	
}
