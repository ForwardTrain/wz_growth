package com.school.wz_growth.common.validator;



import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.qiniu.QiniuUpload;
import com.school.wz_growth.common.response.Code;

import java.io.IOException;

public class FileUploadUtils {


    /***
     *  新闻内容上传 分割图片，上传到七牛
     */
    public static String parseNewsDataMarket(String account_id,String source_str) throws ServiceException, IOException,Exception {

        if (StringUnits.isNotNullOrEmpty(source_str) == false)
            return source_str;

        StringBuilder res_sb = new StringBuilder();

        String[] data_source_array = source_str.split("<img");
        for (String sub_temp_source_str : data_source_array)
        {
            if (sub_temp_source_str.startsWith(" src="))
            {

                String[] sub_str_temp_array = sub_temp_source_str.split(">");//取到每次的结尾
                String[] img_data_array = sub_str_temp_array[0].split("base64,");//取数据
                if (img_data_array.length==1)//img标签里如果没有base64，说明是已经转换的url
                {
                    String img_market_str = String.format("<img%s",sub_temp_source_str);
                    res_sb.append(img_market_str);
                }else
                {

                    StringBuilder img_data_base64_sb = new StringBuilder();
                    String[] img_data_base64_array = String.format("%s ",img_data_array[1]).split("\"");

                    for (int k = 0; k < img_data_base64_array.length-1; k++)
                    {
                        img_data_base64_sb.append(img_data_base64_array[k]);
                    }

//                    String img_data_base64_str = img_data_array[1];老的写法

                    String img_data_base64_str = img_data_base64_sb.toString();
                    if (StringUnits.isNotNullOrEmpty(img_data_base64_str) == false)
                        throw new ServiceException("数据为空，出错", Code.ERROR.getStatus());

                    String file_name_str = String.format("leqin_news_%s_%s_%s",account_id,DateUtils.getCurrentTime(),RandomUtils.CAPTCHA(6));
                    boolean upload_res = new QiniuUpload().putBase64image(img_data_base64_str,file_name_str);

                    if (upload_res==false)
                        throw new ServiceException("图片上传出错，请检查",Code.ERROR.getStatus());

                    String img_market_str = String.format("<img src=\"%s/%s\" \\>",QiniuUpload.qiniu_base_url,file_name_str);
                    res_sb.append(img_market_str);

                    for (int i = 1; i < sub_str_temp_array.length; i++)
                    {
                        //一图 二文 三图 出问题
                     // String temp_sub_str = sub_str_temp_array[i]+(i<(sub_str_temp_array.length-1)?"\">":"");
                        String temp_sub_str = "";
                        if ((i==sub_str_temp_array.length-1) && sub_str_temp_array[i].endsWith(">"))

                            temp_sub_str=sub_str_temp_array[i];
                        else
                            temp_sub_str = sub_str_temp_array[i]+">";


                        res_sb.append(temp_sub_str);
                    }
                }
            }else {
                res_sb.append(sub_temp_source_str);
            }
        }
        System.out.println(res_sb);
        return res_sb.toString();

    }

