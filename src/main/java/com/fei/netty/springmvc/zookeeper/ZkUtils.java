package com.fei.netty.springmvc.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.ACL;

import util.str.StringUtils;

public abstract class ZkUtils {
	
	public static void recursiveSafeCreate(ZooKeeper zk ,String path,byte[] data,List<ACL> acls,CreateMode mode) throws InterruptedException, KeeperException{
		for(int index = path.indexOf("/",0);;index=path.indexOf("/", index)){
			String subPath = "" ; 
			if(index == -1){
				subPath = path ; 
			}else{				
				subPath = path.substring(0,index) ;
			}
			if(StringUtils.isBlank(subPath)){
				if(index == -1){
					break; 
				}else{
					continue ; 					
				}
			}
			try{
				zk.create(subPath, data,acls,mode) ; 
			}catch(KeeperException e){
				if(e.code() != Code.NODEEXISTS){
				    throw e ; 
				}
			}
			if(index == -1){
				break ; 
			}
		}
	}
	
}










