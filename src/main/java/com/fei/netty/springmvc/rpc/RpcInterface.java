package com.fei.netty.springmvc.rpc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fei.netty.springmvc.zookeeper.server.ServerGroupEnum;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcInterface {
	
	ServerGroupEnum value() ;
	
}
