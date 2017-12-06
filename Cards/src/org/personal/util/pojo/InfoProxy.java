package org.personal.util.pojo;

public class InfoProxy {
	private String host;
	private int port; 
	private int zoneId;
	
	public InfoProxy(String host, int port,int zoneId) {
		this.host = host;
		this.port = port;
		this.zoneId = zoneId;
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
	public int getZoneId() {
		return zoneId;
	}
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}
	
}
