package com.school.wz_growth.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.HomeThreeFileMapper;
import com.school.wz_growth.service.HomeThreeFileService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HomeThreeFileServiceImpl implements HomeThreeFileService {

    @Autowired
    private HomeThreeFileMapper homeThreeFileMapper;


    @Override
    public SingleResult<DataResponse> sel_GM_list(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();

        params.put("title",requestVo.get("title"));
        params.put("page_index",requestVo.get("start"));
        params.put("page_size",requestVo.get("limit"));
        try {
            List<Map<String, Object>> res_list = homeThreeFileMapper.sel_GM_list(params);
            res_list.forEach(sub_obj_map -> {

                sub_obj_map.put("brief",homeThreeFileMapper.sel_GM_brief_list(sub_obj_map));
//                if (sub_obj_map.get("brief") != null && sub_obj_map.get("brief").toString().length() > 0) {
//                    sub_obj_map.put("brief", JSONArray.parseArray(sub_obj_map.get("brief").toString()));
//                } else {
//                    sub_obj_map.put("brief", new ArrayList<>());
//                }
            });
            res.put("list",res_list);
            res.put("total",homeThreeFileMapper.sel_GM_list_count(params));
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_CT_list(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();

        params.put("title",requestVo.get("title"));
//        params.put("title",requestVo.get("title"));
        params.put("page_index",requestVo.get("start"));
        params.put("page_size",requestVo.get("limit"));
        params.put("overallStatus",requestVo.get("overallStatus"));
        params.put("phases",requestVo.get("phases"));
        params.put("sortOrder", requestVo.get("sortOrder"));

        try {

            List<Map<String, Object>> res_list = homeThreeFileMapper.sel_CT_list(params);
            if (StringUnits.isNotNullOrEmpty(requestVo.get("title")))
            {
                String key_words = requestVo.getString("title");
//                String replase_str = String.format("<span style=\"background-color: yellow;\">%s</span>",key_words);
//                    jsonObject.put("briefTitle", (StringUnits.isNotNullOrEmpty(jsonObject.get("briefTitle"))?(jsonObject.get("briefTitle").toString().replaceAll(key_words,replase_str)):""));

                for (Map<String,Object> jsonObject : res_list)
                {
                    if (StringUnits.isNotNullOrEmpty(key_words) && StringUnits.isNotNullOrEmpty(jsonObject.get("briefTitle")))
                        jsonObject.put("briefTitle", StringUnits.convertHighLight(key_words, jsonObject.get("briefTitle").toString()));

                    if (StringUnits.isNotNullOrEmpty(key_words) && StringUnits.isNotNullOrEmpty(jsonObject.get("phases")))
                        jsonObject.put("phases", StringUnits.convertHighLight(key_words, jsonObject.get("phases").toString()));

                    if (StringUnits.isNotNullOrEmpty(key_words) && StringUnits.isNotNullOrEmpty(jsonObject.get("overallStatus")))
                        jsonObject.put("overallStatus", StringUnits.convertHighLight(key_words, jsonObject.get("overallStatus").toString()));
                }
            }

            res_list.stream().forEach(s -> {
                String o = s.get("phases") != null ? s.get("phases").toString() : "";
                if (!"NA".equals(o)) {
                    String phases = StringUnits.capitalizeWords(o);
                    s.put("phases", phases);
                }
                String s1 = s.get("overallStatus") != null ? s.get("overallStatus").toString() : "";
                String overallStatus = StringUnits.capitalizeWords(s1);
                s.put("overallStatus", overallStatus);
            });

            res.put("list",res_list);
            res.put("total",homeThreeFileMapper.sel_CT_list_count(params));
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_CT_list_export(JSONObject requestVo) throws ServiceException {
        List<Map<String, Object>> res_list = homeThreeFileMapper.sel_CT_list_export(requestVo);
        return SingleResult.buildSuccess(res_list);
    }

    @Override
    public SingleResult<DataResponse> sel_TL_list_search(JSONObject requestVo) throws ServiceException {
//        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();

        try {
            res.put("disease_types",homeThreeFileMapper.sel_CT_disease_types());
            res.put("other_disease",homeThreeFileMapper.sel_CT_other_disease());
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_CT_list_detail(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();

        params.put("id",requestVo.get("id"));
        try {
            res = homeThreeFileMapper.sel_CT_list_detail(params);

            if (!ObjectUtils.isEmpty(res)&&!ObjectUtils.isEmpty(res.get("conditions")))
                res.put("conditions",JSONArray.parseArray(res.get("conditions").toString()));

            if (!ObjectUtils.isEmpty(res)&&!ObjectUtils.isEmpty(res.get("intervention_treatment")))
                res.put("intervention_treatment",JSONArray.parseArray(res.get("intervention_treatment").toString()));

            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_TL_list(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();

        params.put("title",requestVo.get("title"));
        params.put("page_index",requestVo.get("start"));
        params.put("page_size",requestVo.get("limit"));
        params.put("sortOrder", requestVo.get("sortOrder"));
        try {

            List<Map<String, Object>> res_list = homeThreeFileMapper.sel_TL_list(params);
            if (StringUnits.isNotNullOrEmpty(requestVo.get("title")))
            {
                String key_words = requestVo.getString("title");
//                String replase_str = String.format("<span style=\"background-color: yellow;\">%s</span>",key_words);
                for (Map<String,Object> jsonObject : res_list)
                {
                    if (StringUnits.isNotNullOrEmpty(key_words) && StringUnits.isNotNullOrEmpty(jsonObject.get("title")))
                        jsonObject.put("title", StringUnits.convertHighLight(key_words, jsonObject.get("title").toString()));

                }
//                    jsonObject.put("title", (StringUnits.isNotNullOrEmpty(jsonObject.get("title"))?(jsonObject.get("title").toString().replaceAll(key_words,replase_str)):""));
            }

            res.put("list",res_list);
            res.put("total",homeThreeFileMapper.sel_TL_list_count(params));
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }

    @Override
    public void sel_TL_list_export(@RequestBody JSONObject requestVo, HttpServletResponse response) throws IOException {
        // 获取专利数据
        List<Map<String, Object>> dataList = homeThreeFileMapper.sel_TL_list_export(requestVo);

        // 创建Excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("专利数据");

        // 创建标题行样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // 创建标题行
        HSSFRow headerRow = sheet.createRow(0);
        String[] headers = {"Title", "Identifiers", "LegalStatus", "ApplicationNo", "Filed",
                "Published", "EarliestPriority", "Inventors", "LensID", "Abstract"};
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            // 设置列宽
            sheet.setColumnWidth(i, 6000);
        }

        // 填充数据
        if (dataList != null && !dataList.isEmpty()) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> item = dataList.get(i);
                HSSFRow row = sheet.createRow(i + 1);

                // 按照要求的字段对应关系填充数据
                row.createCell(0).setCellValue(getStringValue(item, "title"));
                row.createCell(1).setCellValue(getStringValue(item, "identifers"));
                row.createCell(2).setCellValue(getStringValue(item, "LegalStatus"));
                row.createCell(3).setCellValue(getStringValue(item, "ApplicationNumber"));
                row.createCell(4).setCellValue(getStringValue(item, "filed"));
                row.createCell(5).setCellValue(getStringValue(item, "date_published"));
                row.createCell(6).setCellValue(getStringValue(item, "EarliestPriorityDate"));
                row.createCell(7).setCellValue(getStringValue(item, "inventor"));
                row.createCell(8).setCellValue(getStringValue(item, "LensID"));
                row.createCell(9).setCellValue(getStringValue(item, "abstract"));
            }
        }

        // 设置响应头
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String nowDate = sdf.format(new Date());
        String fileName = "Patent_Data_" + nowDate + ".xls";

        // 设置正确的Content-Disposition头
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        // 输出Excel文件
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }

    // 辅助方法：从Map中安全获取字符串值
    private String getStringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : "";
    }

}
