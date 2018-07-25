package com.fei.test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@RequestMapping(value = "/test/get" , method = RequestMethod.GET)
	public Test testGet(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies = request.getCookies() ;
		StringBuilder sb = new StringBuilder("") ;
		if(cookies != null){
			for(Cookie cookie:cookies){
				sb.append(cookie.getName()+":"+cookie.getValue()+";");
			}
		}
		Test test = new Test() ;
		test.setHandler(sb.toString());
		test.setMethod("getGet");
		response.addCookie(new Cookie("test", "test"));
		response.addCookie(new Cookie("test1", "test1"));
		return test; 
	}
	
	
	@RequestMapping(value = "/test/post" , method = RequestMethod.POST)
	public Test testPost(@RequestBody Test test,HttpServletResponse response){
		response.addCookie(new Cookie("test", "test"));
		return test; 
	}
	
}
