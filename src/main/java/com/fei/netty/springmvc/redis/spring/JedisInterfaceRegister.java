package com.fei.netty.springmvc.redis.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import com.fei.netty.springmvc.redis.JedisClientManager;

public class JedisInterfaceRegister implements BeanDefinitionRegistryPostProcessor{

	
	private String[] basePackages ; 
	
	private String jedisName ; 
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		JedisInterfaceScanner scanner = new JedisInterfaceScanner(registry) ;
		String jedisClientManagerName = "jedisClientManager" ; 
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition() ;
		beanDefinition.setBeanClass(JedisClientManager.class);
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference(jedisName));
		registry.registerBeanDefinition(jedisClientManagerName, beanDefinition);
		scanner.setJedisClientManagerName(jedisClientManagerName);
		scanner.scan(basePackages) ; 
	}

	public String[] getBasePackages() {
		return basePackages;
	}

	public void setBasePackages(String[] basePackages) {
		this.basePackages = basePackages;
	}
	
	public void setBasePackage(String basePackage){
		this.basePackages = new String[]{basePackage} ; 
	}

	public String getJedisName() {
		return jedisName;
	}

	public void setJedisName(String jedisName) {
		this.jedisName = jedisName;
	}
	
	
}
