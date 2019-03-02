package com.fei.netty.springmvc.db;

import java.sql.SQLException;
import java.util.List;

public interface Executor {
	
	<T> T query(String sql,List<Param> params)  throws SQLException; 
	
	<T> T query(String sql,List<Param> params,ResultSetHandler handler)  throws SQLException ; 
	
	void insert(String sql,List<Param> params)  throws SQLException; 
	
	void insert(String sql,List<Param> params,boolean autoCreate) throws SQLException ; 
	
	void update(String sql,List<Param> params)  throws SQLException ; 
	
	void batch(String sql,List<Param> params)  throws SQLException; 
	
	void callProcedure(String sql,List<Param> params)  throws SQLException; 
	
}
