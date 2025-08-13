package com.school.wz_growth.common.excel;



import com.sun.rowset.internal.Row;
import javafx.scene.control.Cell;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公用数据读取逻辑
 */
public class ExcelReaderUtil {
    /**
     * 2003
     * @param file
     * @return
     * @throws
     */
    public static List<Map<String,Object>> readExcelContent(MultipartFile file){
        try{
            return readExcelContentXlsx(file);
        }catch ( Exception e){
            return readExcelContentXls(file);
        }
    }

    /**
     * 2003
     * @param file
     * @return
     * @throws
     */
    public static List<Map<String,Object>> readExcelContentForContract(MultipartFile file,String  contract_names){
        try{

            String fileName = file.getOriginalFilename();
//            String[] sub_strs = fileName.split(".");
            String exten_str = fileName.substring(fileName.lastIndexOf(".")+1);
         if (exten_str.equals("xlsx"))
         {
             return readExcelContentContractXlsx(file,contract_names);
         }else {
             return readExcelContentContractXls(file,contract_names);
         }

        }catch ( Exception e){
//            return readExcelContentXls(file);
             e.printStackTrace();
        }
        return null;
    }

    /**
     * 2003
     * @param file
     * @return
     * @throws
     */
    public static Map<String,Object> readExcelContentForPower(MultipartFile file){
//        try{
            return readExcelContentPower(file);
//        }catch ( Exception e){
//            return readExcelContentXls(file);
//        }
    }



