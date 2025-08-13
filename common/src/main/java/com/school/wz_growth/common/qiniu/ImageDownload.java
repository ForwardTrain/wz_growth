package com.school.wz_growth.common.qiniu;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: Haiming Yu
 * @createDate:2022/8/16
 * @description:
 */
public class ImageDownload {

    /**
     * 文件下载到指定路径
     *
     * @param urlString 链接
     * @param savePath  保存路径
     * @param filename  文件名
     *
     * return 本地缓存图片地址
     * @throws Exception
     */
    public static String download(String urlString, String savePath, String filename) throws IOException {

        System.out.println("文件下载地址:"+urlString);
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为20s
        con.setConnectTimeout(20 * 1000);
        //文件路径不存在 则创建
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        //jdk 1.7 新特性自动关闭
        try{
            String file_path = sf.getPath() + File.separator + filename;
//            File img_file = new File(file_path);
//            if (img_file.exists()==false)
            OutputStream out = new FileOutputStream(file_path);
            InputStream in = con.getInputStream();
            //创建缓冲区
            byte[] buff = new byte[4096];
            int n;
            // 开始读取
            while ((n = in.read(buff)) >= 0) {
                out.write(buff, 0, n);
            }

            out.close();
            in.close();
            return savePath+File.separator+filename;
        } catch (Exception e) {
            e.printStackTrace();
        }

      return null;
    }

    public static void main(String[] args) throws Exception {

        /**
         * https://www.jnnews.tv//a/10001/201803/8da090df9a43971ceaac7927b72be820.png
         *
         *   /Users/xxm/Desktop/update/111/zhaosheng.xlsx
         * */
      String temp_path = download("http://sd5ofu5s8.hd-bkt.clouddn.com/zhaosheng.xlsx", "/Users/xxm/Desktop/update/111", "zhaosheng.xlsx");

        System.out.println(temp_path);



    }
}

