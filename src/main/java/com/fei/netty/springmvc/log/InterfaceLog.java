package com.fei.netty.springmvc.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterfaceLog {
	
	private static Logger logger = LoggerFactory.getLogger("com.fei.interface.log") ; 
	
	public static Logger getLogger(){
		return logger ; 
	}

	public static void logRequest(HttpServletRequest request) {
		String info = String.format("requests#url=%s#method=%s#contentType=%s#contentLength=%d",
				request.getRequestURL(),request.getMethod(),request.getContentType(),request.getContentLength()) ; 
		logger.info(info);
	}

	public static void logResponse(HttpServletRequest request,HttpServletResponse response) {
		String info = String.format("response#url=%s#statue=%d#contentType=%s",
				request.getRequestURL(),response.getStatus(),response.getContentType()) ; 
		logger.info(info);
		
	}
	
}
