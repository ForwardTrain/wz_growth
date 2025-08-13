package com.school.wz_growth.common.filter;

import com.school.wz_growth.common.constant.WebConstant;
import com.school.wz_growth.common.redis.JedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

//@Component
public class HeaderTokenInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(HeaderTokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


//         如果是OPTIONS则结束请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return false;
        }

        if (this.ChechsPrex(request.getPathInfo())) {
            return true;
        }
        response.setHeader("sys_version", "1.0.0"); //微信版本号
        if(WebConstant.PASS_REQUEST.contains(request.getPathInfo().toString())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        System.out.println("获取token:"+token);
        if (StringUtils.isBlank(token)) {
            Map<String, String> map = new HashMap<>();
            map.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
            map.put("msg", "请登录后再操作");
            map.put("content","您的身份验证已过期, 请重新登录");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            RenderUtil.renderJson(response, map,request);
            return false;
        }
        else{
            Map<String, Object> tokenMap=JwtUtil.validateToken(token);
            System.out.println("********************token进入*************");
            request.setAttribute("u_name",tokenMap.get("u_name"));
            request.setAttribute("uId",tokenMap.get("uId"));
            request.setAttribute("role_type",tokenMap.get("role_type"));
            System.out.println("tokenMap:"+tokenMap);
            if(!tokenMap.containsKey("uId") || !tokenMap.containsKey("role_type"))
            {
                Map<String, String> map = new HashMap<>();
                map.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
                map.put("msg", "请登录后再操作");
                map.put("content","您的身份验证已过期, 请重新登录");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());

                RenderUtil.renderJson(response, map,request);
            }else
            {
                String token_key=WebConstant.JWT_TOKEN_KEY_+"_"+tokenMap.get("role_type")+"_"+tokenMap.get("uId");
                try(Jedis jedis = JedisUtil.jedisPool.getResource()) {
                    if(jedis.get(token_key) !=null){
//                         JedisUtil.jedisPool.close();
                        return true;
                    }
                    else{
                        Map<String, String> map = new HashMap<>();
                        map.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
                        map.put("msg", "请登录后再操作");
                        map.put("content","您的身份验证已过期, 请重新登录");
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        RenderUtil.renderJson(response, map,request);
//                         JedisUtil.jedisPool.close();
                        return false;
                    }
                }

            }
        }
        return true;
    }


        public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
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


//=================== ==============================
//    HandlerInterceptor  原来的类
//    private final static Logger logger = LoggerFactory.getLogger(HeaderTokenInterceptor.class);
//
//
//     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
//
//         String method = request.getMethod();
//         //options请求直接放行
//         if(method.equals("OPTIONS")){
//             return true;
//         }
//         if (this.ChechsPrex(request.getPathInfo())) {
//             return true;
//         }
//         if(WebConstant.PASS_REQUEST.contains(request.getPathInfo().toString())) {
//             return true;
//         }
//
//         String token = request.getHeader("Authorization");
//         System.out.println("获取token:"+token);
//         if (StringUtils.isBlank(token)) {
//             Map<String, String> map = new HashMap<>();
//             map.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
//             map.put("msg", "请登录后再操作");
//             map.put("content","您的身份验证已过期, 请重新登录");
//             response.setStatus(HttpStatus.UNAUTHORIZED.value());
//             RenderUtil.renderJson(response, map,request);
//             return false;
//         }
//         else{
//             Map<String, Object> tokenMap=JwtUtil.validateToken(token);
//             System.out.println("********************token进入*************");
//             request.setAttribute("u_name",tokenMap.get("u_name"));
//             request.setAttribute("uId",tokenMap.get("uId"));
//             request.setAttribute("role_type",tokenMap.get("role_type"));
//             System.out.println("tokenMap:"+tokenMap);
//             if(!tokenMap.containsKey("uId") || !tokenMap.containsKey("role_type")){
//                 Map<String, String> map = new HashMap<>();
//                 map.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
//                 map.put("msg", "请登录后再操作");
//                 map.put("content","您的身份验证已过期, 请重新登录");
//                 response.setStatus(HttpStatus.UNAUTHORIZED.value());
//
//                 RenderUtil.renderJson(response, map,request);
//             }
//             else{
//                 String token_key=WebConstant.JWT_TOKEN_KEY_+"_"+tokenMap.get("role_type")+"_"+tokenMap.get("uId");
//                 try(Jedis jedis = JedisUtil.jedisPool.getResource()) {
//                     if(jedis.get(token_key) !=null){
////                         JedisUtil.jedisPool.close();
//                         return true;
//                     }
//                     else{
//                         Map<String, String> map = new HashMap<>();
//                         map.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
//                         map.put("msg", "请登录后再操作");
//                         map.put("content","您的身份验证已过期, 请重新登录");
//                         response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                         RenderUtil.renderJson(response, map,request);
////                         JedisUtil.jedisPool.close();
//                         return false;
//                     }
//                 }
//
//             }
//         }
//         return true;
//     }
//
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//    }
//
//        /**
//         * 校验后缀名
//         *
//         * @return
//         */
//        private boolean ChechsPrex(String path) {
//            if (path.indexOf(".jpg") >= 0 ||
//                    path.indexOf(".zip") >= 0 ||
//                    path.indexOf(".png") >= 0 ||
//                    path.indexOf(".rar") >= 0 ||
//                    path.indexOf(".css") >= 0 ||
//                    path.indexOf(".js") >= 0 ||
//                    path.indexOf(".woff") >= 0 ||
//                    path.indexOf(".woff2") >= 0 ||
//                    path.indexOf(".ttf") >= 0 ||
//                    path.indexOf(".svg") >= 0 ||
//                    path.indexOf(".eot") >= 0 ||
//                    path.indexOf(".otf") >= 0 ||
//                    path.indexOf(".ico") >= 0 ||
//                    path.indexOf(".gif") >= 0 ||
//                    path.indexOf(".xls") >= 0 ||
//                    path.indexOf(".xlsx") >= 0 ||
//                    path.indexOf(".properties") >= 0
//                    ) {
//                return true;
//            } else {
//                return false;
//            }
//        }
}
