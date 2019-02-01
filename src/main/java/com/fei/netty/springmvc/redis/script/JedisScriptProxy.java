package com.fei.netty.springmvc.redis.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redis.clients.jedis.Jedis;
import util.str.StringUtils;

public class JedisScriptProxy {
	
	private static String path = "D:\\workspace\\eclipse_work_space\\test.lua\\src" ; 
	
	private static Pattern luaPattern = Pattern.compile("^[\\s\\S]*------------start-------------([\\s\\S]*)------------end----------[\\s\\S]*$"); 
	
	private  LuaSciptExecute execute = new LuaSciptExecute(); 
	
	private Map<Method,String> map = new HashMap<>() ; 
	
	private Jedis jedis = new Jedis("127.0.0.1",6379) ; 
	
	public String loadScript(Method method){
		if(map.containsKey(method)){
			return map.get(method)  ;
		}
		String name = StringUtils.javaToDb(method.getName()) ; 
		File file = new File(path + "\\"+name+".lua") ; 
		if(!file.exists()){
			throw new RuntimeException("no lua") ; 
		}
		try {
			Scanner in = new Scanner(new FileInputStream(file)) ;
			StringBuilder sb = new StringBuilder("") ; 
			while(in.hasNext()){
				sb.append(in.nextLine()+"\n")  ;
			}
			in.close();
			String script = sb.toString() ; 
			Matcher matcher = luaPattern.matcher(script) ; 
			if(matcher.find()){
				script = matcher.group(1) ; 
			}
			map.put(method, script) ; 
			System.out.println(script);
			return script ; 
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e) ;
		} 
	}
	
	private class LuaSciptExecute implements InvocationHandler{

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			String script = loadScript(method) ; 
			List<String> list = new ArrayList<>() ;
			for(Object arg:args){
				list.add(String.valueOf(arg)) ; 
			}
			return jedis.eval(script,list,new ArrayList<>()) ; 
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getInteface(Class<T> clazz){
		return (T)Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{clazz}, execute) ; 
	}
	
	public static void main(String[] args){
		JedisScriptProxy proxy = new JedisScriptProxy() ; 
		JedisScript jedisScript = proxy.getInteface(JedisScript.class) ; 
		for(int i = 0;i < 1000;i++){			
			jedisScript.createUser(i, "name:"+i, 0, 0);
		}
	}
	
	
}









