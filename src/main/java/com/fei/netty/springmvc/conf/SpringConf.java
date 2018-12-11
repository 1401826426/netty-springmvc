package com.fei.netty.springmvc.conf;

public class SpringConf implements Initializer{
	
	private String springPath ; 
	
	private String springMvcPath ;

	public String getSpringPath() {
		return springPath;
	}

	public void setSpringPath(String springPath) {
		this.springPath = springPath;
	}

	public String getSpringMvcPath() {
		return springMvcPath;
	}

	public void setSpringMvcPath(String springMvcPath) {
		this.springMvcPath = springMvcPath;
	}

	@Override
	public void ini() {
		
	} 
	
	
	
}
