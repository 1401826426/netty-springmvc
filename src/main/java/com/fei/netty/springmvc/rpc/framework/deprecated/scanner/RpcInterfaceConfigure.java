package com.fei.netty.springmvc.rpc.framework.deprecated.scanner;
//package com.fei.common.rpc.framework.deprecated.scanner;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//
//import com.fei.common.rpc.framework.RpcInterfaceFactory;
//
//public class RpcInterfaceConfigure implements BeanDefinitionRegistryPostProcessor{
//	
//	private String basePackages ;
//	
//	private RpcInterfaceFactory rpcInterfaceFactory; 
//	
//	public String getBasePackages() {
//		return basePackages;
//	}
//
//	public void setBasePackages(String basePackages) {
//		this.basePackages = basePackages;
//	}
//
//	public RpcInterfaceFactory getRpcInterfaceFactory() {
//		return rpcInterfaceFactory;
//	}
//
//	public void setRpcInterfaceFactory(RpcInterfaceFactory rpcInterfaceFactory) {
//		this.rpcInterfaceFactory = rpcInterfaceFactory;
//	}
//
//	@Override
//	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException { 
//	}
//
//	@Override
//	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//		RpcInterfaceScanner scanner = new RpcInterfaceScanner(registry) ; 
//		scanner.setRpcInterfaceFactory(rpcInterfaceFactory);
//		scanner.scan(basePackages) ; 
//	}
//}
