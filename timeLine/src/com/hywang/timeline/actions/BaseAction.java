package com.hywang.timeline.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {
	
	//name must be the subclass name
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	private static final long serialVersionUID = -5051733506121706971L;

	protected HttpServletResponse httpServletResponse;
	
	protected HttpServletRequest httpServletRequest;
	
	protected SessionMap<String,Object> session;

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.httpServletResponse=response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.httpServletRequest=request;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=(SessionMap<String,Object>)session;
	}

}
