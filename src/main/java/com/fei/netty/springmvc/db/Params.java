package com.fei.netty.springmvc.db;

import java.sql.SQLType;
import java.util.ArrayList;

public class Params extends ArrayList<Param>{
	
	private static final long serialVersionUID = 1L;

	public void addParam(Param param){
		add(param) ; 
	}
	
	public void addParam(SQLType type,Object obj){
		add(new Param(obj, type)) ; 
	}
	
	public void addParam(Object obj){
		add(new Param(obj)) ; 
	}
	
}
