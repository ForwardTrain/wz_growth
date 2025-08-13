package com.school.wz_growth.common.filter;


import org.springframework.web.cors.CorsUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {
    private String allowOrigin;
    private String allowMethods;
    private String allowCredentials;
    private String allowHeaders;
    private String exposeHeaders;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigin = filterConfig.getInitParameter("allowOrigin");
        allowMethods = filterConfig.getInitParameter("allowMethods");
        allowCredentials = filterConfig.getInitParameter("allowCredentials");
        allowHeaders = filterConfig.getInitParameter("allowHeaders");
        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");


        System.out.println("allowOrigin = " + allowOrigin);
        System.out.println("allowMethods = " + allowMethods);
        System.out.println("allowCredentials = " + allowCredentials);
        System.out.println("allowHeaders = " + allowHeaders);
        System.out.println("exposeHeaders = " + exposeHeaders);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) req;
        if (CorsUtils.isCorsRequest(request))
        {
            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); //解决跨域访问报错
            response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间 单位秒
        }

        if (CorsUtils.isPreFlightRequest(request))
        {
            return;
        }else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }


}
