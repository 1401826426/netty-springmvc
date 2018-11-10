package com.fei.netty.springmvc.zookeeper.test;

import org.apache.zookeeper.server.ZooKeeperServerMain;

public class JustTest {
	
	public static void main(String[] args) {
		if(args.length == 0){
			args = new String[]{
					"F:\\netty-spring\\DataAy\\DataAyCommon\\src\\main\\resources\\zoo.cfg"
			}; 
		}
		ZooKeeperServerMain.main(args);
	}

}
