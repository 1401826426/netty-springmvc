package com.fei.netty.springmvc.http;

import java.util.List;

import javax.servlet.http.Cookie;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class DefaultHttpServletResponseHandler extends MessageToMessageEncoder<DefaultHttpServletResponse>{

	@Override
	protected void encode(ChannelHandlerContext ctx, DefaultHttpServletResponse msg, List<Object> out) throws Exception {
		DefaultFullHttpResponse nettyResponse = getResponse(msg) ;  
		out.add(nettyResponse) ; 
	}
	
	private DefaultFullHttpResponse getResponse(DefaultHttpServletResponse response) {
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

}
