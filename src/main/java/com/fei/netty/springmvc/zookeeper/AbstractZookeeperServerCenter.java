package com.fei.netty.springmvc.zookeeper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;

import com.fei.netty.springmvc.converter.Converter;
import com.fei.netty.springmvc.converter.ConverterException;
import com.fei.netty.springmvc.converter.fackson.FackJsonConverter;
import com.fei.netty.springmvc.zookeeper.server.Server;
import com.fei.netty.springmvc.zookeeper.server.ServerGroupEnum;

import util.collection.HashMapArrayListMultiMap;

public abstract class AbstractZookeeperServerCenter extends AbstractZookeeperCenter {

	private HashMapArrayListMultiMap<ServerGroupEnum, Server> servers;

	private Map<String, Server> serverMap;

	private String parentPath = "/fei/dataAy";

	private Converter converter ; 
	
	private ReadWriteLock lock = new ReentrantReadWriteLock() ;
	
	public void ini(){
		if(this.converter == null){
			this.converter = new FackJsonConverter() ; 
		}
		super.ini(); 
		Server server = loadServer() ; 
		registerSelf(server);
		refresh();
	}
	
	protected abstract  Server loadServer()  ; 

	public void registerSelf(Server server) {
		try{			
			byte[] bytes = converter.writeValue(server) ; 
			createDataRecursive(parentPath+"/"+server.getGroup().str()+"/"+server.getServerKey(),bytes, Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	protected void refresh() {
		logger.info("==============zookeeper refresh start=============") ; 
		try{
			lock.writeLock().lock();  
			servers = new HashMapArrayListMultiMap<>();
			serverMap = new HashMap<>();
			loadServers();			
		}finally {
			lock.writeLock().unlock();
		}
		logger.info("==============zookeeper refresh end=============") ; 
	}

	private void loadServers() {
		List<String> childs = getChild(parentPath);
		if(childs != null){
			for (String child : childs) {
				loadGroupServers(parentPath+"/"+child);
			}
		}
	}

	private void loadGroupServers(String path) {
		List<String> serverPaths = getChild(path);
		for(String serverPath:serverPaths){
			byte[] bytes = getData(path+"/"+serverPath) ; 
			try {
				Server server = (Server) converter.readValue(bytes, Server.class) ;
				serverMap.put(server.getServerKey(),server) ; 
				servers.putOne(server.getGroup(),server) ; 
			} catch (ConverterException e) {
				e.printStackTrace();
			} 
		}
	}

	public Converter getConverter() {
		return converter;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	public Collection<Server> getServerByGroupType(ServerGroupEnum serverGroup) {
		try{
			lock.readLock().lock();  
			return servers.get(serverGroup);
		}finally{
			lock.readLock().lock();  
		}
	}

	public Server getServerByKey(String serverKey) {
		try{
			lock.readLock().lock();  
			return serverMap.get(serverKey);
		}finally{
			lock.readLock().unlock();  
		}
	}
	
	public abstract Server getSelfServer();

}
