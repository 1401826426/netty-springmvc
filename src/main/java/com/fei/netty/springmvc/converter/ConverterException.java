package com.fei.netty.springmvc.converter;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ConverterException extends Exception{

	private static final long serialVersionUID = 1L;

	private Exception e ;
	
	public ConverterException(Exception e) {
		super();
		e.printStackTrace();
		this.e = e;
	}

	@Override
	public String getMessage() {
		return e.getMessage() ; 
	}

	@Override
	public String getLocalizedMessage() {
		return e.getLocalizedMessage() ; 
	}

	@Override
	public synchronized Throwable getCause() {
		return e.getCause() ; 
	}

	@Override
	public synchronized Throwable initCause(Throwable cause) {
		return e.initCause(cause) ; 
	}

	@Override
	public String toString() {
		return e.toString() ; 
	}

	@Override
	public void printStackTrace() {
		e.printStackTrace() ;
	}

	@Override
	public void printStackTrace(PrintStream s) {
		e.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		e.printStackTrace(s);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return e.fillInStackTrace() ; 
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return e.getStackTrace() ; 
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		e.setStackTrace(stackTrace);
	} 
	
	
	
}
