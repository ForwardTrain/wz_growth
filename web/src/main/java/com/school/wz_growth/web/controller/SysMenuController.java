package com.school.wz_growth.web.controller;



import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.dao.SysMenuMapper;
import com.school.wz_growth.model.front.request.sys.*;
import com.school.wz_growth.service.SysMenuService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 菜单 */
@RequestMapping("/SysMenu")
@RestController
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService menuService;

    @Resource
    private SysMenuMapper menuMapper;

    /**
     * 菜单 - 新增
     */
    @RequestMapping("/add")
    public SingleResult<DataResponse> add(@Valid @RequestBody MenuRegistorAddRequestVo requestVo, BindingResult result)  throws ServiceException{
        //必须要调用validate方法才能实现输入参数的合法性校验
        super.validatorParam(requestVo);
        return menuService.doAdd(requestVo);
    }

    /**
     * 菜单 - 编辑
     */
    @RequestMapping("/update")
    public SingleResult<DataResponse> update(@Valid @RequestBody MenuRegistorUpdateRequestVo requestVo, BindingResult result)  throws ServiceException{
        //必须要调用validate方法才能实现输入参数的合法性校验
        super.validatorParam(requestVo);
        return menuService.doUpdate(requestVo);
    }

    /**
     * 菜单 - 删除
     */
    @RequestMapping("/delete")
    public SingleResult<DataResponse> delete(@Valid @RequestBody MenuRegistorDeleteRequestVo requestVo, BindingResult result)  throws ServiceException{
        //必须要调用validate方法才能实现输入参数的合法性校验
        super.validatorParam(requestVo);
        return menuService.doDelete(requestVo);
    }



    /**
     * 菜单 - 所有列表
     */
    @RequestMapping("/all/menu")
    public SingleResult<DataResponse> allmenu(@Valid @RequestBody MenuAllRequestVo requestVo, BindingResult result)  throws ServiceException{
        //必须要调用validate方法才能实现输入参数的合法性校验
        super.validatorParam(requestVo);
        return menuService.queryAllMenuByPage(requestVo);
    }



    /**
     * 用户登录之后获取的权限菜单
     * @param requestVo
     * @param result
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/user/list")
    public SingleResult<DataResponse> userList(@Valid @RequestBody MenuUserListRequestVo requestVo, BindingResult result)  throws ServiceException{
        super.validatorParam(requestVo);
        super.putAppUserTokenParams(requestVo);
        return menuService.queryUserMenuByPage(requestVo);
    }

    @RequestMapping("/xxxx")
    public void xxxx() throws ServiceException {
        int sId = 1;
        int batchSize = 10000;
        while (true) {
            int eId = sId + batchSize - 1;
            HashMap<String, Object> params = new HashMap<>();
            params.put("sId", sId);
            params.put("eId", eId);

            // 查询当前区间的数据
            List<Map<String, Object>> records = menuMapper.xxx(params);
//             List<Map<String, Object>> records = menuMapper.xxx2_test();

            // 如果没有数据，退出循环
            if (records == null || records.isEmpty()) {
                break;
            }

            // 对每条记录导出为 txt 文件
            for (Map<String, Object> record : records)
            {
                String pmid = record.get("PMID").toString();
                String path = "/Users/xxm/Desktop/pubmed" + pmid + ".txt";
                exportDataToTxt(record, path);
            }

            // 更新起始id，进行下一批
            sId = eId + 1;
        }

    }

    public static void exportDataToTxt(Map<String, Object> record, String outputFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            // 获取各字段值（注意判空）
            String articleTitle = record.get("article_title") != null ? record.get("article_title").toString() : "";
            String pmid = record.get("PMID") != null ? record.get("PMID").toString() : "";
            String url = record.get("url") != null ? record.get("url").toString() : "";
            String journal = record.get("journal") != null ? record.get("journal").toString() : "";
            String allAuthors = record.get("all_authors") != null ? record.get("all_authors").toString() : "";
            String allBriefs = record.get("all_briefs") != null ? record.get("all_briefs").toString() : "";

            String keywordsArray = record.get("keywords") != null ? record.get("keywords").toString() : "";
            String keywords = keywordsArray.replace("[", "").replace("]", "");

            // 将换行符转换为系统默认换行符，确保在文本文件中正常换行
            allBriefs = allBriefs.replace("\n", System.lineSeparator());

            // 写入文件，按要求格式化输出
            writer.write(articleTitle);
            writer.newLine();
            writer.newLine();
            writer.write("PMID:" + pmid + "    URL:" + url);
            writer.newLine();
            writer.newLine();
            writer.write("期刊年卷:" + journal);
            writer.newLine();
            writer.write("关键词:" + keywords);
            writer.newLine();
            writer.write("作者列表:" + allAuthors);
            writer.newLine();
            writer.newLine();
            writer.write(allBriefs);
            writer.newLine();
//            writer.newLine();
//            // 每条记录间用分隔符（可选）
//            writer.write("------------------------------------------------------");
//            writer.newLine();
            System.out.println("数据已成功导出到文件：" + outputFilePath);
        } catch (IOException e) {
            System.err.println("写入文件时发生错误：" + e.getMessage());
            e.printStackTrace();
        }
    }

}
