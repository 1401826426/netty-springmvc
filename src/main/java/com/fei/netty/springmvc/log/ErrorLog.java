package com.fei.netty.springmvc.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorLog {

	private static Logger logger = LoggerFactory.getLogger("com.fei.interface.log") ; 
	
	public static Logger getLogger(){
		return logger ; 
	}

	public static void error(Throwable cause){
		StackTraceElement[] elements = cause.getStackTrace() ;
		StringBuilder sb = new StringBuilder("") ;
		sb.append(cause.getLocalizedMessage() + "\t"+cause.getMessage() + "\n") ; 
		for(StackTraceElement element:elements){
			sb.append("\t" + element.toString() + "\n") ;  
//			sb.append("\t" + element.getMethodName() + "(" + element.getClassName()+".java:"+element.getLineNumber()+")\n") ; 
		}
		logger.error(sb.toString());
	}
}
