package com.fei.test;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.fei.netty.springmvc.conf.Configuration;
import com.fei.netty.springmvc.future.RFuture;
import com.fei.netty.springmvc.rpc.common.RpcRequest;
import com.fei.netty.springmvc.rpc.common.RpcResponse;
import com.fei.netty.springmvc.rpc.sender.NettySenderFactory;
import com.fei.netty.springmvc.rpc.socketrpc.sender.NettySenderV2;
import com.fei.netty.springmvc.zookeeper.server.Server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import util.data.DataParserBuilder;

public class TestNettySenderV2 {
	
	private static int requestId = 1 ; 
	
	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		ResourceLoader rl = new PathMatchingResourcePatternResolver() ; 
		Resource resource = rl.getResource("classpath:conf.xml") ;
		Configuration conf = null;
		try {
			conf = DataParserBuilder.getInstance().getXmlDataParser().parse(Configuration.class, resource.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		NettySenderFactory factory = new NettySenderFactory(conf.getRpcConf()) ;
		for(int i = 0;i < 100;i++){
			System.out.println(i);
			final String strData = String.valueOf(i) ; 
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Server server = new Server() ; 
					server.setHost("localhost");
					server.setPort(8080);
					NettySenderV2 nettySender = factory.getSender(server) ;
					byte[] bytes = strData.getBytes() ; 
					ByteBuf data = new UnpooledByteBufAllocator(false).buffer(bytes.length) ;
					data.writeBytes(bytes) ; 
					RpcRequest rpcRequest = new RpcRequest(requestId++, "test@test", data) ;  
					RFuture<RpcResponse> rFuture = nettySender.sendRequest(rpcRequest) ;
					try {
						RpcResponse response = rFuture.get() ;
						System.out.println(response);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					} 
				}
			}).start();  
		}
		in.close(); 
		 
	}
	
}