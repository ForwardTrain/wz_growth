package com.school.wz_growth.common.filter;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class PathCheckMsgUtil {

    private static Logger logger = Logger.getLogger( PathCheckMsgUtil.class );

    public static void writeMsgToPage(HttpServletRequest request, HttpServletResponse response, int code, String message, String logInfo) {
        logger.info(logInfo);

        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> result=new HashMap<String, Object>();
//        // CORS "pre-flight" request
//        response.addHeader("Access-Control-Allow-Origin", "http://192.168.7.22:8089");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
//        response.addHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token, content-type,Content-Type");
//        response.addHeader("Access-Control-Max-Age", "1800");//30 min
//        response.setStatus(HttpStatus.OK.value());

        try(PrintWriter pw = response.getWriter();) {
            result.put("code", code);
            result.put("message", message);

            String callback =JSON.toJSONString(result);
            pw.write(callback);
            pw.flush();
        } catch (IOException e) {
            logger.error("写到客户端时候，获取writer的时候IOException了~悲剧了~", e);
        }
    }
}
