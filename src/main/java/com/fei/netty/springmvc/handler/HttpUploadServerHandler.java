package com.fei.netty.springmvc.handler;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.HttpObject;

public class HttpUploadServerHandler extends MessageToMessageDecoder<HttpObject>{

	@Override
	protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
		
	}

}
