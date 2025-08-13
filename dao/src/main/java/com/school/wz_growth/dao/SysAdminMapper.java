package com.school.wz_growth.dao;




import java.util.List;
import java.util.Map;

public interface SysAdminMapper {

    /** 管理员 信息查询 */
    Map<String,Object> selAdminByUserName(Map<String, Object> params);

    /** 管理员 更新密码 */
    void updateAdminById(Map<String, Object> params);


    /** 工作人员账号 */
    List<Map<String,Object>> select_sys_account_list(Map<String, Object> params);
    int select_sys_account_list_count(Map<String, Object> params);
    Map<String,Object> select_account_info(Map<String, Object> params);
    void update_account_info(Map<String, Object> params);
    void insert_account_info(Map<String, Object> params);
    void update_account_info_status(Map<String, Object> params);
    void delete_account_info(Map<String, Object> params);

}
