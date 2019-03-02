package com.fei.netty.springmvc.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

public abstract class DataSourceExecutor extends AbstractExecutor{
	
//	private Logger logger = LoggerFactory.getLogger(DataSourceExecutor.class) ; 
	
	private DataSource ds ;

	@Override
	protected Connection getConnection() throws SQLException {
//		ConnectionHolder conHolder =
//				(ConnectionHolder) TransactionSynchronizationManager.getResource(this.ds);
//		if(conHolder == null){
//			conHolder = new ConnectionHolder(this.ds.getConnection()) ; 
//			TransactionSynchronizationManager.bindResource(this.ds, conHolder);
//		}
//		conHolder.requested();
//		return conHolder.getConnection();
		return DataSourceUtils.getConnection(this.ds) ; 
	}

	@Override
	protected void releaseConn(Connection connection) throws SQLException {
//		ConnectionHolder conHolder =
//				(ConnectionHolder) TransactionSynchronizationManager.getResource(this.ds);
//		if(conHolder != null){			
//			conHolder.released(); 
//		}else{
//			logger.warn("no connection to release");
//		}
//		connection.close();
		DataSourceUtils.releaseConnection(connection, this.ds);
	}



}
