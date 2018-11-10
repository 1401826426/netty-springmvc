package com.fei.netty.springmvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseCompositeFactory implements HttpServletResponseFactory{
	
	private List<HttpServletResponseFactory> factories= new ArrayList<>() ; 

	@Override
	public HttpServletResponse getHttpServletResponse(HttpServletRequest request) {
		if(factories == null){
			return null ; 
		}
		for(HttpServletResponseFactory factory:factories){
			HttpServletResponse response = factory.getHttpServletResponse(request) ; 
			if(response != null){
				return response ; 
			}
		}
		return null;
	}
	
	public void addFacotry(HttpServletResponseFactory factory){
		this.factories.add(factory) ; 
	}
	
}
