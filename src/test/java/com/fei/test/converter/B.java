package com.fei.test.converter;

public class B{
	int a ; 
	String b ; 
	public B() {
		super();
	}
	B(int a,String b){
		this.a = a ; 
		this.b = b ; 
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	@Override
	public String toString() {
		return "B [a=" + a + ", b=" + b + "]";
	}
	
}


