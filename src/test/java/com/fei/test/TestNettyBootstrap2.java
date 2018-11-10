package com.fei.test;

import com.fei.netty.springmvc.NettyBootstrapV2;

public class TestNettyBootstrap2 {
	
	public static void main(String[] args){
		NettyBootstrapV2 nettyBootstrapV2 = new NettyBootstrapV2("classpath:conf.xml") ;
		nettyBootstrapV2.start(); 
	}
	
}
