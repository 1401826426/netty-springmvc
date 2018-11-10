package com.fei.netty.springmvc.rpc.framework.deprecated.scanner;
//package com.fei.common.rpc.framework.deprecated.scanner;
//
//import java.util.Set;
//
//import org.springframework.beans.factory.config.BeanDefinitionHolder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
//
//import com.fei.common.rpc.framework.RpcInterfaceFactory;
//
//public class RpcInterfaceScanner extends ClassPathBeanDefinitionScanner{
//	
//	private RpcInterfaceFactory rpcInterfaceFactory ; 
//	
//	public RpcInterfaceScanner(BeanDefinitionRegistry registry) {
//		super(registry);
//	}
//	
//	public void setRpcInterfaceFactory(RpcInterfaceFactory rpcInterfaceFactory) {
//		this.rpcInterfaceFactory = rpcInterfaceFactory;
//	}
//
//
//
//	@Override
//	protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
//		Set<BeanDefinitionHolder> definitions = super.doScan(basePackages);
//		processDefinitions(definitions) ;
//		return definitions ; 
//	}
//
//	private void processDefinitions(Set<BeanDefinitionHolder> definitions) {
//		if(definitions == null){
//			return ; 
//		}
//		for(BeanDefinitionHolder holder:definitions){
//			GenericBeanDefinition beanDefinition = (GenericBeanDefinition)holder.getBeanDefinition() ;
//			beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
//			beanDefinition.setBeanClass(RpcInterfaceBeanFactory.class); 
//			beanDefinition.getPropertyValues().add("factory", rpcInterfaceFactory) ; 
//		}
//	}
//	
//}
