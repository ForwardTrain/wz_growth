package com.school.wz_growth.common.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportExcelDataTool {
    private final HSSFWorkbook workbook;

    private final HSSFSheet sheet;

    private final HSSFCellStyle cellStyle;


    public ExportExcelDataTool() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet();
        workbook.setSheetName(0, "身份证明细模版");

        createSheet(sheet);
        Font font = workbook.createFont();
        font.setColor(HSSFColor.RED.index);
        cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
    }


    /**
     * @param sheet
     */
    private void createSheet(HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0, 1);
        cell.setCellValue("身份证号");

        row = sheet.createRow(1);
        cell = row.createCell(0, 1);
        cell.setCellValue("42013019923000000037792");
    }

    public void exportExcel2Brower(HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String nowDate = sdf.format(new Date());
        String fileName = "attachment; filename=示例-" + nowDate + ".xls";
        OutputStream out = null;
        try {
            response.setHeader("Content-disposition", new String(fileName.getBytes("utf-8"),
                    "utf-8"));
            response.setContentType("application/msexcel;charset=utf-8");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            //
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    //
                }
            }
        }
    }
}