package com.fei.netty.springmvc.conf;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.FixedRecvByteBufAllocator;

public class NettyClientConf {

//	public static final ChannelOption<MessageSizeEstimator> MESSAGE_SIZE_ESTIMATOR = valueOf("MESSAGE_SIZE_ESTIMATOR");
//	public static final ChannelOption<InetAddress> IP_MULTICAST_ADDR = valueOf("IP_MULTICAST_ADDR");
//	public static final ChannelOption<NetworkInterface> IP_MULTICAST_IF = valueOf("IP_MULTICAST_IF");
	private String allocator ; 
	private String rcvBufAllocator ;
    private int adaptiveRecvBufAllocatorMinimum;
    private int adaptiveRecvBufAllocatorInitial;
    private int adaptiveRecvBufAllocatorMaximum ; 
    private int fixedRecvByteBufAllocatorBufSize ; 
    private boolean tcpDelay ; 
    private boolean ipMulticastLoopDisable ; 
    private int ipMulticastTtl ; 
    private int ipTos ; 
    private int soTimeOut ; 
    private int soBackLog ; 
    private int soLinger ; 
    private boolean soReuseaddr ;  
    private int soRcvBuf ; 
    private int soSndBuf ;
    private boolean soKeepAlive ; 
    private boolean soBroadcast ; 
    private boolean autoRead ; 
    private boolean allowHalfClosure ; 
    private int writeBufferLowWaterMask ; 
    private int writeBufferHignWaterMask ; 
    private int writeSpinCount ;
    private int maxMessagesPerRead ; 
    private int connectTimeOutMills ; 
    
    private Map<ChannelOption<?>,Object> options ; 
    
 
	public String getAllocator() {
		return allocator;
	}



	public void setAllocator(String allocator) {
		this.allocator = allocator;
	}



	public String getRcvBufAllocator() {
		return rcvBufAllocator;
	}



	public void setRcvBufAllocator(String rcvBufAllocator) {
		this.rcvBufAllocator = rcvBufAllocator;
	}



	public int getAdaptiveRecvBufAllocatorMinimum() {
		return adaptiveRecvBufAllocatorMinimum;
	}



	public void setAdaptiveRecvBufAllocatorMinimum(int adaptiveRecvBufAllocatorMinimum) {
		this.adaptiveRecvBufAllocatorMinimum = adaptiveRecvBufAllocatorMinimum;
	}



	public int getAdaptiveRecvBufAllocatorInitial() {
		return adaptiveRecvBufAllocatorInitial;
	}



	public void setAdaptiveRecvBufAllocatorInitial(int adaptiveRecvBufAllocatorInitial) {
		this.adaptiveRecvBufAllocatorInitial = adaptiveRecvBufAllocatorInitial;
	}



	public int getAdaptiveRecvBufAllocatorMaximum() {
		return adaptiveRecvBufAllocatorMaximum;
	}



	public void setAdaptiveRecvBufAllocatorMaximum(int adaptiveRecvBufAllocatorMaximum) {
		this.adaptiveRecvBufAllocatorMaximum = adaptiveRecvBufAllocatorMaximum;
	}



	public int getFixedRecvByteBufAllocatorBufSize() {
		return fixedRecvByteBufAllocatorBufSize;
	}



	public void setFixedRecvByteBufAllocatorBufSize(int fixedRecvByteBufAllocatorBufSize) {
		this.fixedRecvByteBufAllocatorBufSize = fixedRecvByteBufAllocatorBufSize;
	}



	public boolean isTcpDelay() {
		return tcpDelay;
	}



	public void setTcpDelay(boolean tcpDelay) {
		this.tcpDelay = tcpDelay;
	}



	public boolean isIpMulticastLoopDisable() {
		return ipMulticastLoopDisable;
	}



	public void setIpMulticastLoopDisable(boolean ipMulticastLoopDisable) {
		this.ipMulticastLoopDisable = ipMulticastLoopDisable;
	}



	public int getIpMulticastTtl() {
		return ipMulticastTtl;
	}



	public void setIpMulticastTtl(int ipMulticastTtl) {
		this.ipMulticastTtl = ipMulticastTtl;
	}



	public int getIpTos() {
		return ipTos;
	}



	public void setIpTos(int ipTos) {
		this.ipTos = ipTos;
	}



	public int getSoTimeOut() {
		return soTimeOut;
	}



	public void setSoTimeOut(int soTimeOut) {
		this.soTimeOut = soTimeOut;
	}



	public int getSoBackLog() {
		return soBackLog;
	}



	public void setSoBackLog(int soBackLog) {
		this.soBackLog = soBackLog;
	}



	public int getSoLinger() {
		return soLinger;
	}



	public void setSoLinger(int soLinger) {
		this.soLinger = soLinger;
	}



	public boolean isSoReuseaddr() {
		return soReuseaddr;
	}



	public void setSoReuseaddr(boolean soReuseaddr) {
		this.soReuseaddr = soReuseaddr;
	}



	public int getSoRcvBuf() {
		return soRcvBuf;
	}



	public void setSoRcvBuf(int soRcvBuf) {
		this.soRcvBuf = soRcvBuf;
	}



	public int getSoSndBuf() {
		return soSndBuf;
	}



