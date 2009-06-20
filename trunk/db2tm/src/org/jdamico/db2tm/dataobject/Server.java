package org.jdamico.db2tm.dataobject;

public class Server {
	
	private String dbUrl;
	private String dbAlias;
	private String port;
	private String dbName;
	private String user;
	private String passwd;
	private String host;
	private String testTime;
	
	
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
	
	public Server(String dbUrl, String dbAlias) {
		this.dbAlias = dbAlias;
		this.dbUrl = dbUrl;
	}
	public String getDbUrl() {
		return dbUrl;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public String getDbAlias() {
		return dbAlias;
	}
	public void setDbAlias(String dbAlias) {
		this.dbAlias = dbAlias;
	}
	
	
	

}
