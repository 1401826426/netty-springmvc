package com.fei.netty.springmvc.rpc.framework.spring.mvc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class MyRRBProcessorFactory implements FactoryBean<MyRequestResponseBodyProcessor>{
	
	@Override
	public MyRequestResponseBodyProcessor getObject() throws Exception {
		List<HttpMessageConverter<?>> converters = new ArrayList<>() ;
		Jackson2ObjectMapperFactoryBean factory = new Jackson2ObjectMapperFactoryBean() ;
		factory.afterPropertiesSet(); 
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(factory.getObject()) ;
		converters.add(converter) ; 
		MyRequestResponseBodyProcessor processor = new MyRequestResponseBodyProcessor(converters) ; 
		return processor;
	}

	@Override
	public Class<?> getObjectType() {
		return MyRequestResponseBodyProcessor.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}	

}
