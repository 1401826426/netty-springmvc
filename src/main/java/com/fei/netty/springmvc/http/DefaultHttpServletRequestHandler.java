package com.fei.netty.springmvc.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fei.netty.springmvc.http.session.SessionConstans;
import com.fei.netty.springmvc.http.session.SessionManager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;

public class DefaultHttpServletRequestHandler extends MessageToMessageDecoder<DefaultFullHttpRequest>{
	
	private ServletContext servletContext ;
	
	public DefaultHttpServletRequestHandler(ServletContext servletContext) {
		super();
		this.servletContext = servletContext;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, DefaultFullHttpRequest msg, List<Object> out) throws Exception {
		DefaultFullHttpRequest nettyRequest = (DefaultFullHttpRequest)msg ;
		HttpServletRequest request = getServletRequest(nettyRequest) ; 
		out.add(request) ; 
	}
	
	private HttpServletRequest getServletRequest(DefaultFullHttpRequest nettyRequest) {
		DefaultHttpServletRequest request = new DefaultHttpServletRequest(servletContext,nettyRequest.getMethod().name(),nettyRequest.getUri()) ;
		HttpHeaders headers = nettyRequest.headers() ; 
		request.setContentType(headers.get(HttpHeaders.Names.CONTENT_TYPE.toString()));
		request.setProtocol(nettyRequest.getProtocolVersion().protocolName());
		if(nettyRequest.getMethod() == HttpMethod.GET){
			QueryStringDecoder decoder = new QueryStringDecoder(nettyRequest.getUri()) ; 
			Map<String,List<String>> tmps = decoder.parameters() ; 
			for(Map.Entry<String,List<String>> entry:tmps.entrySet()){
				String[] strs = new String[entry.getValue().size()] ; 
				strs = entry.getValue().toArray(strs) ; 
				request.addParameter(entry.getKey(),strs);
			}
		}
		for(Entry<String, String> entry : headers.entries()){
			request.addHeader(entry.getKey(), entry.getValue());
		}
		if(nettyRequest.content().readableBytes() > 0){
			int len = nettyRequest.content().readableBytes() ; 
			byte[] bytes = new byte[len] ; 
			nettyRequest.content().readBytes(bytes) ; 
			request.setContent(bytes);
		}
		List<Cookie> list = new ArrayList<Cookie>() ;
		String sessionId = null ; 
		String cookiestr = headers.get(Names.COOKIE) ;
		if(cookiestr != null){
			Set<io.netty.handler.codec.http.Cookie> set = CookieDecoder.decode(cookiestr) ;
			if(set != null){
				for(io.netty.handler.codec.http.Cookie c:set){
					CookieAdapter adpter = new CookieAdapter(c); 
					if(c.getName().equals(SessionConstans.SESSION_ID_COOKIE_NAME)){
						sessionId = c.getValue() ; 
					}
					list.add(adpter) ; 
				}
			}
			Cookie[] cookies = list.toArray(new Cookie[list.size()]) ;
			request.setCookies(cookies);
		}
		HttpSession session = null  ; 
		if(sessionId != null){
			session = SessionManager.getInstance().get(sessionId,false) ;
			if(session == null){
				session = SessionManager.getInstance().create() ;
			}else{
				SessionManager.getInstance().access(session.getId()); 
			}
		}else{
			session = SessionManager.getInstance().create() ; 
		}
		request.setSession(session);
		return request;
	}
	
	
}
