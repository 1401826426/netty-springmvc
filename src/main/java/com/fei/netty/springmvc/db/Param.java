package com.fei.netty.springmvc.db;

import java.sql.SQLType;

public class Param {
	
	public Object obj ; 
	
	public SQLType type ;

	public Param(Object obj, SQLType type) {
		super();
		this.obj = obj;
		this.type = type;
	}

	public Param(Object obj) {
		super();
		this.obj = obj;
	} 
	
	public SQLType getSqlType(){
		return this.type  ;
	}

}