    public static List<Map<String,Object>> readExcelContentXls(MultipartFile file) {
        List<Map<String,Object>> list = new ArrayList<>();
        POIFSFileSystem fs;
        HSSFWorkbook wb;
        try {
            wb = new HSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw null;
        }
        HSSFSheet sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        HSSFRow row;
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            Map<String,Object> powers = new HashMap<>();
            String biz_name = getCellFormatValue(row.getCell((short) 0)).trim();
            String user_number = getCellFormatValue(row.getCell((short) 1)).trim();
            String year = getCellFormatValue(row.getCell((short) 2)).trim();
            String month = getCellFormatValue(row.getCell((short) 3)).trim();
            String spike_electricity_power = getCellFormatValue(row.getCell((short) 4)).trim();
            String pike_electricity_power = getCellFormatValue(row.getCell((short) 5)).trim();
            String gufeng_power = getCellFormatValue(row.getCell((short) 6)).trim();

            if(StringUtils.isBlank(user_number) ||
                    StringUtils.isBlank(year) ||
                    StringUtils.isBlank(month)||
                    StringUtils.isBlank(spike_electricity_power)||
                    StringUtils.isBlank(pike_electricity_power)||
                    StringUtils.isBlank(gufeng_power))
                continue;
            powers.put("biz_name",biz_name);
            powers.put("user_number",user_number);
            powers.put("year",year);
            powers.put("month",month);
            powers.put("spike_electricity_power",spike_electricity_power);
            powers.put("pike_electricity_power",pike_electricity_power);
            powers.put("gufeng_power",gufeng_power);
            list.add(powers);
        }
        return list;
    }


    /**
     * 2003
     * @param cell
     * @return
     */
    private static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        //cellvalue = cell.getDateCellValue().toLocaleString();

                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);

                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    /**
     * 2007
     * @param file
     * @return
     * @throws
     */
    public static List<Map<String,Object>> readExcelContentXlsx(MultipartFile file) {
        List<Map<String,Object>> list = new ArrayList<>();
        POIFSFileSystem fs;
        XSSFWorkbook wb;
        try {
            wb = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw null;
        }
        XSSFSheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        XSSFRow row;
        for (int i =1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            Map<String,Object> data = new HashMap<>();
            String name = getCellFormatValue2(row.getCell((short) 0)).trim();
            String start_time = getCellFormatValue2(row.getCell((short) 1)).trim();
            String end_time = getCellFormatValue2(row.getCell((short) 2)).trim();
            String device_status = getCellFormatValue2(row.getCell((short) 3)).trim();
            String descption = getCellFormatValue2(row.getCell((short) 4)).trim();
            if(StringUtils.isBlank(name) &&
                    StringUtils.isBlank(start_time) &&
                    StringUtils.isBlank(end_time)&&
                    StringUtils.isBlank(device_status)&&
                    StringUtils.isBlank(descption))
                continue;
            data.put("name",name);
            data.put("start_time",start_time);
            data.put("end_time",end_time);
            data.put("device_status",device_status);
            data.put("descption",descption);
            list.add(data);
        }
        return list;
    }

    /**
     * 2007
     * @param file
     * @return
     * @throws
     */
    public static Map<String,Object> readExcelContentPower(MultipartFile file) {
        List<Map<String,Object>> list = new ArrayList<>();
        Map res_map = new HashMap();

        List<Map<String,Object>> s_times=new ArrayList<>();

        POIFSFileSystem fs;
        XSSFWorkbook wb;
        try {
            wb = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw null;
        }
        XSSFSheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        XSSFRow row;

        for (int i =0; i < 1; i++) {
            row = sheet.getRow(i);
            int cells = row.getLastCellNum();//所有的cell
            if (i == 0) {
                for (int c = 2; c < cells; c++) {
                    Map<String, Object> map = new HashMap<>();
                    String s_time = getCellFormatValue3(row.getCell((short) c)).trim();
                    //非空判断
                    if(stringIsEmpty(s_time))
                        throw new RuntimeException("时间不能为空！");
                    map.put("s_time", s_time);
                    s_times.add(map);
                    //超过00:00的时间点不要
                    if(s_time.equals("00:00"))
                        break;
                }
            }
        }
        for (int z =1; z <= rowNum; z++) {
            //添加电量合计
            BigDecimal total=BigDecimal.ZERO;
            row = sheet.getRow(z);
            int cells = row.getLastCellNum();//所有的cell
            if(z%2 == 1){
                List<Map<String,Object>> data_list = new ArrayList<>();
                for(int c=2;c<cells;c++){
                    Map<String,Object> data_map=new HashMap<>();
                    Map<String,Object> map=s_times.get(c-2);
                    String power=getCellFormatValue3(row.getCell((short) c)).trim();
                    if(stringIsEmpty(power))
                        throw new RuntimeException("电量不能为空！");
                    String s_time=map.get("s_time").toString();
                    data_map.put("a_value",power);
                    data_map.put("s_time",s_time);
                    data_list.add(data_map);
                    total= total.add(new BigDecimal(power));
                    if(s_time.equals("00:00"))
                        break;
                }
                list.addAll(data_list);
                res_map.put("list",data_list);
                res_map.put("total",total);
//                list.add(m);
            }

        }
      //  return list;//不带total的返回list
        return res_map;
    }
    static boolean stringIsEmpty(String s){
        if(s==null||s.equals("")||s.length()<=0||s.isEmpty())
            return true;
        return false;
    }

    /**
     * 2007
     * @param file
     * @return
     * @throws
     */
    public static List<Map<String,Object>> readExcelContentContractXlsx(MultipartFile file,String contract_names)throws Exception {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> s_times=new ArrayList<>();

        POIFSFileSystem fs;
        XSSFWorkbook wb;
        try {
            wb = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw null;
        }
        XSSFSheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        XSSFRow row;

        for (int i =0; i < 1; i++) {
            row = sheet.getRow(i);
            int cells = row.getLastCellNum();//所有的cell
            if (i == 0) {
                for (int c = 3; c < cells; c++) {
                    Map<String, Object> map = new HashMap<>();
                    String s_time = getCellFormatValue3(row.getCell((short) c)).trim();
                    map.put("s_time", s_time);
                    s_times.add(map);
                }
            }
        }
        for (int z =1; z <= rowNum; z++) {
            row = sheet.getRow(z);
            int cells = row.getLastCellNum();//所有的cell
            if(z%2 == 1){
                String contract_type = getCellFormatValue2(row.getCell((short) 0)).trim();
                String contract_user = getCellFormatValue2(row.getCell((short) 1)).trim();
                List<Map<String,Object>> data_list = new ArrayList<>();

                for(int c=3;c<cells;c++){
                    Map<String,Object> data_map=new HashMap<>();

                    Map<String,Object> map=s_times.get(c-3);
                    String power=getCellFormatValue3(row.getCell((short) c)).trim();
                    data_map.put("power",power);
                    data_map.put("s_time",map.get("s_time"));
                    data_list.add(data_map);
                }
                Map<String,Object> map=new HashMap<>();
                if(!contract_names.contains(contract_type)){
                    throw  new Exception("合约类型不匹配");
                }
                map.put("contract_type",contract_type);
                map.put("contract_user",contract_user);
                map.put("data_list",data_list);
                list.add(map);
            }
            else{
                int  index=(z/2)-1;
                Map<String,Object> map=list.get(index);

                List<Map<String,Object>> data_list = (List<Map<String,Object>>)map.get("data_list");
                for(int c=3;c<cells;c++){
                    Map<String,Object> data_map=data_list.get(c-3);
                    String price=getCellFormatValue3(row.getCell((short) c)).trim();
                    data_map.put("price",price);
                }
            }
        }
        return list;
    }
    /**
     * 2003
     * @param file
     * @return
     * @throws
     */
    public static List<Map<String,Object>> readExcelContentContractXls(MultipartFile file,String contract_names)throws Exception {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> s_times=new ArrayList<>();

        POIFSFileSystem fs;
        HSSFWorkbook wb;
        try {
            wb = new HSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw null;
        }
        HSSFSheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        HSSFRow row;
//        getCellFormatValue
        for (int i =0; i < 1; i++) {
            row = sheet.getRow(i);
            if (row==null)
                break;

            int cells = row.getLastCellNum();//所有的cell
            if (i == 0) {
                for (int c = 3; c < cells; c++) {
                    Map<String, Object> map = new HashMap<>();
                    String s_time = getCellFormatValue4(row.getCell((short) c)).trim();
                    map.put("s_time", s_time);
                    s_times.add(map);
                }
            }
        }
        for (int z =1; z <= rowNum; z++) {
            row = sheet.getRow(z);
            if (row==null)
                break;

            int cells = row.getLastCellNum();//所有的cell
            if(z%2 == 1){
                String contract_type = getCellFormatValue4(row.getCell((short) 0)).trim();
                String contract_user = getCellFormatValue4(row.getCell((short) 1)).trim();
                List<Map<String,Object>> data_list = new ArrayList<>();

                for(int c=3;c<cells;c++){
                    Map<String,Object> data_map=new HashMap<>();

                    Map<String,Object> map=s_times.get(c-3);
                    String power=getCellFormatValue4(row.getCell((short) c)).trim();
                    data_map.put("power",power);
                    data_map.put("s_time",map.get("s_time"));
                    data_list.add(data_map);
                }
                Map<String,Object> map=new HashMap<>();
                if(!contract_names.contains(contract_type)){
                    throw  new Exception("合约类型不匹配");
                }
                map.put("contract_type",contract_type);
                map.put("contract_user",contract_user);
                map.put("data_list",data_list);
                list.add(map);
            }
            else{
                int  index=(z/2)-1;
                Map<String,Object> map=list.get(index);

                List<Map<String,Object>> data_list = (List<Map<String,Object>>)map.get("data_list");
                for(int c=3;c<cells;c++){
                    Map<String,Object> data_map=data_list.get(c-3);
                    String price=getCellFormatValue4(row.getCell((short) c)).trim();
                    data_map.put("price",price);
                }
            }
        }
        return list;
    }



    /**
     * 2007
     * @param cell
     * @return
     */
    private static String getCellFormatValue2(XSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case XSSFCell.CELL_TYPE_NUMERIC:
                case XSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        //cellvalue = cell.getDateCellValue().toLocaleString();

                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        cellvalue = sdf.format(date);

                    }
                    // 如果是纯数字
                    else {
                        DecimalFormat df = new DecimalFormat("#.0000");
                        if(cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN)
                            cellvalue = String.valueOf(cell.getBooleanCellValue());
                         else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC)
                            cellvalue = df.format(cell.getNumericCellValue());
                         else
                            cellvalue = cell.getStringCellValue();

                        // 取得当前Cell的数值
