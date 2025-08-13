package com.school.wz_growth.common.validator;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUnits {


    /**
     *  list -> string
     * @param list
     * @param separator
     * @return
     */
    public static String listToString(JSONArray list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
        {
            JSONObject obj = list.getJSONObject((i));
            if (StringUnits.isNotNullOrEmpty(obj.get("name")))
            {
                sb.append(obj.getStr("name"));
                if (i < list.size() - 1)
                    sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static String listToString2(JSONArray list, char separator,String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
        {
            JSONObject obj = list.getJSONObject((i));
            if (StringUnits.isNotNullOrEmpty(obj.get(key)))
            {
                sb.append(obj.getStr(key));
                if (i < list.size() - 1)
                    sb.append(separator);
            }
        }
        return sb.toString();
    }


    public static boolean isNotNullOrEmpty(Object toTest) {

        if (toTest==null)
            return false;
//        System.out.println(toTest.toString().length());
        if (toTest.toString().length()>0)
            return true;

        return false;
    }


    /**
     * 获得上传图片新名字 yyyyMMddHHmmss+5位随机数
     *
     * @return
     */
    public static String getByNewFileName() {
        return DateUtils.formatDate1() + UUIDUtils.getUUID(5);
    }


    /**
     *  数据 改为 数据 + 10的多少次方
     *
     * @return
     */
    public static Map<String,Object> changeNumToBasicUnit(double input_num) {

        BigDecimal input_num_bd  =  new BigDecimal(input_num);

        BigDecimal bd_10 = new BigDecimal (10);

        Map<String,Object> res = new HashMap<>();

        if (input_num_bd.compareTo(bd_10) == -1) // <10的意思
        {
            res.put("num",Math.floor(input_num));
            res.put("unit","");
            return res;
        }


        int index_num = 0;//几方上标值
//        double  temp_num = Math.floor(input_num);
        for (int i = 0; i < 50; i++)
        {
            if (input_num_bd.compareTo(bd_10) == -1)  // <10的意思
                break;
            input_num_bd = input_num_bd.divide(bd_10);
            index_num ++;
        }

        double temp_num = input_num_bd.doubleValue();
        int res_interget = (int) Math.floor(temp_num);
        res.put("num",res_interget);
        res.put("unit","10^"+index_num);
        return res;
    }



    /** 判断是否为正整数 的文本 */
    public static boolean isInteger(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }


    /** 判断是否为纯整数 的文本 */
    public static boolean isNumeric(String str){

        Pattern pattern = Pattern.compile("[-\\+]?[0-9]*\\.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /** 字符串改为首字母大学 其他小写 */
    public static String capitalizeWords(String input) {
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                String capitalized = Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
                result.append(capitalized).append(" ");
            }
        }
        return result.toString().trim();
    }

    /** 进行不区分大小写的匹配 */
    public static String convertHighLight(String keyWords, String text) {
        // 进行不区分大小写的匹配
        Pattern pattern = Pattern.compile(keyWords, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        // 使用 StringBuffer 来构造替换后的字符串
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            // 保留原始大小写的内容，并添加 <span> 标签
            matcher.appendReplacement(result, "<span style=\"background-color: yellow;\">" + matcher.group() + "</span>");
        }
        return matcher.appendTail(result).toString();
    }


    public static void main(String[] args) {

//        String  str1 = "6_1_11625580721249_load_forecast_2021-07-01.xls";
//        String  str2 = "load";
//        System.out.println(str1.contains(str2)==true?1:0);
//
//        System.out.println(StringUnits.isNotNullOrEmpty(""));
//
//        int k = 0;
//        for (int i = 0; i < k; i++)
//        {
//            System.out.printf("", "");
//        }


//        String input = "hELLO wORLD";
//        String output = capitalizeWords(input);
//        System.out.println(output);


        try {
            String decodedString = URLDecoder.decode(">sp|A0A0C5B5G6|MOTSC_HUMAN Mitochondrial-derived peptide MOTS-c OS=Homo sapiens OX=9606 GN=MT-RNR1 PE=1 SV=1", "UTF-8");

            System.out.println(decodedString);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }







}
