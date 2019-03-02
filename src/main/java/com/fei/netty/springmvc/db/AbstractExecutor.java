package com.fei.netty.springmvc.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractExecutor implements Executor{
	
	protected abstract Connection getConnection() throws SQLException; 
	
	protected abstract void releaseConn(Connection connection) throws SQLException ; 

	protected abstract <T> ResultSetHandler getResultSetHandler() ; 
	
	
    public <T> T query(String sql,List<Param> params) throws SQLException{
    	return query(sql,params,getResultSetHandler()) ; 
    }
	
	
	@Override
	public <T> T query(String sql, List<Param> params,ResultSetHandler handler) throws SQLException {
		Connection connection = null; 
		try {
			connection = getConnection() ; 
			PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
			fullfillStatement(preparedStatement,params) ;
			ResultSet rs = preparedStatement.executeQuery() ; 
			return handler.execute(rs,null) ; 
		} finally {
			releaseConn(connection) ; 
		}
	}


	private void fullfillStatement(PreparedStatement preparedStatement, List<Param> params) throws SQLException {
		int index = 0 ;  
		for(Param param:params){
			if(param.obj == null){
				preparedStatement.setObject(index, null);
			}else{
				preparedStatement.setObject(index, param.obj,param.getSqlType());
			}
		}
	}


	@Override
	public void update(String sql, List<Param> params) throws SQLException {
		Connection connection = null; 
		try {
			connection = getConnection() ; 
			PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
			fullfillStatement(preparedStatement,params) ;
			preparedStatement.execute() ; 
		} finally {
			releaseConn(connection) ; 
		}
	}

	@Override
	public void batch(String sql, List<Param> params) throws SQLException {
		Connection connection = null; 
		try {
			connection = getConnection() ; 
			PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
			fullfillStatement(preparedStatement,params) ;
			int[] ress = preparedStatement.executeBatch();  
			for(int res:ress){
				if(res == Statement.EXECUTE_FAILED){
					throw new RuntimeException("batch exe failure") ; 
				}
			}
		} finally {
			releaseConn(connection) ; 
		}
	}

	@Override
	public void callProcedure(String sql, List<Param> params) throws SQLException {
		Connection connection = null; 
		try {
			connection = getConnection() ; 
			CallableStatement callableStatement = connection.prepareCall(sql) ; 
			fullfillStatement(callableStatement,params) ;
			callableStatement.execute() ; 
		} finally {
			releaseConn(connection) ; 
		}
	}

}
