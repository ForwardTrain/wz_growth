package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.service.ContentService;
import com.school.wz_growth.web.BaseController;
import com.school.wz_growth.web.util.HtmlDataExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * 内容
 */
@RequestMapping("/Content")
@RestController
public class ContentCtrl extends BaseController {

    @Autowired
    private ContentService service;

    /** 内容列表 */
    @RequestMapping("/sel_list")
    @ResponseBody
    public SingleResult sel_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.sel_list( requestVo);
    }
    /** 内容列表 --删除*/
    @RequestMapping("/del_list")
    @ResponseBody
    public SingleResult del_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.del_list( requestVo);
    }
    /** 内容列表 --详情*/
    @RequestMapping("/sel_list_details")
    @ResponseBody
    public SingleResult sel_list_details(@RequestBody JSONObject requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
        return service.sel_list_details( requestVo);
    }



    /** 内容列表 --新增 更新*/
    @RequestMapping("/add_upd_list")
    @ResponseBody
    public SingleResult add_upd_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.add_upd_list( requestVo);
    }


    /** 内容列表 --推荐*/
    @RequestMapping("/sel_hot_list")
    @ResponseBody
    public SingleResult sel_hot_list(@RequestBody JSONObject requestVo)throws ServiceException {
        //        super.putAppUserTokenParams(requestVo);
        if(requestVo.get("pageSize")==null||requestVo.getString("pageSize").equals("")) {
            requestVo.put("limit", 20);
            requestVo.put("start", 0);
        }
        else {
            requestVo.put("limit", Integer.parseInt(String.format("%s",requestVo.get("pageSize"))));
            requestVo.put("start",
                    Integer.parseInt(String.format("%s",requestVo.getInteger("pageSize")*(requestVo.getInteger("pageIndex")-1))));
        }
        return service.sel_hot_list( requestVo);
    }


    /** 内容列表 --推荐--置顶*/
    @RequestMapping("/upd_hot_list")
    @ResponseBody
    public SingleResult upd_hot_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.upd_hot_list( requestVo);
    }



    /** 反馈建议列表 */
    @RequestMapping("/sel_support_list")
    @ResponseBody
    public SingleResult sel_support_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParamsPageSize(requestVo);
        return service.sel_support_list( requestVo);
    }

    /** 反馈建议列表详情 */
    @RequestMapping("/sel_support_list_details")
    @ResponseBody
    public SingleResult sel_support_list_details(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParamsPageSize(requestVo);
        return service.sel_support_list_details( requestVo);
    }
    /** 反馈建议列表--删除 */
    @RequestMapping("/del_support_list")
    @ResponseBody
    public SingleResult del_support_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.del_support_list( requestVo);
    }
    /** 反馈建议列表--新增回复 */
    @RequestMapping("/add_upd_report")
    @ResponseBody
    public SingleResult add_upd_report(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.add_upd_report( requestVo);
    }


    /** 新增编辑example */
    @RequestMapping("/add_upd_example")
    @ResponseBody
    public SingleResult add_upd_example(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.add_upd_example( requestVo);
    }
    /** 新增编辑example */
    @RequestMapping("/del_example")
    @ResponseBody
    public SingleResult del_example(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.del_example( requestVo);
    }

    /** 查找example */
    @RequestMapping("/sel_example")
    @ResponseBody
    public SingleResult sel_example(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.sel_example( requestVo);
    }

//    /**
//     * 提取目标网站的数据
//     */
//    @RequestMapping("/extract_protein_data")
//    @ResponseBody
//    public SingleResult extractProteinData(@RequestBody JSONObject requestVo) throws ServiceException {
//        try {
//            // 从请求中获取蛋白质ID或其他参数
//            String proteinId = requestVo.getString("proteinId");
//
//            // 构建URL
//            String url = "https://www.proteinatlas.org/" + proteinId + "/tissue";
//
//            // 获取并提取数据
//            List<JSONArray> barChartDataList = HtmlDataExtractor.fetchAndExtractBarChartData(url, "rnaThumb1");
//
//            // 创建结果对象
//            JSONObject result = new JSONObject();
//            result.put("barChartData", barChartDataList);
//
//            return new SingleResult().success().addData("result", result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new SingleResult().fail(e.getMessage());
//        }
//    }

//    /**
//     * 从HTML中提取barChart数据
//     */
//    @RequestMapping("/extract_bar_chart")
//    @ResponseBody
//    public SingleResult extractBarChart(@RequestBody JSONObject requestVo) throws ServiceException {
//        try {
//            String url = requestVo.getString("url");
//            Document doc = Jsoup.connect(url).get();
//
//            // 查找指定ID的div元素
//            Element rnaThumbDiv = doc.getElementById("rnaThumb1");
//            if (rnaThumbDiv == null) {
//                return new SingleResult().fail("未找到id为'rnaThumb1'的元素");
//            }
//
//            // 在div中查找所有script标签
//            Elements scripts = rnaThumbDiv.getElementsByTag("script");
//            if (scripts.isEmpty()) {
//                return new SingleResult().fail("未找到script标签");
//            }
//
//            String scriptContent = scripts.first().html();
//
//            // 定义正则表达式匹配barChart([...])
//            Pattern pattern = Pattern.compile("\\$\\('#rnaThumb1'\\)\\.barChart\\(\\s*(\\[.*?\\])\\s*,\\s*\\{", Pattern.DOTALL);
//            Matcher matcher = pattern.matcher(scriptContent);
//
//            if (matcher.find()) {
//                String jsonArrayStr = matcher.group(1);
//                // 解析为JSONArray
//                JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
//
//                return new SingleResult().success().addData("barChartData", jsonArray);
//            } else {
//                return new SingleResult().fail("未找到barChart数据");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new SingleResult().fail(e.getMessage());
//        }
//    }

    public static void main(String[] args) throws IOException {
        // 构建URL
        String url = "https://www.proteinatlas.org/ENSG00000164399-IL3/tissue";

        // 获取并提取数据
        JSONArray rnaThumb1 = HtmlDataExtractor.extractFirstBarChartData(url, "rnaThumb1");
        System.out.println("rnaThumb1.toJSONString() = " + rnaThumb1.toJSONString());


    }


}
