package com.fei.test;

import org.springframework.stereotype.Component;

@Component
public class RpcTestImple implements RpcTest{

	@Override
	public void test() {
		System.err.println("=============test================") ; 
	}

}
