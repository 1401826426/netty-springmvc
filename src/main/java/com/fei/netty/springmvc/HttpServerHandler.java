package com.fei.netty.springmvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.DispatcherServlet;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;

public class HttpServerHandler extends ChannelHandlerAdapter {
	
	private DispatcherServlet dispatcherServlet ;
	
	public HttpServerHandler(DispatcherServlet dispatcherServlet){
		this.dispatcherServlet = dispatcherServlet ;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { 
		if(msg instanceof DefaultFullHttpRequest){
			DefaultFullHttpRequest nettyRequest = (DefaultFullHttpRequest)msg ;
			HttpServletRequest request = getServletRequest(nettyRequest) ; 
			MockHttpServletResponse response = new MockHttpServletResponse() ;
			String requestId = request.getHeader("requestId") ; 
			if(requestId != null){				
				response.addHeader("requestId",requestId);
			}
			DispatcherServlet dispatcherServlet = getDispatcherServlet() ; 
			dispatcherServlet.service(request, response);
			DefaultFullHttpResponse nettyResponse = getResponse(response) ;  
			ctx.writeAndFlush(nettyResponse).addListener(ChannelFutureListener.CLOSE) ; 
		}
	}

	private DefaultFullHttpResponse getResponse(MockHttpServletResponse response) {
		DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(response.getStatus())) ;
		HttpHeaders headers = httpResponse.headers() ;
		for(String name:response.getHeaderNames()){
			headers.add(name,response.getHeaderValue(name)) ; 
		}
		if(response.getCookies() != null){
			for(Cookie cookie:response.getCookies()){
				headers.add(HttpHeaders.Names.SET_COOKIE,HttpUtils.generateCookieString(cookie)) ; 
			}
		}
		byte[] bytes = response.getContentAsByteArray() ; 
		httpResponse.content().writeBytes(bytes) ; 
		return httpResponse;
	}

	private DispatcherServlet getDispatcherServlet() throws ServletException {
		return dispatcherServlet ; 
	}

	private HttpServletRequest getServletRequest(DefaultFullHttpRequest nettyRequest) {
		MockHttpServletRequest request = new MockHttpServletRequest(dispatcherServlet.getServletContext(),nettyRequest.getMethod().name(),nettyRequest.getUri()) ;
		HttpHeaders headers = nettyRequest.headers() ; 
		request.setContentType(headers.get(HttpHeaders.Names.CONTENT_TYPE.toString()));
//		request.setMethod(nettyRequest.getMethod().name());
		request.setProtocol(nettyRequest.getProtocolVersion().protocolName());
//		request.setRequestURI(nettyRequest.getUri());
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
		String cookiestr = headers.get(Names.COOKIE) ;
		if(cookiestr != null){
			Set<io.netty.handler.codec.http.Cookie> set = CookieDecoder.decode(cookiestr) ;
			if(set != null){
				for(io.netty.handler.codec.http.Cookie c:set){
					CookieAdapter adpter = new CookieAdapter(c); 
					list.add(adpter) ; 
				}
			}
			Cookie[] cookies = list.toArray(new Cookie[list.size()]) ;
			request.setCookies(cookies);
		}
		return request;
	}
	
	
	
	
}
