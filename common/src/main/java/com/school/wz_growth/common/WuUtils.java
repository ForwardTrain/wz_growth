package com.school.wz_growth.common;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.ObjectUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class WuUtils {

    /**
     * 判断不为空
     * @return
     */
    public static boolean if_not_null(Object o){
        if(o!=null&&!o.toString().equals(""))
            return true;
        return false;
    }


    /**
     * json数组对象转换list
     * @return
     */
    public static List<JSONObject> jsonString2list(Object o){
        return JSONObject.parseArray(JSONObject.toJSONString(o)).toJavaList(JSONObject.class);
    }



    /**
     * 判断为空
     * @return
     */
    public static boolean ifNull(Object o){
        if(o==null||o.toString().equals(""))
            return true;
        return false;
    }


    /**
     * file转base64
     *
     */

    public static  String fileToBase64(File file) throws IOException {
        String strBase64 = null;
        try {
            InputStream in = new FileInputStream(file);
            // in.available()返回文件的字节长度
            byte[] bytes = new byte[in.available()];
            // 将文件中的内容读入到数组中
            in.read(bytes);
            strBase64 = encode(bytes); // 将字节流数组转换为字符串
            in.close();
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return strBase64;
    }

    /**
     * base64转file
     * @param strBase64
     * @param toFile
     * @throws IOException
     */
    public static  void base64ToFile(String strBase64, File toFile)
            throws IOException {
        String string = strBase64;
        ByteArrayInputStream in = null;
        FileOutputStream out = null;
        try {
            // 解码，然后将字节转换为文件
            byte[] bytes = decode(string.trim());
            // 将字符串转换为byte数组
            in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            out = new FileOutputStream(toFile);
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread); // 文件写操作
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (out != null)
                out.close();
            if (in != null)
                in.close();
        }
    }

    /**
     * 编码
     *
     * @param bstr
     * @return String
     */
    public static  String encode(byte[] bstr) {
        if (bstr == null)
            return null;
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bstr);
    }

    /**
     * 解码
     *
     * @param str
     * @return string
     * @throws IOException
     */
    public static  byte[] decode(String str) throws IOException {
        if (str == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(str);
    }


//
//    /**
//     * 压缩base64编码至nM以内
//     * @param base64Img
//     * @param num 压缩到多大
//     * @return
//     */
//    public static  String resizeImageToNM(String base64Img,double num) {
//        try {
//            double i=0;
//            if (base64Img.length()<num*1024*1024)
//                return base64Img;
//            i=Math.sqrt(base64Img.length()/num*1024*1024);
//            BASE64Decoder decoder = new BASE64Decoder();
//            byte[] bytes1 = decoder.decodeBuffer(base64Img);
//            InputStream stream = new ByteArrayInputStream(bytes1);
//            BufferedImage src = ImageIO.read(stream);
//            BufferedImage output = Thumbnails.of(src).size((int)(src.getWidth()/i) , (int)(src.getHeight()/i) ).asBufferedImage();
//            String base64 = imageToBase64(output);
//            if (base64.length() - base64.length() / 8 * 2 > 4*1024*1024) {
//                output = Thumbnails.of(output).scale(1 / (base64.length() / 4*1024*1024)).asBufferedImage();
//            }else
//                base64 = imageToBase64(output);
//            return base64;
//        } catch (Exception e) {
//            return base64Img;
//        }
//    }


    /**
     * BufferedImage转换成base64，在这里需要设置图片格式,因为我需要jpg格式就设置了jpg
     */
    public static String imageToBase64(BufferedImage bufferedImage) {
        Base64 encoder = new Base64();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", baos);
        } catch (IOException e) {
        }
        return new String(encoder.encode((baos.toByteArray())));
    }

    /**
     * 判断是否是数字
     */
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
    public static boolean isNumeric(String str) {
        return str != null && NUMBER_PATTERN.matcher(str).matches();
    }

    /**
     * 1加2减3乘4除5左括号6右括号
     * @return
     */
    public static List<JSONObject> computeSign(){
        return new ArrayList<JSONObject>(){{
            add(new JSONObject(){{
                put("id",1);
                put("name","+");
            }}) ;
            add(new JSONObject(){{
                put("id",2);
                put("name","-");
            }}) ;
            add(new JSONObject(){{
                put("id",3);
                put("name","×");
            }}) ;
            add(new JSONObject(){{
                put("id",4);
                put("name","÷");
            }}) ;
            add(new JSONObject(){{
                put("id",5);
                put("name","(");
            }}) ;
            add(new JSONObject(){{
                put("id",6);
                put("name",")");
            }}) ;
        }};
    }

    /**
     * 1>2>=3=4<=5<
     * @return
     */
    public static List<JSONObject> compairSign(){
        return new ArrayList<JSONObject>(){{
            add(new JSONObject(){{
                put("id",1);
                put("name",">");
            }}) ;
            add(new JSONObject(){{
                put("id",2);
                put("name",">=");
            }}) ;
            add(new JSONObject(){{
                put("id",3);
                put("name","=");
            }}) ;
            add(new JSONObject(){{
                put("id",4);
                put("name","<=");
            }}) ;
            add(new JSONObject(){{
                put("id",5);
                put("name","<");
            }}) ;
        }};
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        OutputStreamWriter out = null;

        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type","application/json;charset:utf-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(param);
            out.flush();
//            Map<String, List<String>> headers = conn.getHeaderFields();
//            // 打印所有响应头
//            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
//                String headerName = entry.getKey();
//                List<String> headerValues = entry.getValue();
//                if (headerName .equals("Link")) {
//                    for (String headerValue : headerValues) {
//                        if (headerValue.contains("cursor=")){
//                            String cursor=headerValue.split("cursor=")[1].split("&")[0];
//                            result += "cursor:" + cursor;
//                            break;
//                        }
//                    }
//                }
//            }

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static String sendPost1(String url, String param) {
        OutputStreamWriter out = null;

        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type","application/json;charset:utf-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(param);
            out.flush();
            Map<String, List<String>> headers = conn.getHeaderFields();
            // 打印所有响应头
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String headerName = entry.getKey();
                List<String> headerValues = entry.getValue();
                if (ObjectUtils.getDisplayString(headerName) .equals("Link")) {
                    for (String headerValue : headerValues) {
                        if (headerValue.contains("cursor=")){
                            String cursor=headerValue.split("cursor=")[1].split("&")[0];
                            result += "<cursor>:" + cursor+"</cursor>";
                            break;
                        }
                    }
                }
            }

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static String sendPost_gbk(String url, String param) {
        OutputStreamWriter out = null;

        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type","application/json;charset:utf-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(param);
            out.flush();


            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }






}
