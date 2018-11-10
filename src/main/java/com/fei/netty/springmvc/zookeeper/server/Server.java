package com.fei.netty.springmvc.zookeeper.server;

public class Server {
	
	private String host ; 
	
	private int port ; 
	
	private ServerGroupEnum group ;

	private int id ; 
	
	private String serverKey ; 
	
	public Server(ServerGroupEnum group, String host, int port, int id) {
		this.group = group ; 
		this.host = host ; 
		this.port = port ; 
		this.id = id  ; 
		this.serverKey = ServerKeyGenerator.generate(this) ; 
	}

	public Server() {
		super();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ServerGroupEnum getGroup() {
		return group;
	}

	public void setGroup(ServerGroupEnum group) {
		this.group = group;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServerKey() {
		return serverKey;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}
	
	
	
	@Override
	public String toString() {
		return "Server [host=" + host + ", port=" + port + ", group=" + group + ", id=" + id + ", serverKey="
				+ serverKey + "]";
	}  
	
	
	
}
