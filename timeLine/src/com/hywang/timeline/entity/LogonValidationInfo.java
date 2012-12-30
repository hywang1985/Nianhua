package com.hywang.timeline.entity;

import java.io.Serializable;

public class LogonValidationInfo implements Serializable {

	private static final long serialVersionUID = -4089761953596027315L;
	
	private int id;
	
	private String userName;
	
	private String sessionid;
	
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	
}
