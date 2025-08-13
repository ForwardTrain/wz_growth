package com.school.wz_growth.web.controller;


import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.dao.model.DrugInfo;
import com.school.wz_growth.dao.model.DrugInfoQueryDTO;
import com.school.wz_growth.model.PageResult;
import com.school.wz_growth.service.DrugInfoService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 药品信息控制器
 */
@RestController
@RequestMapping("/drugInfo")
public class DrugInfoController {

    @Autowired
    private DrugInfoService drugInfoService;

    /**
     * 分页查询药品信息
     * 可根据药品中文名或英文名模糊搜索
     */
    @RequestMapping("/page")
    public SingleResult page(@RequestBody DrugInfoQueryDTO queryDTO) {
        PageResult<DrugInfo> pageResult = drugInfoService.page(queryDTO);
        return SingleResult.buildSuccess(pageResult);
    }

    @RequestMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            // 解析 Excel 文件
            List<DrugInfo> drugInfoList = parseExcel(inputStream);

            // 调用保存数据的方法
            for (DrugInfo drugInfo : drugInfoList) {
                drugInfoService.save(drugInfo);
            }

            return ResponseEntity.ok("数据导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("数据导入失败: " + e.getMessage());
        }
    }

    private List<DrugInfo> parseExcel(InputStream inputStream) throws Exception {
        List<DrugInfo> drugInfoList = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        // 跳过表头行
        int rowIndex = 3;
        Row row;
        while ((row = sheet.getRow(rowIndex++)) != null) {
            DrugInfo drugInfo = new DrugInfo();

            // 基础信息
            drugInfo.setDrugNameCn(getCellValue(row.getCell(1)));
            drugInfo.setDrugNameEn(getCellValue(row.getCell(2)));
            drugInfo.setTradeName(getCellValue(row.getCell(3)));
            drugInfo.setDosageForm(getCellValue(row.getCell(4)));
            drugInfo.setSpecifications(getCellValue(row.getCell(5)));
            drugInfo.setTarget(getCellValue(row.getCell(6)));
            drugInfo.setActiveIngredient(getCellValue(row.getCell(7)));
//            drugInfo.setAtc(getCellValue(row.getCell(8)));
//            drugInfo.setIndications(getCellValue(row.getCell(9))); // 假设适应症在第 10 列

            // 上市信息
            drugInfo.setApprovalNumber(getCellValue(row.getCell(9)));
            drugInfo.setApprovalDate(getCellValue(row.getCell(10))); // 假设批准日期在第 12 列
            drugInfo.setMarketingAuthorizationHolder(getCellValue(row.getCell(11)));
            drugInfo.setAddressOfMarketingAuthorizationHolder(getCellValue(row.getCell(12)));
            drugInfo.setManufacturer(getCellValue(row.getCell(13)));
            drugInfo.setManufacturerAddress(getCellValue(row.getCell(14)));
//            drugInfo.setListedCountry(getCellValue(row.getCell(15)));

            // 医保信息
            drugInfo.setMedicalInsuranceClassification(getCellValue(row.getCell(16)));
            drugInfo.setMedicalInsuranceNumber(getCellValue(row.getCell(17)));
            drugInfo.setUsageScope(getCellValue(row.getCell(18)));
            drugInfo.setOctPrescriptionDrug(getCellValue(row.getCell(19)));

            drugInfoList.add(drugInfo);
        }

        return drugInfoList;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case 1:
                return cell.getStringCellValue();
            case 0:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case 4:
                return String.valueOf(cell.getBooleanCellValue());
            case 2:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private Date parseDate(String dateStr) throws Exception {
        // 根据 Excel 中的日期格式进行解析
        // 假设日期格式为 "yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateStr);
    }
} 