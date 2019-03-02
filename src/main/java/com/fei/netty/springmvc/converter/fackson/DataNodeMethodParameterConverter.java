package com.fei.netty.springmvc.converter.fackson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import com.fei.netty.springmvc.converter.ConverterException;
import com.fei.netty.springmvc.converter.MethodParameterConverter;

import util.data.DataEncoder;
import util.data.JsonNodeParser;
import util.data.node.IDataNode;
import util.data.node.ListNode;
import util.json.JsonDocument;

public class DataNodeMethodParameterConverter implements MethodParameterConverter{

	@Override
	public byte[] serialize(Method method, Object... args) throws ConverterException {
		DataEncoder encoder = new DataEncoder() ; 
		JsonDocument doc = new JsonDocument() ; 
		doc.startArray(); 
		try {
			boolean first = true ; 
			for(Object arg:args){
				if(!first){
					doc.append(",");
				}
				IDataNode dataNode = encoder.doEncode("", arg) ;
				doc.addJson(dataNode.toString());
				first = false; 
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ConverterException(e)  ;
		} 
		doc.endArray(); 
		return doc.toBytes();
	}

	@Override
	public Object[] deserialize(Method method, byte[] value)  throws ConverterException {
		IDataNode dataNode = new JsonNodeParser().parse(value) ; 
		if(!(dataNode instanceof ListNode)){
			throw new ConverterException(new RuntimeException()) ; 
		}
		Type[] types = method.getGenericParameterTypes() ; 
		ListNode listNode = (ListNode)dataNode ;
		List<IDataNode> nodes = listNode.getNodes() ; 
		int pos = 0 ; 
		Object[] objs = new Object[nodes.size()] ; 
		for(IDataNode node:nodes){
			objs[pos] = node.resolve(types[pos]) ; 
			pos++ ; 
		}
		return objs;
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, ConverterException{
		
	}
	
	
}