	public void setSoSndBuf(int soSndBuf) {
		this.soSndBuf = soSndBuf;
	}



	public boolean isSoKeepAlive() {
		return soKeepAlive;
	}



	public void setSoKeepAlive(boolean soKeepAlive) {
		this.soKeepAlive = soKeepAlive;
	}



	public boolean isSoBroadcast() {
		return soBroadcast;
	}



	public void setSoBroadcast(boolean soBroadcast) {
		this.soBroadcast = soBroadcast;
	}



	public boolean isAutoRead() {
		return autoRead;
	}



	public void setAutoRead(boolean autoRead) {
		this.autoRead = autoRead;
	}



	public boolean isAllowHalfClosure() {
		return allowHalfClosure;
	}



	public void setAllowHalfClosure(boolean allowHalfClosure) {
		this.allowHalfClosure = allowHalfClosure;
	}



	public int getWriteBufferLowWaterMask() {
		return writeBufferLowWaterMask;
	}



	public void setWriteBufferLowWaterMask(int writeBufferLowWaterMask) {
		this.writeBufferLowWaterMask = writeBufferLowWaterMask;
	}



	public int getWriteBufferHignWaterMask() {
		return writeBufferHignWaterMask;
	}



	public void setWriteBufferHignWaterMask(int writeBufferHignWaterMask) {
		this.writeBufferHignWaterMask = writeBufferHignWaterMask;
	}



	public int getWriteSpinCount() {
		return writeSpinCount;
	}



	public void setWriteSpinCount(int writeSpinCount) {
		this.writeSpinCount = writeSpinCount;
	}



	public int getMaxMessagesPerRead() {
		return maxMessagesPerRead;
	}



	public void setMaxMessagesPerRead(int maxMessagesPerRead) {
		this.maxMessagesPerRead = maxMessagesPerRead;
	}



	public int getConnectTimeOutMills() {
		return connectTimeOutMills;
	}



	public void setConnectTimeOutMills(int connectTimeOutMills) {
		this.connectTimeOutMills = connectTimeOutMills;
	}


	public Map<ChannelOption<?>, Object> getOptions(){
		if(this.options == null){
			this.options = buildOptions() ; 
		}
		return this.options ; 
	}

	public Map<ChannelOption<?>, Object> buildOptions() { 
		Map<ChannelOption<?>,Object> options = new HashMap<>() ;
		switch(allocator){
		case "pool":
			options.put(ChannelOption.ALLOCATOR, new PooledByteBufAllocator()) ;
			break; 
		case "directUnpool":
			options.put(ChannelOption.ALLOCATOR, new UnpooledByteBufAllocator(true)) ;
			break ; 
		case "unpool":
			options.put(ChannelOption.ALLOCATOR, new UnpooledByteBufAllocator(false)) ;
			break ; 
		default:
			options.put(ChannelOption.ALLOCATOR, new PooledByteBufAllocator()) ;
			break ; 
		}
		
		switch(rcvBufAllocator){
		case "adaptive":
			options.put(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(adaptiveRecvBufAllocatorMinimum,adaptiveRecvBufAllocatorInitial,adaptiveRecvBufAllocatorMaximum)) ;
			break ; 
		case "fixed":
			options.put(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(fixedRecvByteBufAllocatorBufSize)) ;
			break ; 
		default:
			options.put(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT) ;
		}
		
		options.put(ChannelOption.TCP_NODELAY, tcpDelay) ; 
		options.put(ChannelOption.IP_MULTICAST_LOOP_DISABLED, ipMulticastLoopDisable) ;
		options.put(ChannelOption.IP_MULTICAST_TTL, ipMulticastTtl) ;
		options.put(ChannelOption.IP_TOS, ipTos) ;
		options.put(ChannelOption.SO_TIMEOUT, soTimeOut) ;
		options.put(ChannelOption.SO_BACKLOG, soBackLog) ;
		options.put(ChannelOption.SO_LINGER, soLinger) ;
		options.put(ChannelOption.SO_REUSEADDR, soReuseaddr) ;
		options.put(ChannelOption.SO_RCVBUF, soRcvBuf) ;
		options.put(ChannelOption.SO_SNDBUF, soSndBuf) ;
		options.put(ChannelOption.SO_KEEPALIVE, soKeepAlive) ;
		options.put(ChannelOption.SO_BROADCAST, soBroadcast) ;
		options.put(ChannelOption.AUTO_READ, autoRead) ;
		options.put(ChannelOption.ALLOW_HALF_CLOSURE,allowHalfClosure) ;
		options.put(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, writeBufferLowWaterMask) ;
		options.put(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, writeBufferHignWaterMask) ;
		options.put(ChannelOption.WRITE_SPIN_COUNT,writeSpinCount) ;
		options.put(ChannelOption.MAX_MESSAGES_PER_READ,maxMessagesPerRead) ;
		options.put(ChannelOption.CONNECT_TIMEOUT_MILLIS,connectTimeOutMills) ;
		options.put(ChannelOption.MAX_MESSAGES_PER_READ,maxMessagesPerRead) ;
		options.put(ChannelOption.MAX_MESSAGES_PER_READ,maxMessagesPerRead) ;

		
		return options;
	}

}
