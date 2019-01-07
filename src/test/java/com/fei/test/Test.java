package com.fei.test;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public String method ; 
	
	public String handler;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	} 
	
	
	public static void main(String[] args){
		List<Object> list = new ArrayList<>() ; 
		while(true){
			list.add(new Test()) ; 
		}
	}
	
	
	
}
