package com.fei.netty.springmvc.session;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import com.fei.netty.springmvc.conf.NettyConf;

import util.collection.CollectionUtils;

public class SessionManager {
	
	private static SessionManager instance = new SessionManager(); 
	
	private Map<String,HttpSession> sessions = null;  
	
	private ScheduledExecutorService executorService = null ;
	
	private NettyConf nettyConf ; 
	
	public static SessionManager getInstance(){
		return instance ; 
	}
	
	public void sessionStart(NettyConf nettyConf){
		synchronized (instance) {
			sessions = new ConcurrentHashMap<>() ;
			executorService = Executors.newScheduledThreadPool(1) ;
			instance.nettyConf = nettyConf ; 
			instance.ini(); 
		}
	}
	
	public void ini(){
		 
		executorService.schedule(new Runnable() {
			
			@Override
			public void run() {
				try{
					for(Map.Entry<String,HttpSession> entry:sessions.entrySet()){
						try{
							HttpSession session = entry.getValue() ; 
							if(System.currentTimeMillis() - session.getLastAccessedTime() > session.getMaxInactiveInterval()){
								session.invalidate();
								sessions.remove(entry.getKey()) ;
							}
						}catch(Exception e){
							e.printStackTrace();  
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					executorService.schedule(this, nettyConf.getSessionCheckInterval(), TimeUnit.MILLISECONDS) ;
				}
			}
		},0,TimeUnit.SECONDS);
	}
	
	public HttpSession get(String sessionId,boolean create){
		HttpSession session = sessions.get(sessionId) ; 
		if(session == null){
			if(create){
				session = create() ; 
			}
		}
		return session ; 
	}
	
	public synchronized HttpSession create(){
		String sessionId = generateSessionId(); 
		return create(sessionId) ;
	}
	
	public void access(String sessionId){
		DefaultSession session = (DefaultSession)sessions.get(sessionId) ; 
		session.access() ; 
	}
	
	private synchronized HttpSession create(String sessionId){
		DefaultSession session = new DefaultSession(sessionId) ; 
		sessions.put(sessionId, session) ;
		return session ; 
	}
	
	private  String generateSessionId(){
		String sessionId = null ; 
		do{
			SecureRandom random = new SecureRandom() ;
			byte[] bytes = new byte[16] ; 
			random.nextBytes(bytes);
			sessionId = CollectionUtils.bytesToHexStr(bytes) ;  
		
		}while(sessions.containsKey(sessionId)) ; 
		return sessionId ;   
	}
	
	public HttpSession removeSession(String sessionId){
		return this.sessions.remove(sessionId); 
	}
	
	public static void main(String[] args){
		Map<Integer,Integer> map = new ConcurrentHashMap<Integer,Integer>() ; 
		map.put(1, 1) ; 
		map.put(2, 2) ; 
		for(Map.Entry<Integer, Integer> entry:map.entrySet()){
			map.remove(entry.getKey()) ; 
		}
		byte a = -1; 
		System.out.printf(String.format("%x", a)) ;
	}
	
}
