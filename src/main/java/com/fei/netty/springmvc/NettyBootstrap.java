package com.fei.netty.springmvc;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.xml.sax.SAXException;

import com.fei.netty.springmvc.conf.Configuration;
import com.fei.netty.springmvc.conf.ConfigurationParser;
import com.fei.netty.springmvc.conf.NettyConf;
import com.fei.netty.springmvc.conf.SpringConf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyBootstrap {
	
	private String confPath ; 
	
	private Logger logger = LoggerFactory.getLogger(NettyBootstrap.class) ; 
	
	public NettyBootstrap(String confPath){
		this.confPath = confPath ; 
	}
	
	public void start(){
		try {
			Configuration conf = ConfigurationParser.parse(confPath) ;
			DispatcherServlet servlet = getDispatcherServlet(conf.getSpringConf()) ;
			startBootstrap(servlet, conf.getNettyConf());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private void startBootstrap(DispatcherServlet dispatcherServlet , NettyConf conf){
		NioEventLoopGroup bossGroup = new NioEventLoopGroup() ; 
		NioEventLoopGroup workerGroup = new NioEventLoopGroup() ; 
		try{
			ServerBootstrap bootstrap = new ServerBootstrap() ; 
			Channel channel = bootstrap.group(bossGroup,workerGroup)
			         .channel(NioServerSocketChannel.class)
			         .childHandler(new InitChannelHandler(dispatcherServlet)) 
			         .bind(conf.getPort())
			         .sync()
			         .channel();
			logger.info("server start port="+conf.getPort());
			channel.closeFuture().sync() ; 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			bossGroup.shutdownGracefully() ; 
			workerGroup.shutdownGracefully() ; 
		}
	}
	
	
	private DispatcherServlet getDispatcherServlet(SpringConf conf) throws ServletException {
		String springPath = conf.getSpringPath() ; 
		String springMvcPath = conf.getSpringMvcPath() ; 
		ServletContext servletContext = new MockServletContext() ;
		XmlWebApplicationContext wac = new XmlWebApplicationContext() ;
		servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,wac);
		wac.setConfigLocation(springPath);
		wac.refresh();
		DispatcherServlet dispatcherServlet = new DispatcherServlet() ; 
		MockServletConfig servletConfig = new MockServletConfig(servletContext) ;
		servletConfig.getServletContext() ;
		servletConfig.addInitParameter("contextConfigLocation", springMvcPath);
		dispatcherServlet.init(servletConfig);
		return dispatcherServlet ;
	}
	
}
