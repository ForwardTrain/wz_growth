package com.school.wz_growth.common.response;

public class ResponseVoResultCode {
    /**
     * 2xx  表示成功
     * 3xx  表示参数异常  (调用后台数据时参数验证失败等)
     * 4xx  程序运行时异常 (没有捕获的异常，如空指针异常等)
     * 5xx  程序内部错误  (预知已经捕获的异常，如获取缓存异常)
     * 6xx  内部服务调用失败    (调用第三方服务出错)
     * 7xx  权限错误    (会话失效、无权限)
     * 8xx  程序挂了
     * 9xx  安全验证失败
     */
    public final static int CODE_SUCCESS = 200;
    public final static int CODE_PARAM_ERROR = 300;
    public final static int CODE_RUNTIME_ERROR = 400;
    public final static int IS_NOT_FRIEND = 404; //不是好友关系  //无此用户 //没有包含token //当前数据为空 //设备不能踢出
    public final static int IS_HAVE_ERROR = 403; //只能群主操作
    public final static int CODE_PAY = 411; //表示没有需要交费
    public final static int ERROR_STATUS = 2;
    public final static int HAVE_USER = 405; //当前用户已存在

    public final static int CODE_INTERNAL_ERROR = 500;
    public final static int CODE_INTERNAL_RPC_ERROR = 600;
    public final static int CODE_AUTHORITY = 700;
    public final static int CODE_JVM_DOWN = 800;
    public final static int CODE_SECURITY_ERROR = 900;

    //业务
    public final static int CODE_NO_USER_ERROR = 401;   //session 获取用户失败
    public final static int CODE_DATA_BASE_ERROR = 402;   //数据库异常
    public final static int CODE_USER_UNIQUE_ERROR = 402;   //用户名唯一

    public final static int  PHONE_OR_PASSWORD_ERROR= 1000;  //用户名或密码错误  验证码错误
    public final static int  TOKEN_IS_NOT_HAVE= 404;
    public final static int  NOT_REQUEST_TYPE= 403;  //403: PRIVATE 与对方不是好友  // 403: GROUP 发送消息用户不在群组中 // 403: 未知会话类型，默认支持 PRIVATE、GROUP

    public final static int  COOKIE_TIME_OUT= 901;  //COOKIE失效  也就是表明未登录
    public final static int  SMS_COUNT_OUT= 5000;  //短信发送频率被限制

    public final static int RETURN_CODE_SUCCESS = 1;  //返回成功
    public final static int RETURN_CODE_ERROR = -1;  //返回错误
    public final static int RETURN_CODE_NULL = 0;  //返回数据为空

    public final static int CODE_TRUE= 1;  //符合规定的逻辑
    public final static int CODE_FALSE= 0;  //不符合规定的逻辑


    public final static int PARAMS_IS_ERROR = 101;  //参数错误


}
