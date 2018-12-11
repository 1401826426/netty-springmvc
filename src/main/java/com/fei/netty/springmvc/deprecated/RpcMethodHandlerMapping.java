package com.fei.netty.springmvc.deprecated;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;

import com.fei.netty.springmvc.rpc.RpcInterface;
import com.fei.netty.springmvc.rpc.generator.UrlGenerator;

public class RpcMethodHandlerMapping extends AbstractHandlerMethodMapping<RpcMethodMapping>{
	
	private UrlGenerator urlGenerator; 
	
	public UrlGenerator getUrlGenerator() {
		return urlGenerator;
	}

	public void setUrlGenerator(UrlGenerator urlGenerator) {
		this.urlGenerator = urlGenerator;
	}

	@Override
	protected boolean isHandler(Class<?> beanType) {
		boolean flag = AnnotatedElementUtils.hasAnnotation(beanType, RpcInterface.class) ; 
		return flag ; 
	}

	@Override
	protected RpcMethodMapping getMappingForMethod(Method method, Class<?> handlerType) {
		RpcMethodMapping rpcMethodMapping = new RpcMethodMapping() ; 
		rpcMethodMapping.setMethod(method);
		rpcMethodMapping.setHandlerType(handlerType);
		String url = urlGenerator.generate(method) ; 
		rpcMethodMapping.setUrl(url);
		return rpcMethodMapping;
	}

	@Override
	protected Set<String> getMappingPathPatterns(RpcMethodMapping mapping) {
		Set<String> set = new HashSet<String>() ; 
		set.add(mapping.getUrl()) ; 
		return set;
	}

	@Override
	protected RpcMethodMapping getMatchingMapping(RpcMethodMapping mapping, HttpServletRequest request) {
		String url = getUrlPathHelper().getLookupPathForRequest(request) ;
		if(mapping != null && mapping.getUrl().equals(url)){
			return mapping ; 
		}
		return null;
	}

	@Override
	protected Comparator<RpcMethodMapping> getMappingComparator(HttpServletRequest request) {
		return new Comparator<RpcMethodMapping>(){

			@Override
			public int compare(RpcMethodMapping o1, RpcMethodMapping o2) {
				return 0;
			}
			
		};
	}

	
}
