package com.hywang.timeline.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component("userAuthorizeInterceptor")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserAuthorizeInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 2862807663966062544L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		  ActionContext actionContext = invocation.getInvocationContext();
		  HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		
	        HttpSession session = request.getSession();
	        if (session != null) {
	            Object user = session.getAttribute("user");
	            // if user didn't exsist
	            if (user == null) {
	            	return Action.LOGIN;
	            } 
	        }
	        return invocation.invoke();
	}

}