//                        cellvalue= cell.getStringCellValue();
//                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case XSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }


    /**
     * 2007
     * @param cell
     * @return
     */
    private static String getCellFormatValue3(XSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case XSSFCell.CELL_TYPE_NUMERIC:
                case XSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        //cellvalue = cell.getDateCellValue().toLocaleString();

                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        cellvalue = sdf.format(date);

                    }
                    // 如果是纯数字
                    else {
                        DecimalFormat df = new DecimalFormat("#.0000");
                        if(cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN)
                            cellvalue = String.valueOf(cell.getBooleanCellValue());
                        else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC)
                            cellvalue = df.format(cell.getNumericCellValue());
                        else
                            cellvalue = cell.getStringCellValue();

                        // 取得当前Cell的数值
//                        cellvalue= cell.getStringCellValue();
//                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case XSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
    /**
     * 2003
     * @param cell
     * @return
     */
    private static String getCellFormatValue4(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        //cellvalue = cell.getDateCellValue().toLocaleString();

                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        cellvalue = sdf.format(date);

                    }
                    // 如果是纯数字
                    else {
                        DecimalFormat df = new DecimalFormat("#.0000");
                        if(cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN)
                            cellvalue = String.valueOf(cell.getBooleanCellValue());
                        else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC)
                            cellvalue = df.format(cell.getNumericCellValue());
                        else
                            cellvalue = cell.getStringCellValue();

                        // 取得当前Cell的数值
//                        cellvalue= cell.getStringCellValue();
//                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }




}