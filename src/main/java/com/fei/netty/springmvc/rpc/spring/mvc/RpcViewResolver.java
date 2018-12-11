package com.fei.netty.springmvc.rpc.spring.mvc;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

import com.fei.netty.springmvc.rpc.spring.SpringConstants;

public class RpcViewResolver extends AbstractCachingViewResolver{

	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		if(SpringConstants.RPC_VIEW_NAME.equals(viewName)){			
			return new RpcView();
		}
		return null ; 
	}

}
