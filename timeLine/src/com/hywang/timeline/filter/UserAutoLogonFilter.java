package com.hywang.timeline.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hywang.timeline.entity.User;
import com.hywang.timeline.persistence.dao.UserDAO;

public class UserAutoLogonFilter implements Filter {

	private UserDAO userDao;
   
	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
        HttpSession session = httprequest.getSession();
        String userid = null;
        String username = null;
        String userpwd = null;
        String email = null;
        String fname = null;
        String lname = null;
        String sessionid = null; // 此sessionid是上次用户登录时保存于用户端的识别码，用于用户后续访问的自动登录。不是本次访问的session id。
        Cookie[] cookies;
        boolean isAutoLogin;
        User user = (User) session.getAttribute("user");
        if (user == null) { // if there is no user, need to autologon via cookies
            cookies = httprequest.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userid")) {
                        userid = cookie.getValue();
                        user = new User();
                        continue; //if find userid in cookies,means that the new user should be create.
                    }
                    if (cookie.getName().equals("username")) {
                        username = cookie.getValue();
                    }
                    if (cookie.getName().equals("userpwd")) {
                        userpwd = cookie.getValue();
                    }
                    if (cookie.getName().equals("email")) {
                        email = cookie.getValue();
                    }
                    if (cookie.getName().equals("fname")) {
                        fname = cookie.getValue();
                    }
                    if (cookie.getName().equals("lname")) {
                        lname = cookie.getValue();
                    }
                    if (cookie.getName().equals("sessionid")) {
                        sessionid = cookie.getValue();
                    }
                }
                try {
                    isAutoLogin = userDao.getAutoLoginState(username, sessionid);// 如果在数据库中找到了相应记录，则说明可以自动登录。
                    if (isAutoLogin) {
                        user.setId(Integer.parseInt(userid));
                        user.setUserName(username);
                        user.setUserPwd(userpwd);
                        user.setEmail(email);
                        user.setFirstName(fname);
                        user.setLastName(lname);
                        session.setAttribute("user", user); // 将user bean添加到session中。
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
    	userDao= (UserDAO) wac.getBean("userDao");
    }

}
