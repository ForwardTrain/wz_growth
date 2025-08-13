package com.school.wz_growth.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.school.wz_growth.common.constant.WebConstant;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.ResponseVoResultCode;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HandleExceptionUtil {

    private static Logger logger = Logger.getLogger(HandleExceptionUtil.class );

    /**
     * 处理异常
     */
    public static void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        try{
            PrintWriter pw = response.getWriter();
            response.setContentType("application/json;charset=UTF-8");
            Map<String,Object> result=new HashMap<String, Object>();
            try {
                if (ex instanceof ServiceException) {
                    //自定义异常
                    ServiceException serviceEx = (ServiceException)ex;
                    result.put("code", serviceEx.getCode());
                    //自定义异常传递的错误信息
                    result.put("message", serviceEx.getMessage());

                    StringBuffer warn = new StringBuffer();
                    warn.append("user:").append(request.getSession().getAttribute(WebConstant.SESSION_USER) == null ? "null" :"").
                            append(",path:").append(request.getPathInfo()).append(",params:").append(request.getParameterMap().toString()).
                            append(",exceptionMessage:").append(ex.getMessage());
                    logger.warn(warn.toString());
                } else {
                    //运行异常
                    result.put("code", ResponseVoResultCode.CODE_RUNTIME_ERROR);
                    result.put("message", "服务器忙(" + ex.getClass().getName() +"), 请稍后重试");
                    StringBuffer error = new StringBuffer();
                    error.append("user:").append(request.getSession().getAttribute(WebConstant.SESSION_USER) == null ? "null" : "").
                            append(",path:").append(request.getPathInfo()).append(",params:").append(request.getParameterMap().toString()).
                            append(",exception:").append(ex.getClass().getName());
                    logger.error(error.toString());
                }

                pw.write(JSON.toJSONString(result));
                pw.flush();
            }catch (Exception e) {
                logger.error("写到客户端时候，获取writer的时候IOException了~悲剧了~", e);
            } finally {
                IOUtils.closeQuietly(pw);
            }
        }catch (IOException e){
            logger.error("写到客户端时候，获取writer的时候IOException了~悲剧了~", e);
        }
    }




}
