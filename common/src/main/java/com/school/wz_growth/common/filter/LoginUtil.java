package com.school.wz_growth.common.filter;


import com.school.wz_growth.common.constant.WebConstant;
import com.school.wz_growth.common.response.ResponseVoResultCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class LoginUtil {

    //判断是否登录
    public static boolean checkLoginForUserOrRole(HttpServletRequest request, HttpServletResponse response) {
        boolean tag = false;
        try {
            /**
             * 判断用户是否为登录状态
             */
            HttpSession httpSession = request.getSession();
            if (null != httpSession) {
                Object object = request.getSession().getAttribute(WebConstant.SESSION_USER);
                if (object != null) {
                    tag = true;
                } else {
                    //当前登录出现问题
                    /*StringBuffer info = new StringBuffer();

                    info.append("URL访问异常,详细信息如下：");
                    info.append(",Path = [").append(request.getPathInfo()).append("], exception message:[").append("当前登录出现问题, 登录对象为空]");

                    PathCheckMsgUtil.writeMsgToPage(request, response, ResponseVoResultCode.CODE_AUTHORITY, "当前登录会话已过期, 请退出后重新登录", info.toString());
*/
                    return false;
                }
            } else {
                //会话过期
                /*StringBuffer info = new StringBuffer();

                info.append("URL访问异常,详细信息如下：");
                info.append(",Path = [").append(request.getPathInfo()).append("], exception message:[").append("当前会话已过期]");

                PathCheckMsgUtil.writeMsgToPage(request, response, ResponseVoResultCode.CODE_AUTHORITY, "当前登录会话已过期, 请退出后重新登录", info.toString());
*/
                return false;
            }
            /**
             * 判断用户是否包含此权限 根据路径来判断
             *
             */
            if (tag) {
                Map<String, Object> menuMap = (Map<String, Object>) request.getSession().getAttribute(WebConstant.MENU_SESSION_USER);
                String path = request.getPathInfo();
                if (null != menuMap) {
                    if (menuMap.containsKey(path)) {
                        tag = true;
                    } else {
                        //用户无权限
                        StringBuffer info = new StringBuffer();

                        info.append("URL访问异常,详细信息如下：");
                        info.append(",Path = [").append(request.getPathInfo()).append("], exception message:[").append("用户无此路径权限]");

                        PathCheckMsgUtil.writeMsgToPage(request, response, ResponseVoResultCode.CODE_AUTHORITY, "您不具有该权限, 请联系管理员", info.toString());

                        return false;
                    }
                }
            }

            /**
             * 角色类型验证,验证该类型的角色是否有该路径权限
             */
            /*if (tag){
                SysAdmin loginUser = (SysAdmin) request.getSession().getAttribute(WebConstant.SESSION_USER);
                Integer roleType = loginUser.getRoleType();

                if (roleType == null){
                    //角色无类型,
                    StringBuffer info = new StringBuffer();

                    info.append("URL访问异常,详细信息如下：");
                    info.append(",Path = [").append(request.getPathInfo()).append("], exception message:[").append("当前角色无类型]");

                    PathCheckMsgUtil.writeMsgToPage(request, response, ResponseVoResultCode.CODE_AUTHORITY, "当前角色无类型, 请联系管理员处理", info.toString());

                    return false;
                }else if (roleType == 1){
                    //系统类型
                    if (WebConstant.SYS_NO_PATH.contains(request.getPathInfo())){
                        //系统类型不可进行这些操作
                        StringBuffer info = new StringBuffer();

                        info.append("URL访问异常,详细信息如下：");
                        info.append(",Path = [").append(request.getPathInfo()).append("], exception message:[").append("系统类型无法进行该操作]");

                        PathCheckMsgUtil.writeMsgToPage(request, response, ResponseVoResultCode.CODE_AUTHORITY, "您的角色为系统角色,无法进行该操作, 请联系管理员处理", info.toString());
                        return false;
                    }
                }else if (roleType == 2){
                    //业务类型
                    if (WebConstant.BUSINESS_NO_PATH.contains(request.getPathInfo())){
                        //业务类型不能进行这些操作
                        StringBuffer info = new StringBuffer();

                        info.append("URL访问异常,详细信息如下：");
                        info.append(",Path = [").append(request.getPathInfo()).append("], exception message:[").append("业务类型无法进行该操作]");

                        PathCheckMsgUtil.writeMsgToPage(request, response, ResponseVoResultCode.CODE_AUTHORITY, "您的角色为业务角色,无法进行该操作, 请联系管理员处理", info.toString());
                        return false;
                    }
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }
}
