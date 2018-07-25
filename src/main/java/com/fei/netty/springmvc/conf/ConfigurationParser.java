package com.fei.netty.springmvc.conf;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigurationParser {

	public static Configuration parse(String path) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance() ; 
		DocumentBuilder db = dbf.newDocumentBuilder() ;
		ResourceLoader rl = new PathMatchingResourcePatternResolver() ; 
		Resource resource = rl.getResource(path) ; 
		Document dc = db.parse(resource.getInputStream()) ; 
		
		Configuration conf = new Configuration() ; 
		//parse netty conf
		Node confNode = dc.getElementsByTagName("conf").item(0) ;
		NodeList nl = confNode.getChildNodes() ; 
		for(int i = 0;i < nl.getLength();i++){
			Node node = nl.item(i) ; 
			if(node.getNodeName().equals("netty")){
				NettyConf nettyConf = parseNettyConf(node) ;
				conf.setNettyConf(nettyConf); 
			}
			if(node.getNodeName().equals("spring")){
				SpringConf springConf = parseSpringConf(node) ; 
				conf.setSpringConf(springConf);
			}
		}
		return conf;
	}

	
	private static NettyConf parseNettyConf(Node node){
		NodeList nll = node.getChildNodes() ;
		NettyConf nettyConf = new NettyConf() ; 
		for(int i = 0;i < nll.getLength();i++){
			Node nn = nll.item(i) ; 
			if(nn.getNodeName().equals("port")){
				nettyConf.setPort(Integer.valueOf(nn.getFirstChild().getTextContent()));
			}
		}
		return nettyConf ; 
	}
	
	private static SpringConf parseSpringConf(Node sn) {
		SpringConf springConf = new SpringConf() ;  
		for(int i = 0;i < sn.getChildNodes().getLength();i++){
			Node nns = sn.getChildNodes().item(i) ; 
			if(nns.getNodeName().equals("spring.path")){
				springConf.setSpringPath(nns.getFirstChild().getTextContent());
			}
			if(nns.getNodeName().equals("spring.mvc.path")){
				springConf.setSpringMvcPath(nns.getFirstChild().getTextContent());
			}
			
		}
		return springConf ; 
	}

}


















