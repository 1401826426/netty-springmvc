package com.fei.netty.springmvc.converter.fackson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;

import com.fei.netty.springmvc.converter.Converter;
import com.fei.netty.springmvc.converter.ConverterException;

public class JavaConverter implements Converter{

	@Override
	public Object readValue(byte[] bytes, Type type) throws ConverterException {
		ByteArrayInputStream bis =  null ;  
		ObjectInputStream ois = null ;
		try {
			bis = new ByteArrayInputStream(bytes) ;
			ois = new ObjectInputStream(bis) ; 
			return ois.readObject() ; 
		} catch (IOException | ClassNotFoundException e) {
			throw new ConverterException(e) ; 
		} finally{
			if(ois != null){
				try{
					ois.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public byte[] writeValue(Object value) throws ConverterException {
		ByteArrayOutputStream bos = null;
 		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream() ;
			oos = new ObjectOutputStream(bos);
			oos.writeObject(value);
			return bos.toByteArray() ; 
		} catch (IOException e) {
			throw new ConverterException(e) ;
		}finally {
			if(oos != null){
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
