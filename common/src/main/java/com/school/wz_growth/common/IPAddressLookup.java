package com.school.wz_growth.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jthinking.common.util.ip.IPInfo;
import com.jthinking.common.util.ip.IPInfoUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.Document;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class IPAddressLookup {


//    private static final String TAOBAO_IP_LOOKUP_API = "http://ip.taobao.com/service/getIpInfo.php?ip=";
//
//    public static String lookupIPAddress(String ipAddress) throws IOException {
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpGet httpGet = new HttpGet(TAOBAO_IP_LOOKUP_API + ipAddress);
//        HttpResponse response = httpClient.execute(httpGet);
//        HttpEntity entity = response.getEntity();
//        String result = EntityUtils.toString(entity);
//        return result;
//    }


    /**  获取ip的国家  get请求  */
//    @GetMapping("/ipGetCountry")
    public static String ipGetCountry(@RequestParam String ip){
        IPInfo ipInfo = IPInfoUtils.getIpInfo(ip);
        return ipInfo.getCountry();
    }
    /** 获取ip的国家  post请求 */
//    @PostMapping("/ipGetCountry")
    public static String ipPostCountry(String ip){
        IPInfo ipInfo = IPInfoUtils.getIpInfo(ip);
        return ipInfo.getCountry();
    }



    public static void main(String[] args) throws IOException {
        int start = 32;
        int end  = 178;
        System.out.println("MDRAARCSGASSLPLLLALALGLVILHCVVADGNSTRSPETNGLLCGDPEENCAATTTQSKRKGHFSRCPKQYKHYCIKGRCRFVVAEQTPSCVCDEGYIGARCERVDLFYLRGDRGQILVICLIAVMVVFIILVIGVCTCCHPLRKRRKRKKKEEEMETLGKDITPINEDIEETNIA".substring(start-1,end));

        String ipAddress = "121.97.110.1"; // 这里替换为你要查询的实际IP地址
//        String result = IPAddressLookup.lookupIPAddress(ipAddress);
//
//        Gson gson = new Gson();
//        JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
//        if (jsonObject.get("code").getAsInt() == 0) {
//            JsonObject data = jsonObject.get("data").getAsJsonObject();
//            System.out.println("省份：" + data.get("region").getAsString());
//            System.out.println("城市：" + data.get("city").getAsString());
//            System.out.println("运营商：" + data.get("isp").getAsString());
//        } else {
//            System.out.println("查询失败：" + jsonObject.get("msg").getAsString());
//        }


        // 获取IP信息
//        IPInfo ipInfo = IPInfoUtils.getIpInfo(ipAddress);
//        System.out.println(ipInfo.getCountry()); // 国家中文名称
//        System.out.println(ipInfo.getProvince()); // 中国省份中文名称
//        System.out.println(ipInfo.getAddress()); // 详细地址
//        System.out.println(ipInfo.getIsp()); // 互联网服务提供商
//        System.out.println(ipInfo.isOverseas()); // 是否是国外
//        System.out.println(ipInfo.getLat()); // 纬度
//        System.out.println(ipInfo.getLng()); // 经度


//        try {
////            // 建立数据库连接
////            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/wy_school_growth", "root", "123456");
////            Statement statement = connection.createStatement();
////        }catch (Exception e){
////            e.printStackTrace();
////        }






    }


}
