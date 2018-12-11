package com.fei.netty.springmvc.rpc.spring.mvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import com.fei.netty.springmvc.rpc.RpcInterface;
import com.fei.netty.springmvc.rpc.common.RpcRequest;
import com.fei.netty.springmvc.rpc.generator.RpcMethodUrlGenerator;
import com.fei.netty.springmvc.rpc.generator.UrlGenerator;
import com.fei.netty.springmvc.rpc.socketrpc.RpcHttpServletRequest;

import util.clazz.ClazzUtil;

public class RpcRequestHandlerMapping extends AbstractHandlerMapping implements InitializingBean{
	
	private Logger logger = LoggerFactory.getLogger(RpcRequestHandlerMapping.class) ; 
	
	private Map<String,Object> map = new HashMap<>() ; 
	
	private UrlGenerator urlGenerator; 

	public RpcRequestHandlerMapping() {
		this(new RpcMethodUrlGenerator()) ;
	}

	public RpcRequestHandlerMapping(UrlGenerator urlGenerator){
		this.urlGenerator = urlGenerator ;
		setOrder(Integer.MIN_VALUE);
	}
	
	private static HandlerMethod DEFAULT_HANDLER ; 
	
	
	static{
		DefaultRpcHandler bean = new DefaultRpcHandler() ; 
		try {
			Method method = DefaultRpcHandler.class.getMethod("doNoRpcHandler") ;
			DEFAULT_HANDLER = new HandlerMethod(bean, method) ; 
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("===========================start parse rpc cmd================================");
		String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(getApplicationContext(),Object.class) ;
		ApplicationContext applicationContext = getApplicationContext() ; 
		for(String name:beanNames){
			Object bean = applicationContext.getBean(name) ; 
			Class<?> clazz = ClassUtils.getUserClass(bean) ;
			Class<?> interClass = ClazzUtil.getAnnotationClass(clazz, RpcInterface.class) ;
			if(interClass != null){
				Method[] methods = interClass.getMethods() ; 
				for(Method method:methods){
					String cmd = urlGenerator.generate(method) ;
					HandlerMethod handlerMethod = new HandlerMethod(bean, method) ;
					logger.info(String.format("mapping {cmd=%s,bean=%s,method=%s}",cmd,clazz.getName(),method.getName()));
					map.put(cmd, handlerMethod) ; 
				}
			}
		}
		logger.info("===========================end parse rpc cmd================================");
	}
	
	@Override
	protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
		if(request instanceof RpcHttpServletRequest){
			RpcHttpServletRequest rpcHttpServletRequest = (RpcHttpServletRequest)request ; 
			RpcRequest rpcRequest = rpcHttpServletRequest.getRpcRequest() ;
			String cmd = rpcRequest.getCommand() ; 
			Object handler = map.get(cmd) ;
			if(handler != null){
				return handler ;  
			}
	        return DEFAULT_HANDLER ; 
		}
		return null;
	}

	

}








