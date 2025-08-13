package com.school.wz_growth.dao;



import java.util.List;
import java.util.Map;

public interface SysBasicMapper {

    /** 公司 - 信息 */
    Map<String,Object>  selSysBasic() ;
    /** 公司 - 新增 */
     void insertSysBasic(Map<String, Object> params) ;
    /** 公司 - 更新 */
    void updateSysBasic(Map<String, Object> params) ;


    List<Map<String,Object>> sel_sys_params_list();
    void update_sys_params(Map<String, Object> params);

}
