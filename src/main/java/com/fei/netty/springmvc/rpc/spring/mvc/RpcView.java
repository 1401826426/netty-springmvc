package com.fei.netty.springmvc.rpc.spring.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.fei.netty.springmvc.rpc.common.RpcResponse;
import com.fei.netty.springmvc.rpc.socketrpc.RpcServletHttpResponse;
import com.fei.netty.springmvc.rpc.spring.SpringConstants;

public class RpcView implements View{
	
	@Override
	public String getContentType() {
		return "application/json";
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Object object = model.get(SpringConstants.RPC_RESPONSE) ; 
		if(object != null && object instanceof RpcResponse){
			RpcResponse rpcResponse = (RpcResponse)object ; 
			if(response instanceof RpcServletHttpResponse){
				RpcServletHttpResponse rpcServletHttpResponse = (RpcServletHttpResponse)response ; 
				rpcServletHttpResponse.setRpcResponse(rpcResponse);
			}
		}
	}

}
