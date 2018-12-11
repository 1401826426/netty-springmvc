package com.fei.netty.springmvc.redis.spring;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import com.fei.netty.springmvc.redis.JedisInterface;

public class JedisInterfaceScanner extends ClassPathBeanDefinitionScanner{

	private String jedisClientManagerName ; 
	
	public JedisInterfaceScanner(BeanDefinitionRegistry registry) {
		super(registry);
		addIncludeFilter(new JedisInterfaceTypeFilter());
	}
	
	public String getJedisClientManagerName() {
		return jedisClientManagerName;
	}



	public void setJedisClientManagerName(String jedisClientManagerName) {
		this.jedisClientManagerName = jedisClientManagerName;
	}


	@Override
	protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> set = super.doScan(basePackages);
		for(BeanDefinitionHolder beanDefinitionHolder:set){
			GenericBeanDefinition beanDefinition = (GenericBeanDefinition)beanDefinitionHolder.getBeanDefinition() ;
			beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
			beanDefinition.setBeanClass(JedisInterfaceFactoryBean.class);
			beanDefinition.getPropertyValues().addPropertyValue("jedisClientManager", new RuntimeBeanReference(jedisClientManagerName));
		}
		return set ; 
	}

	 @Override
	  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
	    return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
	  }



	public class JedisInterfaceTypeFilter implements TypeFilter{

		@Override
		public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
				throws IOException {
			ClassMetadata classMetadata = metadataReader.getClassMetadata();
			AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata() ; 
			String className = JedisInterface.class.getName(); 
			if(classMetadata.isInterface() && annotationMetadata.hasAnnotation(className)){
				return true ;
			}
			return false;
		}
		
	}
	
}
