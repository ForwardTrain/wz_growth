package com.school.wz_growth.common.excel;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ImportExcel {
    // abc.xls
    public static boolean isXls(String fileName){
        // (?i)忽略大小写
        if(fileName.matches("^.+\\.(?i)(xls)$")){
            return true;
        }else if(fileName.matches("^.+\\.(?i)(xlsx)$")){
            return false;
        }else{
            throw new RuntimeException("格式不对");
        }
    }

    public static List<JSONObject> readExcel(String fileName, InputStream inputStream) throws Exception{
        List<JSONObject> list = new ArrayList<>();
        boolean ret = isXls(fileName);
        Workbook workbook = null;
        try {

            // 根据后缀创建不同的对象
            if(ret){
                workbook = new HSSFWorkbook(inputStream);
            }else{
                workbook = new XSSFWorkbook(inputStream);
            }
            Sheet sheet = workbook.getSheetAt(0);
            // 得到标题行
            org.apache.poi.ss.usermodel.Row titleRow = sheet.getRow(9);
            int lastRowNum = sheet.getLastRowNum();
            int lastCellNum = titleRow.getLastCellNum();

            JSONObject top_temp_ob = null;
            for(int i = 10; i <= lastRowNum; i++ )
            {
                JSONObject map = new JSONObject();
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if (row == null)
                    break;

                List<JSONObject> half=new ArrayList<>();

                for(int j = 0; j < lastCellNum; j++)
                {
                    // 得到列名
                    String key = titleRow.getCell(j).getStringCellValue();
                    org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
                    // cell.setCellType(CellType.STRING);

                    if(j>4)//每半点时间，另外封装
                    {
                        JSONObject halfs = new JSONObject();
                        halfs.put("power",  getCellValue(cell));
                        halfs.put("s_time",key);
                        half.add(halfs);
                        if(j==lastCellNum-1){
                            map.put("oneDay",half);
                        }
                    }else
                    {
                        if (j==0)//交易主体
                        {
                            String temp_name = getCellValue(cell);
                            if (i>10&&(temp_name==null || temp_name.equals("")))
                            {
                                map.put("transactionSubject",top_temp_ob.get("transactionSubject"));
                            }else {
                                map.put("transactionSubject",temp_name);
                            }
                        }else if(j==1)//交易对方
                        {
                            String temp_name = getCellValue(cell);
                            if (i>10&&(temp_name==null || temp_name.equals("")))
                            {
                                map.put("counterparty",top_temp_ob.get("counterparty"));
                            }else {
                                map.put("counterparty",temp_name);
                            }
                        }else if(j==2) //交易价格
                        {
                            String temp_price = getCellValue(cell);

                            if (i>10&&(temp_price==null || temp_price.equals("")))
                            {
                                map.put("price",top_temp_ob.get("price"));
                            }else {
                                map.put("price",getCellValue(cell));
                            }
                        }else if(j==3)//交易日期
                        {
                            map.put("date",getCellValue(cell));
                        }else if(j==4) //交易电量
                        {
                            map.put("dailyPower",getCellValue(cell));
                        }
//                    map.put(key,  getCellValue(cell));
                    }
                }
                list.add(map);
                top_temp_ob = map;
            }
        }catch (Throwable t)
        {
            t.printStackTrace();
            throw new Exception(t.getMessage());
        }finally {
            if (workbook != null)
                workbook.close();
        }
        return list;
    }

    public static String getCellValue(Cell cell) {
        String temp = "";
        if (cell == null) {
            return temp;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell))
                {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                   String date_str = sdf.format(date);
                   return date_str;
                }else {
                    return String.valueOf(cell.getNumericCellValue());
                }

            case Cell.CELL_TYPE_FORMULA:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();

            default:
                return temp;
        }
    }

    /**
     * 按日期加天数得出全新日期
     * @param date	需要加天数的日期
     * @param day	需要增加的天数
     * @return 新的日期
     */
    private static Calendar fromCal = Calendar.getInstance();
    public static Date addDate(Date date, int day) {
        try {
            fromCal.setTime(date);
            fromCal.add(Calendar.DATE, day);

            return fromCal.getTime();
        } catch (Exception e) {
            return null;
        }
    }
}