   public static void main(String[] args) {

        StringBuilder source_sb = new StringBuilder();
        source_sb.append("<div modelvalue=\"\" id=\"quillEditor\" class=\"ql-container ql-snow\" style=\"min-height: 330px;\"><div class=\"ql-editor\" data-gramm=\"false\" contenteditable=\"true\" data-placeholder=\"请输入正文\"><p>夺夺城</p><p><br></p><p><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA8AAAAPCAYAAAA71pVKAAAAAXNSR0IArs4c6QAAAOlJREFUOE+lk8FKQkEUhr//ehctXYStewQhREpNaJH7IALrXXyXCCJoraViRUlE4CO0Ll24DPTeI3PDuMQ1spnVYYbvP3P++Ueklg3qIbPoBNFE2gHywBSzV4wLhrlLte7mS0TLwm5q2+S4BoppwR/1iIgjHT68uf0ETsDAnpEKv4BfR2YfxCo7AVmrHlKJXzI77l3B03GW3ojHoCS7rZ4S6Dyz48E99PezLxPbmaxbbSM11obNOrJebQxsrg3DxMEzIExgN+PG1mrPPt/THsw9O3vN7OW2i2T0z3f2Sph3tr8FXFR3//6rFi/+f1IAY6oPAAAAAElFTkSuQmCC\">柘城地</p></div><div class=\"ql-clipboard\" contenteditable=\"true\" tabindex=\"-1\"></div><div class=\"ql-tooltip ql-hidden\"><a class=\"ql-preview\" rel=\"noopener noreferrer\" target=\"_blank\" href=\"about:blank\"></a><input type=\"text\" data-formula=\"e=mc^2\" data-link=\"https://quilljs.com\" data-video=\"Embed URL\"><a class=\"ql-action\"></a><a class=\"ql-remove\"></a></div></div>");


        try {
            String res_str = FileUploadUtils.parseNewsDataMarket("102",source_sb.toString());

            System.out.println(res_str);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }


//       QiniuUpload qiniuUpload = new  QiniuUpload();
//       try {
//           qiniuUpload.putBase64image("iVBORw0KGgoAAAANSUhEUgAAAA8AAAAPCAYAAAA71pVKAAAAAXNSR0IArs4c6QAAAOlJREFUOE+lk8FKQkEUhr//ehctXYStewQhREpNaJH7IALrXXyXCCJoraViRUlE4CO0Ll24DPTeI3PDuMQ1spnVYYbvP3P++Ueklg3qIbPoBNFE2gHywBSzV4wLhrlLte7mS0TLwm5q2+S4BoppwR/1iIgjHT68uf0ETsDAnpEKv4BfR2YfxCo7AVmrHlKJXzI77l3B03GW3ojHoCS7rZ4S6Dyz48E99PezLxPbmaxbbSM11obNOrJebQxsrg3DxMEzIExgN+PG1mrPPt/THsw9O3vN7OW2i2T0z3f2Sph3tr8FXFR3//6rFi/+f1IAY6oPAAAAAElFTkSuQmCC");
//       } catch (Exception e) {
//           e.printStackTrace();
//       }

   }


}


/**
 <img src="http://healife.dlzhulin.com/leqin_news_102_2023-01-06_5wwyFI" \>

 <div><p>夺夺城</p><p><br></p><p>
 <img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA8AAAAPCAYAAAA71pVKAAAAAXNSR0IArs4c6QAAAOlJREFUOE+lk8FKQkEUhr//ehctXYStewQhREpNaJH7IALrXXyXCCJoraViRUlE4CO0Ll24DPTeI3PDuMQ1spnVYYbvP3P++Ueklg3qIbPoBNFE2gHywBSzV4wLhrlLte7mS0TLwm5q2+S4BoppwR/1iIgjHT68uf0ETsDAnpEKv4BfR2YfxCo7AVmrHlKJXzI77l3B03GW3ojHoCS7rZ4S6Dyz48E99PezLxPbmaxbbSM11obNOrJebQxsrg3DxMEzIExgN+PG1mrPPt/THsw9O3vN7OW2i2T0z3f2Sph3tr8FXFR3//6rFi/+f1IAY6oPAAAAA
 ElFTkSuQmCC\">柘城地</p>
 </div><div class=\"ql-clipboard\" contenteditable=\"true\" tabindex=\"-1\"></div><div class=\"ql-tooltip ql-hidden\"><a class=\"ql-preview\" rel=\"noopener noreferrer\" target=\"_blank\" href=\"about:blank\"></a><input type=\"text\" data-formula=\"e=mc^2\" data-link=\"https://quilljs.com\" data-video=\"Embed URL\"><a class=\"ql-action\"></a><a class=\"ql-remove\"></a></div></div>


 */






