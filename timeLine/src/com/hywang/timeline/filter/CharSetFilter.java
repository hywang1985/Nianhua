package com.hywang.timeline.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Controller;

@Controller
public class CharSetFilter implements Filter {

    private static final String TIMELINE_CHARSET = "UTF-8"; //$NON-NLS-N$

    public void destroy() {

    }

    /*For the whole website of nianhua,the charset should be use UTF-8,both on database and jsp and java side*/
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	request.setCharacterEncoding(TIMELINE_CHARSET);
        response.setCharacterEncoding(TIMELINE_CHARSET);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig arg0) throws ServletException {

    }

}
