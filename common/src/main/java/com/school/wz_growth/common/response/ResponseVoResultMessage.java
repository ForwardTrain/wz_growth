package com.school.wz_growth.common.response;


import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ResponseVoResultMessage {

    private final static String defaultErrorMsg = wrapDefaulTips("系统未知异常");

    private final static String defaultTips = ",请刷新页面重试或者联系客服";

    private static Map<Integer, String> errorMsgMap = new HashMap<Integer, String>();

    static {
        errorMsgMap.put( ResponseVoResultCode.CODE_PARAM_ERROR, wrapDefaulTips("参数传入异常") );
        errorMsgMap.put( ResponseVoResultCode.CODE_RUNTIME_ERROR, wrapDefaulTips("系统异常") );
        errorMsgMap.put( ResponseVoResultCode.CODE_INTERNAL_ERROR, wrapDefaulTips("系统异常") );
        errorMsgMap.put( ResponseVoResultCode.CODE_INTERNAL_RPC_ERROR, wrapDefaulTips("系统服务异常") );
        errorMsgMap.put( ResponseVoResultCode.CODE_AUTHORITY, wrapDefaulTips("版本受限") );
        errorMsgMap.put( ResponseVoResultCode.CODE_JVM_DOWN, wrapDefaulTips("系统升级维护中") );
        //系统业务相关(返回给前端)
        errorMsgMap.put( ResponseVoResultCode.CODE_NO_USER_ERROR, "用户会话失效,请重新登录" );
    }

    public static String getErrorMsg(int errorCode ) {
        String msg = errorMsgMap.get( errorCode );
        if(StringUtils.isNotBlank(msg)) {
            return msg;
        }
        return defaultErrorMsg;
    }

    private static String wrapDefaulTips(String msg) {

        return msg + defaultTips;
    }

}
