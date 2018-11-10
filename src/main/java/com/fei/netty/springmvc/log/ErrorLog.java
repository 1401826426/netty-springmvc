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
		for(StackTraceElement element:elements){
			sb.append(element.getClassName() + "#" + element.getMethodName() + "#" + element.getLineNumber() + "\n") ; 
		}
		logger.error(sb.toString());
	}
}
