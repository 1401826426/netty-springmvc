package com.fei.test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.fei.netty.springmvc.conf.Configuration;
import com.fei.netty.springmvc.rpc.RpcCallBack;
import com.fei.netty.springmvc.rpc.RpcInterfaceFactory2;
import com.fei.netty.springmvc.zookeeper.server.Server;
import com.fei.test.RpcTest.RpcTestAync;

import util.data.DataParserBuilder;

public class TestRpcInterfaceFactory2 {
	
	private static Logger logger = LoggerFactory.getLogger(TestRpcInterfaceFactory2.class) ;
	
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
		ExecutorService es = Executors.newCachedThreadPool() ; 
		for(int i = 0;i < 1;i++){	
			
//			System.out.println(rpcTest.test()); 
			es.execute(new Runnable() {
				
				@Override
				public void run() {
					RpcTestAync rpcTest = factory.getRpcInterface(RpcTestAync.class, server) ; 
					while(true){
//						for(int i = 0;i < 100;i++){
//						}
//						System.out.println(rpcTest);
						logger.info(rpcTest.toString());
						rpcTest.test(new RpcCallBack<Boolean>() {

							@Override
							public void success(Boolean obj) {
								logger.info("result = " + obj);
								
							}

							@Override
							public void error(Throwable throwable) {
								throwable.printStackTrace();
							}
						});
//						System.out.println(rpcTest.test()); 
						try{
							Thread.sleep(1000);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			});
//			
		}
	}
	
}





