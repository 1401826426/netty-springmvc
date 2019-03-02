package com.fei.test;

import org.springframework.stereotype.Component;

import com.fei.test.converter.ResultDto;

@Component
public class RpcTestImple implements RpcTest{

	@Override
	public boolean test() {
		System.err.println("=============test================") ; 
		return true ; 
	}

	@Override
	public String testStr(String str) {
		return str ; 
	}

	@Override
	public ResultDto testA(TestDto1 dto1, TestDto2 dto2, String c) {
		ResultDto resultDto = new ResultDto() ; 
		resultDto.setValue(dto1.toString() + " "+dto2.toString() + c);
		return resultDto;
	}

}
