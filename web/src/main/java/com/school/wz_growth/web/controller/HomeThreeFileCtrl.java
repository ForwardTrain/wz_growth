package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.service.HomeThreeFileService;
import com.school.wz_growth.web.BaseController;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  三方平台
 *
 */
@RequestMapping("/HomeThreeFile")
@RestController
public class HomeThreeFileCtrl extends BaseController {

    @Autowired
    private HomeThreeFileService service;

    /**
     1.GeenMedical
     GeenMedical（根哥学术）是一个专注于医学和生命科学领域的在线平台，旨在为研究人员、医生、学生以及其他医疗专业人员提供丰富的学术资源和工具。该平台提供了多种功能，包括文献检索、学术交流、会议信息、科研工具等，帮助用户获取最新的研究成果和临床指南。
     https://www.geenmedical.com/search?query=%7B%22keyword%22:%22%5C%22Medicine%20(Baltimore)%5C%22%5Bjour%5D%22%7D
      */
    @RequestMapping("/sel_GM_list")
    @ResponseBody
    public SingleResult sel_GM_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParamsPageSize(requestVo);
        return service.sel_GM_list( requestVo);
    }

    /**
     2.ClinicalTrials.gov 临床研究
     平台简介： ClinicalTrials.gov 是由美国国立卫生研究院（NIH）下属的国家医学图书馆（NLM）维护的一个公共数据库，旨在提供全球范围内正在进行和已完成的临床试验的详细信息。该平台是全球最大的临床试验注册库之一，涵盖了广泛的疾病领域和干预措施，帮助研究人员、医疗专业人员、患者及其家属了解最新的临床研究进展。
     https://clinicaltrials.gov/search?term=heart%20attack
     */
    @RequestMapping("/sel_CT_list")
    @ResponseBody
    public SingleResult sel_CT_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParamsPageSize(requestVo);
        return service.sel_CT_list( requestVo);
    }

    @RequestMapping("/sel_CT_list_export")
    @ResponseBody
    public void sel_CT_list_export(@RequestBody JSONObject requestVo, HttpServletResponse response) throws ServiceException, IOException {
        // 获取临床研究数据
        SingleResult<DataResponse> result = service.sel_CT_list_export(requestVo);

        // 创建Excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("临床研究数据");

        // 创建标题行样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // 创建标题行
        HSSFRow headerRow = sheet.createRow(0);
        String[] headers = {"Number", "StudyTitle", "Conditions", "Interventions", "Status", "Phases", "Sponsor", "Locations"};
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            // 设置列宽
            sheet.setColumnWidth(i, 6000);
        }

        // 填充数据
        if (result != null && result.getCode() == 1 && result.getData() != null) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.getData();

            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> item = dataList.get(i);
                HSSFRow row = sheet.createRow(i + 1);

                // 按照要求的字段对应关系填充数据
                row.createCell(0).setCellValue(getStringValue(item, "nct_number"));
                row.createCell(1).setCellValue(getStringValue(item, "briefTitle"));
                row.createCell(2).setCellValue(getStringValue(item, "conditions"));
                row.createCell(3).setCellValue(getStringValue(item, "Interventions"));
                row.createCell(4).setCellValue(getStringValue(item, "overallStatus"));
                row.createCell(5).setCellValue(getStringValue(item, "phases"));
                row.createCell(6).setCellValue(getStringValue(item, "leadSponsor"));
                row.createCell(7).setCellValue(getStringValue(item, "locations"));
            }
        }

        // 设置响应头 - 修复中文文件名编码问题
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String nowDate = sdf.format(new Date());

        // 使用英文文件名避免编码问题
        String fileName = "Clinical_Research_Data_" + nowDate + ".xls";

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

    /**
     * 3.The Lens：
     *  是一个免费且开放的专利和学术文献搜索平台，旨在为用户提供强大的工具来探索、分析和理解全球范围内的专利和技术文献。它不仅是一个简单的搜索引擎，还提供了一系列高级功能，帮助用户进行深入的研究和数据分析。
     * https://www.lens.org/lens/search/patent/list?q=growth%20factor&p=4&n=10&s=_score&d=%2B&f=false&e=false&l=en&authorField=author&dateFilterField=publishedDate&orderBy=%2B_score&presentation=false&preview=true&stemmed=true&useAuthorId=false&patentStatus.must=active&patentStatus.must=pending&hasSequence=false
     * */
    @RequestMapping("/sel_TL_list")
    @ResponseBody
    public SingleResult sel_TL_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParamsPageSize(requestVo);
        return service.sel_TL_list( requestVo);
    }

    @RequestMapping("/sel_TL_list_export")
    @ResponseBody
    public void sel_TL_list_export(@RequestBody JSONObject requestVo, HttpServletResponse response) throws ServiceException, IOException {
        service.sel_TL_list_export(requestVo, response);
    }

    @RequestMapping("/sel_TL_list_search")
    @ResponseBody
    public SingleResult sel_TL_list_search(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParamsPageSize(requestVo);
        return service.sel_TL_list_search( requestVo);
    }

    @RequestMapping("/sel_CT_list_detail")
    @ResponseBody
    public SingleResult sel_CT_list_detail(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParamsPageSize(requestVo);
        return service.sel_CT_list_detail( requestVo);
    }

    @RequestMapping("/dramp")
    @ResponseBody
    public SingleResult dramp(@RequestBody JSONObject requestVo) throws ServiceException, IOException {
        // 基础URL
        String baseUrl = "http://dramp.cpu-bioinfor.org/cgi-bin/seq-search/simi-search.cgi";

        // 原始参数（注意保持原始换行符）
        String passKey = "0eb7kPDzDTnEbWsW0HmmPx2Midaih0FrdAHTo6SE4+kt6/50xZ6xzLKaGQ";
//        String simiArea = ">sp|A0JNW5|BLT3B_HUMAN Bridge-like lipid transfer protein family member 3B OS=Homo sapiens OX=9606 GN=BLTP3B PE=1 SV=2\r\n" +
//                "MAGIIKKQILKHLSRFTKNLSPDKINLSTLKGEGELKNLELDEEVLQNMLDLPTWLAINK\r\n" +
//                "AVFCNKASIRIPWTKLKTHPICLSLDKVIMEMSTCEEPRSPNGPSPIATASGQSEYGFAEK";
        String simiArea = requestVo.get("simiArea").toString();
        String matrix = "-s BL62";
        String eUp = "20.0";
        String ali = "10";
        String searchName = "ssearch35";

        // 构建查询参数（使用显式编码）
        String query = String.format(
                "pass_key=%s&simi_area=%s&matrix=%s&E-up=%s&ali=%s&search_name=%s",
                urlEncode(passKey),
                urlEncode(simiArea),
                urlEncode(matrix),
                urlEncode(eUp),
                urlEncode(ali),
                urlEncode(searchName)
        );

        // 创建完整URL
        URL url = new URL(baseUrl + "?" + query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // 处理响应
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return SingleResult.buildSuccess(readResponse(conn));
        } else {
            throw new IOException("Request failed with code: " + responseCode);
        }
    }

    // URL编码工具方法（保留原始换行符）
    private String urlEncode(String value) throws IOException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.displayName())
                .replace("%0A", "%0D%0A")  // 转换换行符编码
                .replace("+", "%20");       // 处理空格编码
    }

    // 读取响应内容
    private String readResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            return response.toString().trim();
        }
    }

}
