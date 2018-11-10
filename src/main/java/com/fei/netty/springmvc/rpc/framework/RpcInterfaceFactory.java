package com.fei.netty.springmvc.rpc.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.fei.netty.springmvc.converter.Converter;
import com.fei.netty.springmvc.converter.fackson.FackJsonConverter;
import com.fei.netty.springmvc.rpc.framework.generator.RpcMethodUrlGenerator;
import com.fei.netty.springmvc.rpc.framework.generator.UrlGenerator;
import com.fei.netty.springmvc.rpc.framework.proxy.RpcInterfaceProxyHandler;
import com.fei.netty.springmvc.rpc.framework.sender.Sender;
import com.fei.netty.springmvc.rpc.framework.sender.netty.NettySender;
import com.fei.netty.springmvc.zookeeper.server.Server;

public class RpcInterfaceFactory {
	
	private Sender sender  ; 
	
	private UrlGenerator urlGenerator ; 
	
	private Converter converter ;
	
	private InvocationHandler handler ; 

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public UrlGenerator getUrlGenerator() {
		return urlGenerator;
	}

	public void setUrlGenerator(UrlGenerator urlGenerator) {
		this.urlGenerator = urlGenerator;
	}

	public Converter getConverter() {
		return converter;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	} 
	
	@SuppressWarnings("unchecked")
	public <T> T getRpcInterface(Class<T> clazz,Server server){
		if(clazz == null || !(clazz.isInterface())){
			throw new RuntimeException("clazz为空或者不是接口") ; 
		}
		iniHandler(server) ; 
		return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this.handler) ;  
	}

	private void iniHandler(Server server) {
		if(this.handler == null){
			checkAllIni();  
			this.handler = new RpcInterfaceProxyHandler(sender,urlGenerator,converter,server) ;
		}
	}

	private void checkAllIni() {
		if(sender == null){
			NettySender nettySender = new NettySender() ; 
			nettySender.ini();  
			this.sender = nettySender; 
		}
		if(converter == null){
			this.converter = new FackJsonConverter() ;  
		}
		if(this.urlGenerator == null){
			this.urlGenerator = new RpcMethodUrlGenerator() ; 
		}
	}
	
	public static void main(String[] args) {
//		RpcInterfaceFactory factory = new RpcInterfaceFactory();
//		Server server = new Server(ServerGroupEnum.ADMIN,"localhost",8080,1);
//		TestRpc testRpc = factory.getRpcInterface(TestRpc.class,server) ;
//		TestDto testDto = new TestDto() ; 
//		testDto.setName("name");
//		testDto.setPassword("password");
//		List<TestDto> list = new ArrayList<TestDto>() ;
//		list.add(testDto) ; 
//		System.err.print(testRpc.test(list)) ;
//		System.err.println(testRpc.testOne(testDto));
//		
//		TestRpcAync testRpcAync = factory.getRpcInterface(TestRpcAync.class,server) ; 
//		testRpcAync.test(list, new RpcCallBack<List<TestDto>>() {
//			
//			@Override
//			public void success(List<TestDto> obj) {
//				System.err.println(obj);
//			}
//			
//			@Override
//			public void error(Exception e) {
//				e.printStackTrace();
//			}
//		});
	}
}
