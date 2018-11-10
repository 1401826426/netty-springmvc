package com.fei.netty.springmvc.http.session;

import javax.servlet.http.HttpSession;

public interface SessionListener {
	
	public void sessionCreate(HttpSession session) ; 
	
	public void sessionInvalid(HttpSession session) ; 
	
}
