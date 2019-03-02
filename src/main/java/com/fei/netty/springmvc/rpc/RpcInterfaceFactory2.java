package com.fei.netty.springmvc.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fei.netty.springmvc.conf.Configuration;
import com.fei.netty.springmvc.converter.Converter;
import com.fei.netty.springmvc.converter.ConverterException;
import com.fei.netty.springmvc.converter.MethodParameterConverter;
import com.fei.netty.springmvc.future.RFuture;
import com.fei.netty.springmvc.future.RFutureListener;
import com.fei.netty.springmvc.rpc.common.RequestIdGenerator;
import com.fei.netty.springmvc.rpc.common.RpcRequest;
import com.fei.netty.springmvc.rpc.common.RpcResponse;
import com.fei.netty.springmvc.rpc.generator.UrlGenerator;
import com.fei.netty.springmvc.rpc.sender.NettySenderFactory;
import com.fei.netty.springmvc.rpc.sender.Sender2;
import com.fei.netty.springmvc.zookeeper.server.Server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class RpcInterfaceFactory2 {
	
	private UrlGenerator generator ; 
	
	private Converter converter ; 
	
	private MethodParameterConverter methodParameterConverter ; 
	
	private RequestIdGenerator idGenerator ; 
	
	private NettySenderFactory senderFactory ; 
	
	private ByteBufAllocator allocator ; 
	
	private static Set<String> commonMethod = new HashSet<>() ; 
	
	static{
		commonMethod.add("hashcode") ; 
		commonMethod.add("toString") ; 
		commonMethod.add("wait") ; 
		commonMethod.add("notify") ; 
		commonMethod.add("notifyAll") ;
	}
	
	public RpcInterfaceFactory2(Configuration conf){
		super();
		this.generator = conf.getRpcConf().getGenerator() ; 
		this.converter = conf.getRpcConf().getConverter();
		this.senderFactory = conf.getRpcConf().getSenderFactory() ;
		this.idGenerator = conf.getRpcConf().getIdGenerator() ;
		this.allocator = conf.getAllocator() ; 
		this.methodParameterConverter = conf.getRpcConf().getMethodParameterConverter() ;  
	}

	
	private Sender2 getSender(Server server){
		return senderFactory.getSender(server) ; 
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getRpcInterface(Class<?> rpcInterface,Server server){
		Sender2 sender = getSender(server) ; 
		return (T)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[]{rpcInterface}, new RpcInterfaceProxyHandler(sender)) ; 
	}
	
	private class RpcInterfaceProxyHandler implements InvocationHandler{	
		
		private Sender2 sender ; 

		private Object obj = new Object() ; 
		
		public RpcInterfaceProxyHandler(Sender2 sender){
			this.sender = sender ; 
		}

		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			try{
				if(commonMethod.contains(method.getName())){
					return method.invoke(obj, args) ; 
				}
				boolean aync = asynMethod(method) ; 
				if(aync){			
					method = getRealMethod(method) ; 		
				}
				String url = generator.generate(method) ;
				Type type = method.getGenericReturnType()  ;
				byte[] bytes = generateArgBytes(args,method) ;
				ByteBuf data = allocator.buffer(bytes.length) ;
				data.writeBytes(bytes) ; 
				RpcRequest request = new RpcRequest(idGenerator.getRequestId(), url, data) ;
				RFuture<RpcResponse> future = sender.sendRequest(request) ; 
				if(aync){
					@SuppressWarnings("rawtypes")
					RpcCallBack callBack = detectCallBack(type,args) ;
					if(callBack != null){
						future.addListener(new RFutureListener<RFuture<RpcResponse>>() {
							
							@SuppressWarnings("unchecked")
							@Override
							public void onComplete(RFuture<RpcResponse> t) {
								if(t.isSuccess()){
									RpcResponse response = t.getNow() ;
									if(response == null){
										callBack.error(new RuntimeException("illeagal timeout"));
									}
									Object obj;
									try {
										obj = converter.readValue(response.getData().array(), type);
										callBack.success(obj);
									} catch (ConverterException e) {
										callBack.error(e);
									} 
								}else{
									callBack.error(t.cause());
								}
								
							}
						});
					}
				}else{
					RpcResponse response = future.get() ;
					Object obj = converter.readValue(response.getData().array(), type) ;
					return obj ; 
				}
				
			}catch(Throwable t){
				t.printStackTrace();
			}
			return null ; 
		}

		private byte[] generateArgBytes(Object[] args, Method method) throws ConverterException {
			return methodParameterConverter.serialize(method, args) ; 
		}

		//检测最后的一个参数是否有callBack
		@SuppressWarnings("rawtypes")
		private RpcCallBack detectCallBack(Type type, Object[] args) {
			if(args.length > 0){
				Object call = args[args.length-1] ; 
				if(call != null && call instanceof RpcCallBack){
					RpcCallBack rpcCallBack = (RpcCallBack)call ; 
					return rpcCallBack ;
				}
			}
			return null ; 
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

	
}
