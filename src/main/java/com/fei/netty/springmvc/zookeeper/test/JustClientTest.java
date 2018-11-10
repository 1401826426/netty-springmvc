package com.fei.netty.springmvc.zookeeper.test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import com.fei.netty.springmvc.converter.ConverterException;

public class JustClientTest {
	
	public static void main(String[] args) throws KeeperException, InterruptedException, IOException, ConverterException, NoSuchAlgorithmException {
		System.err.println(DigestAuthenticationProvider.generateDigest("user:pass"));
		ZooKeeper zooKeeper = new ZooKeeper("localhost:2181",200, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				EventType type = event.getType() ;  
				KeeperState state = event.getState() ; 
				System.err.println(type);
				System.err.println(state) ; 
			}
			
		}) ; 
		List<String> list = zooKeeper.getChildren("/", null) ;
		System.err.println(list);
		List<ACL> acls = zooKeeper.getACL("/",new Stat()) ;
		System.err.println(acls);
		TestBean test = new TestBean() ; 
		test.setName("name");
		test.setPassword("password");
//		Converter converter = new FackJsonConverter() ; 
//		byte[] bytes = converter.writeValue(test); 
//		zooKeeper.create("/test/test",bytes, Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT) ; 
//		zooKeeper.getData("/test/test/test", false, new Stat()) ;
		
//		zooKeeper.getChildren("/test/test",, cb, ctx);
	}
}
