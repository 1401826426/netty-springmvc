//package com.fei.netty.springmvc.deprecated;
//
//import com.fei.netty.springmvc.asyn.SynExecuteSupport;
//import com.fei.netty.springmvc.rpc.framework.common.RpcByteRequest;
//import com.fei.netty.springmvc.rpc.framework.common.RpcByteResponse;
//
//public abstract class AbstractSender extends SynExecuteSupport implements Sender{
//
//	@Override
//	public void sendAync(RpcByteRequest request, RpcSenderCallBack callBack) {
//		getExecutorService().execute(new Runnable() {
//			
//			@Override
//			public void run() {
//				try{
//					RpcByteResponse response = send(request) ;
//					if(callBack != null){						
//						callBack.success(response);
//					}
//				}catch (Exception e) {
//					if(callBack != null){
//						callBack.error(e);						
//					}
//				}
//				
//			}
//		});
//	}
//	
//	
//	
//	
//}
