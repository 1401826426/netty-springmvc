package com.fei.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.fei.netty.springmvc.conf.Configuration;
import com.fei.netty.springmvc.rpc.RpcInterfaceFactory2;
import com.fei.netty.springmvc.zookeeper.server.Server;

import util.data.DataParserBuilder;

public class TestRpcInterfaceFactory2 {
	
	private static Logger logger = LoggerFactory.getLogger(TestRpcInterfaceFactory2.class) ;
	
	
	private Server server ; 
	
	private RpcInterfaceFactory2 factory ; 
	
	@Before
	public void before() throws IOException{
		ResourceLoader rl = new PathMatchingResourcePatternResolver() ; 
		Resource resource = rl.getResource("classpath:conf.xml") ;
		Configuration conf = null;
		conf = DataParserBuilder.getInstance().getXmlDataParser().parse(Configuration.class, resource.getInputStream());
		conf.ini();
		factory = new RpcInterfaceFactory2(conf) ; 
		server = new Server() ;
		server.setHost("127.0.0.1");
		server.setPort(8080);
	}
	
	@Test
	public void testArgs(){
		RpcTest rpcTest = factory.getRpcInterface(RpcTest.class, server) ; 
		TestDto1 dto1 = new TestDto1() ; 
		dto1.setA(1);
		dto1.setB("b");
		TestDto2 dto2 = new TestDto2() ; 
		dto2.setC(2);
		dto2.setD("d");
		System.out.println(rpcTest.testA(dto1, dto2, "qwertygfdsa").getValue());
	}
	
	
}





