package com.fei.netty.springmvc.conf;

import com.fei.netty.springmvc.converter.Converter;
import com.fei.netty.springmvc.converter.MethodParameterConverter;
import com.fei.netty.springmvc.converter.fackson.DataNodeMethodParameterConverter;
import com.fei.netty.springmvc.converter.fackson.FackJsonConverter;
import com.fei.netty.springmvc.rpc.common.DefaultRequestIdGenerator;
import com.fei.netty.springmvc.rpc.common.RequestIdGenerator;
import com.fei.netty.springmvc.rpc.generator.RpcMethodUrlGenerator;
import com.fei.netty.springmvc.rpc.generator.UrlGenerator;
import com.fei.netty.springmvc.rpc.sender.NettySenderFactory;

public class RpcConf implements Initializer{
	
	private int maxDataLen;
	
	private int responseTimeOut ;//单位,秒 
	
	private Converter converter ; 
	
	private UrlGenerator generator ;
	
	private MethodParameterConverter methodParameterConverter ; 
	
	private NettySenderFactory senderFactory ;
	
	private RequestIdGenerator idGenerator ;

	public int getMaxDataLen() {
		return maxDataLen;
	}

	public void setMaxDataLen(int maxDataLen) {
		this.maxDataLen = maxDataLen;
	}
	
	

	public void setMethodParameterConverter(MethodParameterConverter methodParameterConverter) {
		this.methodParameterConverter = methodParameterConverter;
	}

	public int getResponseTimeOut() {
		return responseTimeOut;
	}

	public void setResponseTimeOut(int responseTimeOut) {
		this.responseTimeOut = responseTimeOut;
	}

	public Converter getConverter() {
		return converter;
	}

	public UrlGenerator getGenerator() {
		return generator;
	}

	public NettySenderFactory getSenderFactory() {
		return senderFactory;
	}

	public RequestIdGenerator getIdGenerator() {
		return idGenerator;
	}

	
	public MethodParameterConverter getMethodParameterConverter() {
		return this.methodParameterConverter ; 
	}

	public void ini() {
		this.generator = new RpcMethodUrlGenerator() ; 
		this.converter = new FackJsonConverter() ; 
		this.senderFactory = new NettySenderFactory(this) ;
		this.idGenerator = new DefaultRequestIdGenerator() ;   
		this.methodParameterConverter = new DataNodeMethodParameterConverter() ; 
	}

	
	
	

}
