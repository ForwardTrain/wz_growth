package com.school.wz_growth.common.qiniu;


//import cn.jiguang.common.utils.StringUtils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Client;
import com.qiniu.http.Headers;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.school.wz_growth.common.validator.StringUnits;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;



import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class QiniuUpload {

    //密钥配置
    Auth auth = Auth.create(QiniuConfig.getInstance().getAccessKey(), QiniuConfig.getInstance().getSecretKey());
    Configuration c = new Configuration(QiniuConfig.getInstance().getZone());

    //创建上传对象
    UploadManager uploadManager = new UploadManager(c);

    //基础url
    public static String qiniu_base_url = "http://"+QiniuConfig.getInstance().getDomainOfBucket();

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(QiniuConfig.getInstance().getBucketName());
    }

    /**
     * 上传文件
     * @param filePath 上传的本地文件目录
     * @param key_service 上传到七牛的文件名称
     */
    public boolean upload(String filePath,String key_service) {
        try {
            //调用put方法上传
            Response res = uploadManager.put(filePath, key_service, getUpToken());

            if (res.statusCode == 200)
            {//上传成功
                return true;
            }
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
//            try {
//                //响应的文本信息
//                System.out.println(r.bodyString());
//            } catch (QiniuException e1) {
//                //ignore
//            }
        }
        return false;
    }

    /**
     * 从七牛云删除图片
     * @param fileNameList 图片名集合
     * @return 返回每张图片的删除结果
     */
    public  Object qiniuCloudDeleteImage(String[] fileNameList){
        //用Json格式传数组时直接传["xxx", "xxx", "xxx", "xxx"]
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //生成凭证
        String accessKey = QiniuConfig.getInstance().getAccessKey();
        String secretKey =  QiniuConfig.getInstance().getSecretKey();
        String bucket =  QiniuConfig.getInstance().getBucketName();
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //创建一个用来接收删除结果的集合
        List<String> list = new ArrayList<>();
        try {
            //单次批量请求的文件数量不得超过1000
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, fileNameList);
            Response response = bucketManager.batch(batchOperations);
            System.out.printf("删除了");
//            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
//            for (int i = 0; i < fileNameList.length; i++)
//            {
//                BatchStatus status = batchStatusList[i];
//                if (status.code == 200) {//成leN功返回文件名+true并添加进集合
//                    list.add("{fileName:"+fiameList[i]+",deleteResult:true}");
//                }else {//失败返回文件名+false并添加进集合
//                    list.add("{fileName:"+fileNameList[i]+",deleteResult:false}");
//                }
//            }
        } catch (QiniuException ex) {
            System.err.println("七牛云ERROR:" + ex.response.toString());
        }
        return list;
    }


    /**
     * 返回文件下载地址 如: http://q0jg8f8fj.bkt.clouddn.com/upload/testJava.png
     * @param filePath
     * @return
     */
    public String getDownloadUrl(String filePath){

//        return  "http://"+QiniuConfig.getInstance().getDomainOfBucket()+"/"+filePath;
//        String.format("http://%s/%s",QiniuConfig.getInstance().getDomainOfBucket(), filePath)
//        String encodedFileName = URLEncoder.encode(fileName, "utf-8");
//        String publicUrl = String.format("http://%s/%s",QiniuConfig.getInstance().getDomainOfBucket(), filePath);
//        long expireInSeconds = QiniuConfig.getInstance().getExpireInSeconds();
//        if(-1 == expireInSeconds){
//            return auth.privateDownloadUrl(publicUrl);
//        }
//        return auth.privateDownloadUrl(publicUrl, expireInSeconds);


//        String publicUrl = "/"+filePath;
        String publicUrl = String.format("http://%s/%s",QiniuConfig.getInstance().getDomainOfBucket(), filePath);
        return publicUrl;
    }

    /** 图片 - base64上传
     * img_data_str 图片base64编码数据
     * */
    public boolean putBase64image(String img_data_str,String img_name) throws Exception {
//        String file = "/Users/wjb/Desktop/test.png";//图片路径
//        FileInputStream fis = null;
//        int l = (int) (new File(file).length());
//        byte[] src = new byte[l];
//        fis = new FileInputStream(new File(file));
//        fis.read(src);
//        String file64 = Base64.encodeToString(src, 0);
        int l = -1;//文件大小。支持传入 -1 表示文件大小以 http request body 为准。
        String key = img_name;//"xxm202301012003"; //文件名称

        String file64 = img_data_str;
        String url = "http://up-z0.qiniup.com/putb64/" + l+"/key/"+ UrlSafeBase64.encodeToString(key);
        //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody rb = RequestBody.create(null, file64);

        System.out.println("========");
        System.out.println(getUpToken());

        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
//        System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        if (response.code() == 200)
            return true;
        else
        {
            System.out.println(response);
            return false;
        }
//        System.out.println(response);
    }

    /**
     * 上传文件
     * @param filePath 上传的文件 链接地址
     * @param key_service 上传到七牛的文件名称
     */
    public String feRet(String filePath,String key_service) {
        try {
            BucketManager bucketManager=new BucketManager(auth,c);
            FetchRet fetchRet=bucketManager.fetch(filePath,QiniuConfig.getInstance().getBucketName(),key_service);
            return fetchRet.key;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
        }
        return "";
    }


    /**
     * 文件在线打包
     */
    public void files_mkzip(Map<String,Object> params) throws Exception {

        //压缩包文件
//        String zipFileName = "demo_java.zip";
//        //数据处理完成结果通知地址
//        String persistentNotifyUrl = "http://api.example.com/qiniu/pfop/notify";
//        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String srcKey = "index_java.txt";
//        List<String> fileUrlList = new ArrayList<>(Arrays.asList("http://filezdmanager.hello2345.com/1715158516620123.jpg", "http://filezdmanager.hello2345.com/171515853508912.jpg"));
//        List<String> aliasList =  new ArrayList<>(Arrays.asList("xxm_test005.jpg", ""));

         String zipFileName = params.get("zipFileName").toString();
        //数据处理完成结果通知地址
        String persistentNotifyUrl = params.get("persistentNotifyUrl").toString();
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String srcKey = params.get("srcKey").toString();
        List<Map<String,Object>> url_obj_list = (List<Map<String, Object>>) params.get("list");
        List<String> fileUrlList = new ArrayList<>();
        List<String> aliasList =  new ArrayList<>();
        for (Map<String, Object> stringObjectMap : url_obj_list)
        {
           fileUrlList.add(stringObjectMap.get("url").toString());

            //打包文件的名称，不传为原文件名称
           if (StringUnits.isNotNullOrEmpty(stringObjectMap.get("name")))
               aliasList.add(stringObjectMap.get("name").toString());
           else
               aliasList.add("");
        }


        Path path = null;
        try {

            /** 1.打包txt文件的处理和上传 */
            StringMap putPolicy = new StringMap();
            //数据处理指令
            String zipFops = String.format("mkzip/4/|saveas/%s", UrlSafeBase64.encodeToString(QiniuConfig.getInstance().getBucketName() + ":" + zipFileName));
            putPolicy.put("persistentOps", zipFops);
            //数据处理队列名称，必填
            putPolicy.put("persistentPipeline", "pipeline");
            //数据处理完成结果通知地址
            putPolicy.put("persistentNotifyUrl", persistentNotifyUrl);
            long expireSeconds = 3600;
//            String upToken = auth.uploadToken(QiniuConfig.getInstance().getBucketName(), null, expireSeconds, putPolicy);
            //构造一个带指定Zone对象的配置类
            //Configuration cfg = new Configuration(Zone.zone2());
            //...其他参数参考类注释
            UploadManager uploadManager = new UploadManager(c);

            // 创建临时索引文件
            String txt_file_name_str =srcKey.substring(0,srcKey.lastIndexOf("."));
            path = Files.createTempFile(txt_file_name_str, ".txt");
            for(int i = 0 ; i < fileUrlList.size(); i++) {
                String line = "/url/" + UrlSafeBase64.encodeToString(fileUrlList.get(i));
                if (StringUtils.isNotEmpty(aliasList.get(i))) {
                    line += "/alias/" + UrlSafeBase64.encodeToString(aliasList.get(i));
                }
                // 按行写入
                Files.write(path,
                        Arrays.asList(line),
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            }

            Response response = uploadManager.put(path.toString(), srcKey, getUpToken());
            if (response.statusCode != 200)
            {//上传失败
                System.out.println("上传失败");
                throw new Exception("文件上传失败");
            }

            /** 2.在线打包 */
            //将多个数据处理指令拼接起来
            String persistentOpfs = StringUtils.join(new String[]{
                    zipFops
            }, ";");

            //数据处理队列名称，必须
            String persistentPipeline = "";
            //构造一个带指定 Region 对象的配置类
            Configuration cfg = new Configuration(Region.region0());
            //构建持久化数据处理对象
            OperationManager operationManager = new OperationManager(auth, cfg);
            String persistentId = pfop(QiniuConfig.getInstance().getBucketName(), srcKey, persistentOpfs, persistentPipeline, persistentNotifyUrl, true);

            //可以根据该 persistentId 查询任务处理进度
//            System.out.println(String.format("任务处理进度:%s",persistentId));
            for (int i = 0; i < 30; i++)
            {
                Thread.currentThread().sleep(1000);
                OperationStatus operationStatus = operationManager.prefop(persistentId);
                if (operationStatus.code != 1)
                {
                    System.out.println(String.format("打包结果:%s%s","打包中",i));
                    //System.out.println(String.format("打包结果:%s",operationStatus.desc));
                } else{
                    System.out.println(String.format("打包结果:%s","成功"));
                    break;
                }
            }


//            /** 3.删除 -- 打包txt文件 */
//            String[] delete_files_array = new String[]{srcKey};
//            qiniuCloudDeleteImage(delete_files_array);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            // 应用退出时删除临时文件
            path.toFile().deleteOnExit();
        }

    }

    public String pfop(String bucket, String key, String fops, String pipeline, String notifyURL, boolean force)
            throws QiniuException {
        StringMap params = new StringMap().putNotEmpty("pipeline", pipeline).
                putNotEmpty("notifyURL", notifyURL).putWhen("force", 1, force);
        return pfop(bucket, key, fops, params);
    }

    public String pfop(String bucket, String key, String fops, StringMap params) throws QiniuException {
        params = params == null ? new StringMap() : params;
        params.put("bucket", bucket).put("key", key).put("fops", fops);
        byte[] data = new byte[0];  //StringUtils.utf8Bytes(params.formString());
        try {
            data = params.formString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String url = "https://api.qiniuapi.com/pfop/";

        StringMap headersMap = new StringMap();
        headersMap.put("Content-Type", "application/x-www-form-urlencoded");
        Headers headers = Headers.of(headersMap);
        String ac = auth.signQiniuAuthorization(url, "POST", data, headers);
        String localAuthorization = "Qiniu " + ac;
        headersMap.put("Authorization", localAuthorization);
        Client client = new Client();
        Response response = client.post(url, data,
                headersMap, Client.FormMime);
        if (!response.isOK()) {
            throw new QiniuException(response);
        }
        PfopResult status = response.jsonToObject(PfopResult.class);
        response.close();
        if (status != null) {
            return status.persistentId;
        }
        return null;
    }

    private class PfopResult {
        public String persistentId;
    }

    public static void main(String[] args) {

//        String filePath = "http://219.139.191.20:4061/group1/images/2023101910414665997_1_2023090713393660603Bitmap.png?download=0";
//        String fileName = "upload_testJava.png";
//        new QiniuUpload().feRet(filePath,fileName);


//        String filePath = new QiniuUpload().getDownloadUrl("/upload/testJava.png");
//        System.out.println(filePath);

//        String img_data = "iVBORw0KGgoAAAANSUhEUgAAAA8AAAAPCAYAAAA71pVKAAAAAXNSR0IArs4c6QAAAOlJREFUOE+lk8FKQkEUhr//ehctXYStewQhREpNaJH7IALrXXyXCCJoraViRUlE4CO0Ll24DPTeI3PDuMQ1spnVYYbvP3P++Ueklg3qIbPoBNFE2gHywBSzV4wLhrlLte7mS0TLwm5q2+S4BoppwR/1iIgjHT68uf0ETsDAnpEKv4BfR2YfxCo7AVmrHlKJXzI77l3B03GW3ojHoCS7rZ4S6Dyz48E99PezLxPbmaxbbSM11obNOrJebQxsrg3DxMEzIExgN+PG1mrPPt/THsw9O3vN7OW2i2T0z3f2Sph3tr8FXFR3//6rFi/+f1IAY6oPAAAAAElFTkSuQmCC";
//        try {
////            new QiniuUpload().putBase64image(img_data,"xxm_text.pgn");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        System.out.println(UrlSafeBase64.encodeToString(""));
        try {
//            new QiniuUpload().files_mkzip(null);

//            String[] res_array = {"17156944750101.粮油技术培训班的通知.pdf"};
//            new QiniuUpload().QiniuCloudDeleteImage(res_array);


//            //在线打包
//            Map<String,Object> params = new HashMap<>();
//            //压缩包文件
//            params.put("zipFileName","demo_java.zip");
//            //数据处理完成结果通知地址
//            params.put("persistentNotifyUrl","http://api.example.com/qiniu/pfop/notify");
//            //默认不指定key的情况下，以文件内容的hash值作为文件名
//            params.put("srcKey","index_java.txt");
//
//            List<Map<String,Object>> list =  new ArrayList<>();
//            list.add(new HashMap(){{
//                put("url","http://filezdmanager.hello2345.com/WeChatcf614e2da258e327e400c5da322434de.jpg");
//            }});
//            list.add(new HashMap(){{
//                put("url","http://filezdmanager.hello2345.com/WeChatad6c612a55320f3dceddaef5e597595b.jpg");
//            }});
//            list.add(new HashMap(){{
//                put("url","http://filezdmanager.hello2345.com/WeChat3e4907b1e32ff35c36e8afbc2e80a512.jpg");
//            }});
//            params.put("list",list);
//            new QiniuUpload().files_mkzip(params);


            String fileName = "13341_zip.zip";
            String temp_str =  fileName.substring(0,fileName.lastIndexOf("."));

//            String txt_file_name_str =srcKey_array[0];
            System.out.println(temp_str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
