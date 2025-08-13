package com.school.wz_growth.common.forPidUtil;


import com.school.wz_growth.common.sysEnum.StateCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuUtil {
    public static List recursionMenu(List<Map<String,Object>> list,Map roleMenu){
        List treeList = new ArrayList();
        // 循环查出的list，找到根节点（最大的父节点）的子节点
        for(Map<String,Object> map : list){
            // 我们这里最大的根节点ID是-1，所以首先找pid为-1的子，然后调用我们的递归算法
            if(null != roleMenu){
                if(roleMenu.get("menu_id").toString().contains(","+map.get("id").toString()+",")){
                    map.put("is_check",StateCode.可用.value());
                }
            }
            if("0".equals(map.get("pId").toString()))
            {
                List sub_temp_list = addChildMenu(map,list,roleMenu);
                if (sub_temp_list!=null && sub_temp_list.size()>0)
                    map.put("children",sub_temp_list);

                treeList.add(map);
            }
        }
        return treeList;
    }

    public static List addChildMenu(Map<String,Object> parentMap,List<Map<String,Object>> list,Map roleMenu){
        List<Map> childList = new ArrayList<>();
        // 为每一个父节点增加子树（List形式，没有则为空的list）
        if (childList!=null && childList.size()>0)
             parentMap.put("children",childList);


        for (Map<String,Object> childMap : list){
            //如果子节点的pid等于父节点的ID，则说明是父子关系
            if(childMap.get("pId").toString().equals(parentMap.get("id").toString())){
                // 是父子关系，则将其放入子list字面
                if(null != roleMenu){
                    if(roleMenu.get("menu_id").toString().contains(","+childMap.get("id").toString()+",")){
                        childMap.put("is_check",StateCode.可用.value());
                    }
                }
                childList.add(childMap);
                // 继续调用递归算法，将当前作为父节点，继续找他的子节点，反复执行。
                addChildMenu(childMap,list,roleMenu);
            }
        }

        // 当遍历完成，返回调用的父节点的所有子节点
        return childList;
    }


}
