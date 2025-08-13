package com.school.wz_growth.common.validator;

import java.security.SecureRandom;

public class RandomUtils {


    /**  随机生成一个n位的随机数（包含大小写，数字）   */
    public static String CAPTCHA(int n) {
        SecureRandom r = new SecureRandom();
        String code = "";//分配一个空字符内存
        for(int i = 0; i < n; i++)
        {
            int type = r.nextInt(3);
            switch(type) {
                case 0://大写字母
                    char c0 = (char)(r.nextInt(26) + 65);//ASII中大写字母的范围
                    code += c0;
                    break;
                case 1 ://小写字母
                    char c1 = (char)(r.nextInt(26) + 97);//ASII中小写字母的范围
                    code += c1;
                    break;
                case 2://数字
                    int m = r.nextInt(10);//生成0~9的随机数
                    code += m;
                    break;

            }
        }
        return code;
    }

    /**  随机生成一个n位的随机数（只有数字）   */
    public static String CAPTCHA_byNum(int n) {
        SecureRandom r = new SecureRandom();
        String code = "";//分配一个空字符内存
        for(int i = 0; i < n; i++)
        {
            int m = r.nextInt(10);//生成0~9的随机数
            code += m;
        }
        return code;
    }


    public static void main(String[] args) {


        String code = CAPTCHA_byNum(4);//生成四位数的随机数
        System.out.println("随机验证码：" + code);
    }
}
