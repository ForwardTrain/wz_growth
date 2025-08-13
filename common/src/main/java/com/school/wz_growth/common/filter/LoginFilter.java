package com.school.wz_growth.common.filter;


import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.constant.WebConstant;
import com.school.wz_growth.common.response.ResponseVoResultCode;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊初始化session与权限过滤器＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/plain;charset=UTF-8");

        HttpServletResponse response = (HttpServletResponse) servletResponse;


        if (this.ChechsPrex(request.getPathInfo())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //todo 登录接口判断校验 后期可以使用通配符符合规则的放开
            if (WebConstant.PASS_REQUEST.contains(request.getPathInfo())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {

                //判断用户是否登录或是否具有此路径权限
                if (!LoginUtil.checkLoginForUserOrRole(request, response)) {
                    //未登录 或 没有路径权限
                    System.out.println("*************未登录 或 没有路径权限******************");
                    System.out.println(request.getContentType());
                    System.out.println(request.getPathInfo());

                    if (null == request.getContentType()) {
                        servletResponse.setContentType("text/html;charset=UTF-8");
                        servletResponse.getWriter().println("<script language=\"javascript\">if(window.opener==null){window.top.location.href=\"../login\";}else{window.opener.top.location.href=\"../login\";window.close();}</script>");
                    } else {
                        if (request.getContentType().equals("application/json; charset=UTF-8")) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("result", ResponseVoResultCode.CODE_AUTHORITY);
                            servletResponse.getWriter().println(jsonObject);
                        } else {
                            servletResponse.setContentType("text/html;charset=UTF-8");
                            servletResponse.getWriter().println("<script language=\"javascript\">if(window.opener==null){window.top.location.href=\"../login\";}else{window.opener.top.location.href=\"../login\";window.close();}</script>");
                        }
                    }
                } else {
                    //已登录

                    if (request.getPathInfo().equals("/index")) {
                        filterChain.doFilter(servletRequest, servletResponse);
                    } else {
                        boolean tag = true;
                        if (tag) {
                            filterChain.doFilter(servletRequest, servletResponse);
                        } else {
                            servletResponse.setContentType("text/html;charset=UTF-8");
                            servletResponse.getWriter().println("<script language=\"javascript\">if(window.opener==null){window.top.location.href=\"../login\";}else{window.opener.top.location.href=\"../login\";window.close();}</script>");
                        }
                    }
                }
            }
        }
    }

    public void destroy() {
    }

    /**
     * 校验后缀名
     *
     * @return
     */
    private boolean ChechsPrex(String path) {
        if (path.indexOf(".jpg") >= 0 ||
                path.indexOf(".zip") >= 0 ||
                path.indexOf(".png") >= 0 ||
                path.indexOf(".rar") >= 0 ||
                path.indexOf(".css") >= 0 ||
                path.indexOf(".js") >= 0 ||
                path.indexOf(".woff") >= 0 ||
                path.indexOf(".woff2") >= 0 ||
                path.indexOf(".ttf") >= 0 ||
                path.indexOf(".svg") >= 0 ||
                path.indexOf(".eot") >= 0 ||
                path.indexOf(".otf") >= 0 ||
                path.indexOf(".ico") >= 0 ||
                path.indexOf(".gif") >= 0 ||
                path.indexOf(".xls") >= 0 ||
                path.indexOf(".properties") >= 0
                ) {
            return true;
        } else {
            return false;
        }
    }
}
