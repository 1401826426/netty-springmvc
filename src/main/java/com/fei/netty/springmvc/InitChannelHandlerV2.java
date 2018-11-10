package com.fei.netty.springmvc;

import org.springframework.web.servlet.DispatcherServlet;

import com.fei.netty.springmvc.conf.Configuration;
import com.fei.netty.springmvc.http.DefaultHttpServletRequestHandler;
import com.fei.netty.springmvc.http.DefaultHttpServletResponseHandler;
import com.fei.netty.springmvc.rpc.RpcShakeHandHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class InitChannelHandlerV2 extends ChannelInitializer<Channel>{
	
	private DispatcherServlet dispatchServlet ; 
	
	private Configuration conf;   
	
	private HttpServletResponseFactory factory ; 
	
	public InitChannelHandlerV2(DispatcherServlet dispatchServlet,Configuration conf,HttpServletResponseFactory factory) {
		super();
		this.dispatchServlet = dispatchServlet;
		this.conf = conf ; 
		this.factory = factory ; 
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast(new HttpServerCodec()) ; 
	    ch.pipeline().addLast(new HttpObjectAggregator(65536));
	    if(conf.getRpcConf() != null){	    	
	    	ch.pipeline().addLast(new RpcShakeHandHandler(conf.getRpcConf())) ;  
	    }
	    ch.pipeline().addLast(new DefaultHttpServletRequestHandler(dispatchServlet.getServletContext())) ;
	    ch.pipeline().addLast(new DefaultHttpServletResponseHandler()) ;  
	    ch.pipeline().addLast(new HttpServerHanderV2(dispatchServlet,factory)) ; 
	}

}













