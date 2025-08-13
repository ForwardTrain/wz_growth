package com.school.wz_growth.common.qiniu;

import com.qiniu.common.Zone;

import java.util.Properties;


public class QiniuConfig {

    private String accessKey;
    private String secretKey;
    private String bucketName;//空间名
    private Zone zone;//[{'zone0':'华东'}, {'zone1':'华北'},{'zone2':'华南'},{'zoneNa0':'北美'},{'zoneAs0':''}]
    private String domainOfBucket;//外链默认域名
    private long expireInSeconds;

    private static QiniuConfig instance = new QiniuConfig();

    public static QiniuConfig getInstance(){
        return instance;
    }


    private QiniuConfig(){
        Properties prop = new Properties();
        try {

            prop.load(QiniuConfig.class.getResourceAsStream("/qinio.properties"));
            accessKey = prop.getProperty("qiniu.access-key");
            secretKey = prop.getProperty("qiniu.secret-key");
            bucketName = prop.getProperty("qiniu.bucket");
            domainOfBucket = prop.getProperty("qiniu.domain-of-bucket");
            expireInSeconds = Long.parseLong(prop.getProperty("qiniu.expire-in-seconds"));
            String zoneName = prop.getProperty("qiniu.zone");
            if(zoneName.equals("zone0")){
                zone = Zone.zone0();
            }else if(zoneName.equals("zone1")){
                zone = Zone.zone1();
            }else if(zoneName.equals("zone2")){
                zone = Zone.zone2();
            }else if(zoneName.equals("zoneNa0")){
                zone = Zone.zoneNa0();
            }else if(zoneName.equals("zoneAs0")){
                zone = Zone.zoneAs0();
            }else if (zoneName.equals("zone02")){

                zone = (new Zone.Builder()).region("cn-east-2").upHttp("http://upload-cn-east-2.qiniup.com").upHttps("https://upload-cn-east-2.qiniup.com").upBackupHttp("http://up-cn-east-2.qiniup.com").upBackupHttps("https://up-cn-east-2.qiniup.com").iovipHttp("http://iovip-cn-east-2.qiniuio.com").iovipHttps("https://iovip-cn-east-2.qiniuio.com").rsHttp("http://rs-cn-east-2.qiniuapi.com").rsHttps("https://rs-as0.qbox.me").rsfHttp("http://rs-cn-east-2.qiniuapi.com").rsfHttps("https://rsf-as0.qbox.me"). apiHttp("http://api.qiniuapi.com").apiHttps("https://api-as0.qiniu.com").build();
            }else{
                throw new Exception("Zone对象配置错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public Zone getZone() {
        return zone;
    }

    public String getDomainOfBucket() {
        return domainOfBucket;
    }

    public long getExpireInSeconds() {
        return expireInSeconds;
    }


}
