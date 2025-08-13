package com.school.wz_growth.dao;



import java.util.List;
import java.util.Map;

public interface SysOrgPersonMapper {

    /** 组织人员 - 详情查询  */
    Map<String,Object>  selOrgPersonByTelOrId(Map<String, Object> params);
    /** 人员岗位 - 列表 */
    List<Map<String,Object>>  selOrgPersonJobList(Map<String, Object> params);
    /** 组织人员 - 列表 */
    List<Map<String,Object>>  queryOrgPersonList(Map<String, Object> params);
    Integer  queryOrgPersonListCount(Map<String, Object> params);


    /** 组织人员 - 添加 */
    void  addOrgPerson(Map<String, Object> params);


    /** 组织人员 - 编辑 */
    void  updateOrgPerson(Map<String, Object> params);
    void  updateOrgPersonPsd(Map<String, Object> params);
    void  updateOrgPersonState(Map<String, Object> params);

    /** 组织人员 - 删除 -根据人员id */
    void  delOrgPerson(Map<String, Object> params);
    /** 组织人员 - 删除 -根据人员ids */
    void  delOrgPersons(Map<String, Object> params);
    /** 组织人员 - 删除 -根据部门id */
    void  delOrgPersonByDeptId(Map<String, Object> params);






    /** 审批 - 流程 */
    List<Map<String,Object>>  queryJobPersonList(Map<String, Object> params);
}
