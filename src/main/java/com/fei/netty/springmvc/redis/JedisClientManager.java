package com.fei.netty.springmvc.redis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.BasicCommands;
import redis.clients.jedis.BinaryJedisClusterCommands;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.MultiKeyBinaryJedisClusterCommands;
import redis.clients.jedis.MultiKeyJedisClusterCommands;

public class JedisClientManager implements InvocationHandler{
	
	private JedisPool jedisPool ; 
	
	private JedisCluster jedisCluster ;
	
	private Map<Method,Method> toJedisInterface = new HashMap<>() ; 
	
	private Map<Method,Method> toJedisClusterInterface = new HashMap<>() ; 
	
	private Map<Class<?>,Object> interfaceObject = new HashMap<>(); 
	
	public JedisClientManager(JedisCluster jedisCluster){
		this.jedisCluster = jedisCluster ;  
	}
	
	public JedisClientManager(JedisPool jedisPool){
		this.jedisPool = jedisPool ;   
	}
    
	private void addInterface(Class<?> clazz) {
		if(clazz == null){
			return ; 
		}
		if(!clazz.isInterface()){
			throw new RuntimeException("not interface") ;
		}
		Map<Method,Method> jedisMap = new HashMap<>() ;
		Map<Method,Method> jedisClusterMap = new HashMap<>() ;
		Class<?> jedisClazz = Jedis.class ; 
		Class<?> jedisCluster = JedisCluster.class ; 
		for(Method method:clazz.getMethods()){
			try{
				Method jedisMethod = jedisClazz.getMethod(method.getName(),method.getParameterTypes()) ;
			    jedisMap.put(method, jedisMethod) ;
			    Method jedisClusterMethod = jedisCluster.getMethod(method.getName(), method.getParameterTypes()) ; 
			    jedisClusterMap.put(method, jedisClusterMethod) ; 
			}catch(NoSuchMethodException e){
				throw new RuntimeException("no method name=" + method.getName() + ",pts="+method.getParameterTypes()) ; 
			}
		}
		this.toJedisInterface.putAll(jedisMap);
		this.toJedisClusterInterface.putAll(jedisClusterMap);
	}

	
	@SuppressWarnings("unchecked")
	public <T> T getInterface(Class<T> clazz){
		if(clazz == null){
			throw new RuntimeException("clazz is null") ; 
		}
		if(!clazz.isInterface()){
			throw new RuntimeException("not interface") ; 
		}
		Object result = interfaceObject.get(clazz) ; 
    	if(result == null){
    		synchronized (this) {
    			result = interfaceObject.get(clazz) ;
				if(result == null){
					result = createProxy(clazz) ;
					if(result != null){						
						interfaceObject.put(clazz,result) ; 
					}
				}
			}
    	}
    	return (T)result;  
    }
    
	private <T> Object createProxy(Class<T> clazz) {
		addInterface(clazz);
		if(jedisCluster == null && jedisPool == null){
			throw new RuntimeException("no jedis") ; 
		}
		Class<?>[] interfaces = new Class<?>[]{clazz} ; 
		return Proxy.newProxyInstance(this.getClass().getClassLoader(), interfaces, this);
	}



	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(jedisPool != null){
			Jedis jedis = null ; 
			try{
				jedis = jedisPool.getResource() ; 
				Method jedisMethod = toJedisInterface.get(method) ;
				return jedisMethod.invoke(jedis, args);
			}catch(Exception e){
				throw e ; 
			}finally{
				if(jedis != null){
					jedis.close();  
				}
			}
		}else if(jedisCluster != null){
			Method jedisClusterMethod = toJedisClusterInterface.get(method) ;
			return jedisClusterMethod.invoke(jedisCluster, args) ; 
		}else{
			throw new RuntimeException("no jedis") ;
		}
		
	}
	
	public static void main(String[] args) {
		JedisClientManager jedisClientManager = new JedisClientManager(new JedisPool("127.0.0.1",6379)) ;
		jedisClientManager.getInterface(JedisCommands.class);
		jedisClientManager.getInterface(JedisCommands.class);
//		jedisClientManager.getInterface(MultiKeyCommands.class);
//		jedisClientManager.getInterface(AdvancedJedisCommands.class);
//		jedisClientManager.getInterface(ScriptingCommands.class);
		jedisClientManager.getInterface(BasicCommands.class);
//		jedisClientManager.getInterface(BinaryJedisCommands.class);
//		jedisClientManager.getInterface(MultiKeyBinaryCommands.class);
//		jedisClientManager.getInterface(AdvancedBinaryJedisCommands.class);
//		jedisClientManager.getInterface(BinaryScriptingCommands.class);
//		jedisClientManager.getInterface(ClusterCommands.class);
		jedisClientManager.getInterface(MultiKeyJedisClusterCommands.class);
//		jedisClientManager.getInterface(JedisClusterScriptingCommands.class);
		jedisClientManager.getInterface(BinaryJedisClusterCommands.class);
		jedisClientManager.getInterface(MultiKeyBinaryJedisClusterCommands.class);
//		jedisClientManager.getInterface(JedisClusterBinaryScriptingCommands.class);
	}
	
	
	
}














