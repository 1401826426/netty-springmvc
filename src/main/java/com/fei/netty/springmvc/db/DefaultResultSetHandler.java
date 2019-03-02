package com.fei.netty.springmvc.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import util.bean.BeanDescription;
import util.bean.BeanDescriptionParser;
import util.bean.PropertyDescription;
import util.str.StringUtils;

public class DefaultResultSetHandler implements ResultSetHandler {


	@SuppressWarnings("unchecked")
	@Override
	public <T> T execute(ResultSet rs, Class<T> clazz) throws SQLException {
		BeanDescription beanDescription = BeanDescriptionParser.getInstance().parse(clazz) ; 
		try {
			Object obj = clazz.newInstance() ;
			while(rs.next()){
				ResultSetMetaData rsmd = rs.getMetaData() ; 
				int cn = rsmd.getColumnCount() ; 
				for(int i = 1;i <= cn;i++){
					String name = rsmd.getColumnName(i) ; 
					String javaName = StringUtils.dbToJava(name) ; 
					PropertyDescription pd = beanDescription.getNamePdsMap().get(javaName) ;
					pd.getWriteMethod().invoke(obj) ; 
				}
			}
			return (T)obj ; 
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		return null;
	}

}











