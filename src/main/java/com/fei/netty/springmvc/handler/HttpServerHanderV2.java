package com.fei.netty.springmvc.handler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.DispatcherServlet;

import com.fei.netty.springmvc.HttpServletResponseFactory;
import com.fei.netty.springmvc.http.session.SessionCookie;
import com.fei.netty.springmvc.log.ErrorLog;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class HttpServerHanderV2 extends ChannelHandlerAdapter{
	
	private DispatcherServlet servlet ;
	
	private HttpServletResponseFactory factory ; 
	
	public HttpServerHanderV2(DispatcherServlet servlet,HttpServletResponseFactory factory){
		this.servlet = servlet ; 
		this.factory = factory ; 
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof HttpServletRequest){
			HttpServletRequest request = (HttpServletRequest)msg ;
			String connection = request.getHeader("Connection") ;
			HttpServletResponse response = factory.getHttpServletResponse(request) ;
			if(response == null){
				ErrorLog.getLogger().error("no match response " + response);
				ctx.close() ; 
			}
			servlet.service(request, response);
			HttpSession session = request.getSession() ;
			if(session != null){				
				Cookie cookie = new SessionCookie(session) ; 
				response.addCookie(cookie);
			}
			ChannelFuture future = ctx.writeAndFlush(response) ;
			if(!"keep-alive".equals(connection)){
				future.addListener(ChannelFutureListener.CLOSE) ; 
			}
			return ; 
		}
		ctx.fireChannelRead(msg) ;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ErrorLog.error(cause);
		ctx.pipeline().close() ;  
	}	
	
	
	
}














