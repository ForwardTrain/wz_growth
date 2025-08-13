package com.school.wz_growth.web.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.sys.MenuRegistorAddRequestVo;
import com.school.wz_growth.service.ContentService;
import com.school.wz_growth.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by jlm on 2025-03-12 23:59
 */
@RequestMapping("/protein")
@RestController
public class SysProteinTypeController {

    @Autowired
    private ContentService contentService;

    /**
     * 菜单 - 新增
     */
    @RequestMapping("/update")
    public SingleResult<String> updateDesc() throws ServiceException {
        SingleResult<DataResponse> dataResponseSingleResult = contentService.selectProteinType();
        List<JSONObject> list = (List<JSONObject>) dataResponseSingleResult.getData();
        for (JSONObject jsonObject : list) {
            String interPro_accession = jsonObject.getString("interPro_accession");
            if (StringUtils.isNotEmpty(interPro_accession)) {
                try {
                    String url = "https://www.ebi.ac.uk/interpro/wwwapi/entry/InterPro/" + interPro_accession;
                    String s = HttpUtil.get(url);
                    JSONObject jsonObject1 = JSON.parseObject(s);
                    JSONArray jsonArray = jsonObject1.getJSONObject("metadata").getJSONArray("description");
                    // 遍历 JSONArray 并拼接 text 字段
                    StringBuilder result = new StringBuilder();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String text = item.getString("text");
                        if (text != null) {
                            result.append(text).append("\n"); // 添加空格作为分隔符
                        }
                    }


                    Long id = jsonObject.getLong("id");
                    
                    // 调用service更新描述
                    contentService.updateProteinTypeDesc(id, result.toString());

                    Thread.sleep(1000);
                } catch (Exception e) {
                    // 记录错误但继续处理其他记录
                    System.err.println("处理 InterPro ID: " + interPro_accession + " 时发生错误: " + e.getMessage());
                }
            }
        }
        return SingleResult.buildSuccess("更新完成");
    }

}
