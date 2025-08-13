package com.school.wz_growth.common.filter;


import com.school.wz_growth.common.constant.WebConstant;
import com.school.wz_growth.common.redis.JedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;


public class AppTokenCheckFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{

            if (this.ChechsPrex(request.getPathInfo())) {
                filterChain.doFilter(request, response);
                return;
            }
            if(!WebConstant.PASS_REQUEST.contains(request.getPathInfo().toString())) {

                Enumeration<String> headers=request.getHeaderNames();
                Enumeration<String> zz=request.getHeaders("Authorization");
                String token = request.getHeader("Authorization");
                System.out.println("获取token:"+token);
                if (StringUtils.isBlank(token)) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json;charset=UTF-8");
                    PathCheckMsgUtil.writeMsgToPage(request, response,HttpStatus.UNAUTHORIZED.value(), "您的身份验证已过期, 请重新登录", "no token");
                }
                else{
                    Map<String, Object> tokenMap=JwtUtil.validateToken(token);
                    System.out.println("********************token进入*************");
                    request.setAttribute("u_name",tokenMap.get("u_name"));
                    request.setAttribute("uId",tokenMap.get("uId"));
                    request.setAttribute("role_type",tokenMap.get("role_type"));
                    System.out.println("tokenMap:"+tokenMap);
                    if(!tokenMap.containsKey("uId") || !tokenMap.containsKey("role_type")){
                        PathCheckMsgUtil.writeMsgToPage(request, response,HttpStatus.UNAUTHORIZED.value(), "您的身份验证已过期, 请重新登录", "no token");
                    }
                    else{
                        String token_key=WebConstant.JWT_TOKEN_KEY_+"_"+tokenMap.get("role_type")+"_"+tokenMap.get("uId");
                        try(Jedis jedis = JedisUtil.jedisPool.getResource()) {
                            if(jedis.get(token_key) !=null){
//                                JedisUtil.jedisPool.close();
                                filterChain.doFilter(request, response);
                            }
                            else{
//                                JedisUtil.jedisPool.close();
                                PathCheckMsgUtil.writeMsgToPage(request, response,HttpStatus.UNAUTHORIZED.value(), "您的身份验证已过期, 请重新登录", "no token");
                            }
                        }

                    }
                }
            }
            else{

                //如果jwt令牌通过了检测, 那么就把request传递给后面的RESTful api
                filterChain.doFilter(request, response);
            }
     }
        catch (Exception e){
            PathCheckMsgUtil.writeMsgToPage(request, response,HttpStatus.UNAUTHORIZED.value(), "您的身份验证已过期, 请重新登录", "no token");
        }
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
                path.indexOf(".xlsx") >= 0 ||
                path.indexOf(".properties") >= 0
                ) {
            return true;
        } else {
            return false;
        }
    }
}
