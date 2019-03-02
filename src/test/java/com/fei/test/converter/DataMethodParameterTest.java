package com.fei.test.converter;

import java.lang.reflect.Method;

import org.junit.Test;

import com.fei.netty.springmvc.converter.ConverterException;
import com.fei.netty.springmvc.converter.fackson.DataNodeMethodParameterConverter;

public class DataMethodParameterTest {
	
	@Test
	public void testA() throws NoSuchMethodException, SecurityException, ConverterException{
//		Class<?> clazz = A.class ; 
//		Method method = clazz.getMethod("test",int.class,int.class,String.class) ; 
//		byte[] bytes = new DataNodeMethodParameterConverter().serialize(method, 1,2,"c") ; 
//		System.out.println(new String(bytes));
//		Object[] objs = new DataNodeMethodParameterConverter().deserialize(method,new String(bytes)) ; 
//		for(Object obj:objs){
//			System.out.println(obj);
//		}
		
		Class<?> clazz = A.class ; 
		Method method = clazz.getMethod("test", B.class,C.class,String.class) ; 
		byte[] bytes = new DataNodeMethodParameterConverter().serialize(method,new B(1,"b"),new C(2,"d"),"c") ; 
		System.out.println(new String(bytes));
		Object[] objs = new DataNodeMethodParameterConverter().deserialize(method,bytes) ; 
		for(Object obj:objs){
			System.out.println(obj);
		}
	}
	
	
}


interface A{
	
	public void test(int a,int b,String c) ; 
	
	public void test(B a,C b,String c) ; 
	
}




