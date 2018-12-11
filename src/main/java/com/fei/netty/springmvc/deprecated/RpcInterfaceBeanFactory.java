package com.fei.netty.springmvc.deprecated;
//package com.fei.common.rpc.framework.deprecated.scanner;
//
//import org.springframework.beans.factory.FactoryBean;
//
//import com.fei.common.rpc.framework.RpcInterfaceFactory;
//
//public class RpcInterfaceBeanFactory<T> implements FactoryBean<T>{
//	
//	private Class<T> clazz ;
//	
//	private RpcInterfaceFactory factory ; 
//
//	public void setFactory(RpcInterfaceFactory factory) {
//		this.factory = factory;
//	}
//
//	public RpcInterfaceBeanFactory(Class<T> clazz) {
//		super();
//		this.clazz = clazz;
//	}
//
//	@Override
//	public T getObject() throws Exception {
//		return factory.getRpcInterface(clazz);
//	}
//
//	@Override
//	public Class<?> getObjectType() {
//		return clazz;
//	}
//
//	@Override
//	public boolean isSingleton() {
//		return true;
//	} 
//	
//	
//	
//
//}
