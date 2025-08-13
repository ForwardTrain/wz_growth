package com.school.wz_growth.common.filter;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RenderUtil {
    public static void renderJson(HttpServletResponse response, Object jsonObject, HttpServletRequest request) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            //这里设置跨域
            response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,OPTIONS");
            response.setHeader("Access-Control-Allow-Headers","*");
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Access-Control-Max-Age","90001");

            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
