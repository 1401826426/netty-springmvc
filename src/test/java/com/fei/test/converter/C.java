package com.fei.test.converter;

public class C{
	int c ; 
	String d ; 
	
	public C() {
		super();
	}
	C(int c,String d){
		this.c = c ; 
		this.d = d ; 
	}
	public int getC() {
		return c;
	}
	public void setC(int c) {
		this.c = c;
	}
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d = d;
	}
	@Override
	public String toString() {
		return "C [c=" + c + ", d=" + d + "]";
	}
	
	
}
