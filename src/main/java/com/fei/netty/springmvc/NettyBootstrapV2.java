package com.fei.netty.springmvc;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.fei.netty.springmvc.conf.Configuration;
import com.fei.netty.springmvc.conf.NettyConf;
import com.fei.netty.springmvc.conf.SpringConf;
import com.fei.netty.springmvc.deprecated.NettyBootstrap;
import com.fei.netty.springmvc.http.DefaultHttpServletResponseFactory;
import com.fei.netty.springmvc.http.DefaultServletConfig;
import com.fei.netty.springmvc.http.DefaultServletContext;
import com.fei.netty.springmvc.http.session.SessionManager;
import com.fei.netty.springmvc.rpc.data.response.RpcHttpResponseFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import util.data.DataParserBuilder;

public class NettyBootstrapV2 {
	
private String confPath ; 
	
	private Logger logger = LoggerFactory.getLogger(NettyBootstrap.class) ; 
	
	public NettyBootstrapV2(String confPath){
		this.confPath = confPath ; 
	}
	
	public void start(){
		try {
			ResourceLoader rl = new PathMatchingResourcePatternResolver() ; 
			Resource resource = rl.getResource(confPath) ;
			Configuration conf = DataParserBuilder.getInstance().getXmlDataParser().parse(Configuration.class, resource.getInputStream()) ;
			DispatcherServlet servlet = getDispatcherServlet(conf.getSpringConf()) ;		
			startBootstrap(servlet, conf);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private void startBootstrap(DispatcherServlet dispatcherServlet , Configuration configuration){
		NettyConf conf = configuration.getNettyConf() ; 
		NioEventLoopGroup bossGroup = new NioEventLoopGroup() ; 
		NioEventLoopGroup workerGroup = new NioEventLoopGroup() ; 
		try{
			ServerBootstrap bootstrap = new ServerBootstrap() ; 
			SessionManager.getInstance().sessionStart(conf); //启动session
			HttpServletResponseCompositeFactory factory = new HttpServletResponseCompositeFactory() ;
			factory.addFacotry(new RpcHttpResponseFactory());
			factory.addFacotry(new DefaultHttpServletResponseFactory());
			Channel channel = bootstrap.group(bossGroup,workerGroup)
			         .channel(NioServerSocketChannel.class)
			         .childHandler(new InitChannelHandlerV2(dispatcherServlet,configuration,factory)) 
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
		ServletContext servletContext = new DefaultServletContext() ;
		XmlWebApplicationContext wac = new XmlWebApplicationContext() ;
		servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,wac);
		wac.setConfigLocation(springPath);
		wac.refresh();
		DispatcherServlet dispatcherServlet = new DispatcherServlet() ; 
		DefaultServletConfig servletConfig = new DefaultServletConfig(servletContext) ;
		servletConfig.addInitParameter("contextConfigLocation", springMvcPath);
		dispatcherServlet.init(servletConfig);
		return dispatcherServlet ;
	}

	
}
