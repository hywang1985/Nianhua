package com.hywang.timeline.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserAuthorizeFilter implements Filter {

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
        HttpServletResponse httpresponse = (HttpServletResponse) response;
        HttpSession session = httprequest.getSession();
        if (session != null) {
            Object user = session.getAttribute("user");
            // if user didn't exsist
            if (user == null) {
                String url = "/user/login.jsp";
                httpresponse.sendRedirect(url);
            } else {
                httprequest.getRequestDispatcher("/UserProfileServlet").forward(httprequest, httpresponse);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
