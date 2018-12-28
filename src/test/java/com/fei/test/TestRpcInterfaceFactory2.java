package com.fei.test;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.fei.netty.springmvc.conf.Configuration;
import com.fei.netty.springmvc.rpc.RpcInterfaceFactory2;
import com.fei.netty.springmvc.zookeeper.server.Server;

import util.data.DataParserBuilder;

public class TestRpcInterfaceFactory2 {
	
	public static void main(String[] args) throws IOException{
		ResourceLoader rl = new PathMatchingResourcePatternResolver() ; 
		Resource resource = rl.getResource("classpath:conf.xml") ;
		Configuration conf = null;
		conf = DataParserBuilder.getInstance().getXmlDataParser().parse(Configuration.class, resource.getInputStream());
		conf.ini();
		RpcInterfaceFactory2 factory = new RpcInterfaceFactory2(conf) ; 
		Server server = new Server() ;
		server.setHost("127.0.0.1");
		server.setPort(8080);
		RpcTest rpcTest = factory.getRpcInterface(RpcTest.class, server) ; 
		System.out.println(rpcTest.test()); 
	}
	
}





