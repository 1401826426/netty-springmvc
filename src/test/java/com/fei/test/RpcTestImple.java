package com.fei.test;

import org.springframework.stereotype.Component;

@Component
public class RpcTestImple implements RpcTest{

	@Override
	public boolean test() {
		System.err.println("=============test================") ; 
		return true ; 
	}

}
