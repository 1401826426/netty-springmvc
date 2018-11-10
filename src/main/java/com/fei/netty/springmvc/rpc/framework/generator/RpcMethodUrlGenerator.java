package com.fei.netty.springmvc.rpc.framework.generator;

import java.lang.reflect.Method;

import util.clazz.ClazzUtil;
import util.str.StringUtils;


//对于rpc method的产生
//用其对应接口加对应method的方式
//然后用大写字母隔开
public class RpcMethodUrlGenerator implements UrlGenerator{
	
	@Override
	public String generate(Method method){
		if(method == null){
			return "" ; 
		}
		String name = method.getName() ;
		String className = ClazzUtil.getDecalareInterface(method).getSimpleName() ; 
		String[] classNames = StringUtils.splitByUpper(className) ; 
		String[] methodNames = StringUtils.splitByUpper(name) ; 
		StringBuilder sb = new StringBuilder("") ;
		for(String na:classNames){
			sb.append("/"+na.toLowerCase()) ;
		}
		for(String na:methodNames){
			sb.append("/"+na.toLowerCase())  ; 
		}
		return sb.toString(); 
	}
	
}
