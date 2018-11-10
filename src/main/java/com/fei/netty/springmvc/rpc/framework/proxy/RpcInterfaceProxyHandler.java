package com.fei.netty.springmvc.rpc.framework.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

import com.fei.netty.springmvc.converter.Converter;
import com.fei.netty.springmvc.converter.ConverterException;
import com.fei.netty.springmvc.rpc.framework.RpcCallBack;
import com.fei.netty.springmvc.rpc.framework.common.RpcByteRequest;
import com.fei.netty.springmvc.rpc.framework.common.RpcByteResponse;
import com.fei.netty.springmvc.rpc.framework.generator.UrlGenerator;
import com.fei.netty.springmvc.rpc.framework.sender.RpcSenderCallBack;
import com.fei.netty.springmvc.rpc.framework.sender.Sender;
import com.fei.netty.springmvc.zookeeper.server.Server;

public class RpcInterfaceProxyHandler implements InvocationHandler{	
    
	private Sender sender; 
	
	private UrlGenerator generator ;
	
	private Converter converter ; 
	
	private Server server ; 
	
	public RpcInterfaceProxyHandler(Sender sender, UrlGenerator generator, Converter converter,Server server) {
		super();
		this.sender = sender;
		this.generator = generator;
		this.converter = converter;
		this.server = server ; 
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		boolean aync = asynMethod(method) ; 
		if(aync){			
			method = getRealMethod(method) ; 		
		}
		String url = generator.generate(method) ;
		Type type = method.getGenericReturnType()  ;
		byte[] bytes = generateArgBytes(args,method) ; 
		RpcByteRequest request = new RpcByteRequest(server.getHost(),server.getPort(),url, bytes) ;
		if(aync){
			RpcSenderCallBack callBack = detectCallBack(type,args) ;
			sender.sendAync(request, callBack);
			return null ; 
		}else{
			RpcByteResponse response = sender.send(request) ;
			if(response.getStatus() != 200){
				throw new RuntimeException("没有正确返回"+"返回值为"+response.getStatus()) ; 
			}
			Object obj = converter.readValue(response.getData(), type) ;
			return obj;
		}
	}

	private byte[] generateArgBytes(Object[] args, Method method) throws ConverterException {
		byte[] bytes = null ; 
		int len = method.getParameterCount() ;  
		if(len ==0){
			bytes = new byte[0] ; 
		}else if(len == 1){
			bytes = converter.writeValue(args[0]) ; 
		}else{
			bytes = converter.writeValue(Arrays.copyOf(args,len)) ; 
		}
		return bytes ; 
	}

	//检测最后的一个参数是否有callBack
	private RpcSenderCallBack detectCallBack(Type type, Object[] args) {
		RpcSenderCallBack callBack = null ;
		if(args.length > 0){
			Object call = args[args.length-1] ; 
			if(call != null && call instanceof RpcCallBack){
				@SuppressWarnings("rawtypes")
				RpcCallBack rpcCallBack = (RpcCallBack)call ; 
				callBack = new RpcSenderCallBack() {
					@SuppressWarnings("unchecked")
					@Override
					public void success(RpcByteResponse response) {
						try {
							if(response.getStatus() != 200){
								rpcCallBack.error(new RuntimeException("没有正确返回,返回值为"+response.getStatus()));
								return ; 
							}
							Object obj = converter.readValue(response.getData(), type) ;
							rpcCallBack.success(obj);
						} catch (ConverterException e) {
							rpcCallBack.error(e);
						}	
					}
					@Override
					public void error(Exception e) {
						rpcCallBack.error(e);
					}
				};
			}
		}
		return callBack ; 
	}
     
	//对于异步的方法调用,将其method转换为真正调用的method
	private Method getRealMethod(Method method) throws NoSuchMethodException, SecurityException { 
		Class<?>[] params = method.getParameterTypes() ; 
		if(params.length > 0){
			Class<?> clazz = params[params.length-1] ; 
			if(clazz.isAssignableFrom(RpcCallBack.class)){
				Class<?>[] paramsWithouLast = Arrays.copyOf(params,params.length-1) ; 
				Class<?>[] interfaces = method.getDeclaringClass().getInterfaces() ; 
				for(Class<?> intf:interfaces){
					Method detectMethod = intf.getMethod(method.getName(),paramsWithouLast) ; 
					if(detectMethod != null){
						return detectMethod ; 
					}
				}
			}
		}
		return method;
	}
	
	private boolean asynMethod(Method method){
		Class<?>[] params = method.getParameterTypes() ; 
		if(params.length > 0){
			Class<?> clazz = params[params.length-1] ; 
			if(clazz.isAssignableFrom(RpcCallBack.class)){
				return true ; 
			}
		}
		return false ; 
	}

}















