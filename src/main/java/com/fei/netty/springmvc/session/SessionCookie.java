package com.fei.netty.springmvc.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class SessionCookie extends Cookie{

	private static final long serialVersionUID = 1L;

	public SessionCookie(HttpSession session){
		super(SessionConstans.SESSION_ID_COOKIE_NAME, session.getId()) ;
	}
	
	

}
