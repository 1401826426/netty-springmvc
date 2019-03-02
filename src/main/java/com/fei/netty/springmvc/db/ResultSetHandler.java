package com.fei.netty.springmvc.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler {
	
	<T> T execute(ResultSet rs,Class<T> clazz) throws SQLException ; 
	
}

