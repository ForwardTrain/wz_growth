package com.school.wz_growth.web;


import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.ResponseVoResultCode;
import com.school.wz_growth.common.validator.IValidator;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.model.front.request.base.BaseRequestVo;
import com.school.wz_growth.web.interceptor.HandleExceptionUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseController {

    protected Logger logger = Logger.getLogger( this.getClass() );
    /**
     * Servlet APIs。
     */
    private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
    private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();


    public HttpServletRequest getRequest() {

        return this.request.get();
    }

    public HttpServletResponse getResponse() {

        return this.response.get();
    }

    protected <T extends BaseRequestVo> void putAppUserTokenParams(T requestVo){
        requestVo.setuId(Long.parseLong(String.valueOf(getRequest().getAttribute("uId"))));
        requestVo.setU_name(String.valueOf(getRequest().getAttribute("u_name")));
        requestVo.setRole_type(Integer.parseInt(String.valueOf(getRequest().getAttribute("role_type"))));
    }
    protected  void putAppUserTokenParamsPageSize(com.alibaba.fastjson.JSONObject requestVo){
        if(requestVo.get("pageIndex")==null||requestVo.getString("pageIndex").equals("")) {
            requestVo.put("limit", 20);
            requestVo.put("start", 0);
        }
        else {
            requestVo.put("limit", Integer.parseInt(String.format("%s",requestVo.get("pageSize"))));
            requestVo.put("start",
                    Integer.parseInt(String.format("%s",requestVo.getInteger("pageSize")*(requestVo.getInteger("pageIndex")-1))));
        }
    }
    protected  void putAppUserTokenParams(com.alibaba.fastjson.JSONObject requestVo){

        if (StringUnits.isNotNullOrEmpty(requestVo.get("pageSize")) == false)
            requestVo.put("pageSize","20");
        if(requestVo.get("pageIndex")==null||requestVo.getString("pageIndex").equals("")) {
            requestVo.put("limit", 20);
            requestVo.put("start", 0);
        }
        else {
            requestVo.put("limit", Integer.parseInt(String.format("%s",requestVo.get("pageSize"))));
            requestVo.put("start",
                    Integer.parseInt(String.format("%s",requestVo.getInteger("pageSize")*(requestVo.getInteger("pageIndex")-1))));
        }
        if(requestVo.get("uId")==null||requestVo.getString("uId").equals(""))
            requestVo.put("uId",Long.parseLong(String.valueOf(getRequest().getAttribute("uId"))));
        requestVo.put("u_name",String.valueOf(getRequest().getAttribute("u_name")));
        requestVo.put("role_type",Integer.parseInt(String.valueOf(getRequest().getAttribute("role_type"))));
    }
    /**
     * 参数验证器
     */
    protected void validatorParam( IValidator iValidator ) throws ServiceException {
        if(!iValidator.validator()) {
            throw new ServiceException("前端参数异常", ResponseVoResultCode.PARAMS_IS_ERROR);
        }
    }

    /**
     * 统一的拦截器
     * @param req
     * @param resp
     * @param ex
     */
    @ExceptionHandler({ Exception.class })
    public void exceptionHandler( HttpServletRequest req, HttpServletResponse resp, Exception ex) {
        try
        {
            ex.printStackTrace();
            System.out.println("exception："+ex.getMessage());
            HandleExceptionUtil.exceptionHandler(req, resp, ex);
        }
        catch (Exception e){

        }
    }

    @ModelAttribute
    private void setServletAPIs(HttpServletRequest request, HttpServletResponse response) {
        this.request.set(request);
        this.response.set(response);
    }

//    public void putDateIntoTeksunResponse(TeksunResponseVo responseVo, Map<String,Object> result) throws ServiceException {
//        int code = Integer.parseInt(String.valueOf(result.get("code")));
//        if(code == 1){
//            responseVo.setStatus(1);
//            responseVo.setBodys(result.get("data"));
//        }else{
//            throw new ServiceException("server error", CheckStatus.不可用.getValue());
//        }
//    }

}
