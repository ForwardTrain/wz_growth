package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.service.ContentService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


/**
 * 前端内容
 */
@RequestMapping("/HomeContent")
@RestController
public class HomeContentCtrl extends BaseController {

    @Autowired
    private ContentService service;

    /** 内容列表 */
    @RequestMapping("/sel_list")
    @ResponseBody
    public SingleResult sel_list(@RequestBody JSONObject requestVo)throws ServiceException {
        if(requestVo.get("pageSize")==null||requestVo.getString("pageSize").equals("")) {
            requestVo.put("limit", 20);
            requestVo.put("start", 0);
        }
        else {
            requestVo.put("limit", Integer.parseInt(String.format("%s",requestVo.get("pageSize"))));
            requestVo.put("start",
                    Integer.parseInt(String.format("%s",requestVo.getInteger("pageSize")*(requestVo.getInteger("pageIndex")-1))));
        }
        return service.sel_list( requestVo);
    }

    /** 内容列表 --详情*/
    @RequestMapping("/sel_list_details")
    @ResponseBody
    public SingleResult sel_list_details(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.sel_list_details( requestVo);
    }





    /** 内容列表 --推荐*/
    @RequestMapping("/sel_hot_list")
    @ResponseBody
    public SingleResult sel_hot_list(@RequestBody JSONObject requestVo)throws ServiceException {
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



    /** 反馈建议列表 */
    @RequestMapping("/sel_support_list")
    @ResponseBody
    public SingleResult sel_support_list(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.sel_support_list( requestVo);
    }

    /** 反馈建议列表详情 */
    @RequestMapping("/sel_support_list_details")
    @ResponseBody
    public SingleResult sel_support_list_details(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.sel_support_list_details( requestVo);
    }

    /** 反馈建议列表--新增 */
    @RequestMapping("/add_support")
    @ResponseBody
    public SingleResult add_upd_support(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.add_upd_support( requestVo);
    }

    /** 前端搜索 - 左侧搜索栏 */
    @RequestMapping("/search_combobox")
    @ResponseBody
    public SingleResult search_combobox(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.search_combobox( requestVo);
    }

    /** 前端搜索 - 高级搜索 */
    @RequestMapping("/search_combobox2")
    @ResponseBody
    public SingleResult search_combobox2(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.search_combobox2( requestVo);
    }

    /** 前端搜索 - 左侧搜索栏数量统计
     *   http://127.0.0.1:8082/WZGrowth/HomeContent/search_combobox_type_num_count
     * */
    @RequestMapping("/search_combobox_type_num_count")
    @ResponseBody
    public SingleResult search_combobox_type_num_count()throws ServiceException {
        return service.search_combobox_type_num_count();
    }


    /** 前端搜索列表 搜索栏下拉框这里搜搜*/
    @RequestMapping("/search_result_list")
    @ResponseBody
    public SingleResult search_result_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParamsPageSize(requestVo);
        return service.search_result_list( requestVo);
    }

    /**
     * 导出搜索结果为Excel文件
     */
    @RequestMapping("/export_search_result_fast")
    @ResponseBody
    public void export_search_result_excel(@RequestBody JSONObject requestVo, HttpServletResponse response) throws ServiceException, IOException {
        // 获取搜索结果数据
        SingleResult result = service.search_result_list_byId(requestVo);

        // 设置响应头，指定文件类型和下载文件名
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        long time = System.currentTimeMillis();
        String s = "search_results_" + time + ".fasta";
        response.setHeader("Content-Disposition", "attachment; filename=" + s);

        // 获取输出流
        PrintWriter writer = response.getWriter();

        // 从结果中提取数据列表
        if (result != null && result.getCode() == 1 && result.getData() != null) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.getData();

            // 遍历数据，按FASTA格式写入文件
            for (Map<String, Object> item : dataList) {
                String uniproEntry = item.get("unipro_entry") != null ? item.get("unipro_entry").toString() : "";
                String proteinName = item.get("protein_name") != null ? item.get("protein_name").toString() : "";
                String organism = item.get("organism") != null ? item.get("organism").toString() : "";
                String completeForm = item.get("complete_form") != null ? item.get("complete_form").toString() : "";
                String DRGF_code = item.get("DRGF_code") != null ? item.get("DRGF_code").toString() : "";

                // 写入FASTA格式的头部信息
                writer.println(">sp|" + DRGF_code + "| " + proteinName + " OS=" + organism);
                // 写入序列信息
                writer.println(completeForm);
            }
        }

        writer.flush();
        writer.close();
    }

//    @RequestMapping("/test")
//    @ResponseBody
//    public String test(JSONObject requestVo)throws ServiceException {
////        super.putAppUserTokenParamsPageSize(requestVo);
////        return service.search_result_list( requestVo);
//        return "成功";
//    }

//    /** 前端搜索列表 左侧树状这里搜搜*/
//    @RequestMapping("/left_search_result_list")
//    @ResponseBody
//    public SingleResult left_search_result_list(@RequestBody JSONObject requestVo)throws ServiceException {
//        if(requestVo.get("pageSize")==null||requestVo.getString("pageSize").equals("")) {
//            requestVo.put("limit", 20);
//            requestVo.put("start", 0);
//        }
//        else {
//            requestVo.put("limit", Integer.parseInt(String.format("%s",requestVo.get("pageSize"))));
//            requestVo.put("start",
//                    Integer.parseInt(String.format("%s",requestVo.getInteger("pageSize")*(requestVo.getInteger("pageIndex")-1))));
//        }
//        return service.left_search_result_list( requestVo);
//    }

    /** 前端搜索列表 根据关键字搜索*/
    @RequestMapping("/sel_by_key_words_search_result_list")
    @ResponseBody
    public SingleResult sel_by_key_words_search_result_list(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.sel_by_key_words_search_result_list( requestVo);
    }


    /** 前端搜搜  关键字*/
    @RequestMapping("/sel_key_words")
    @ResponseBody
    public SingleResult sel_key_words(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.sel_key_words( requestVo);
    }


    /** 前端搜搜  关键字example*/
    @RequestMapping("/sel_key_words_example")
    @ResponseBody
    public SingleResult sel_key_words_example(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.sel_key_words_example( requestVo);
    }

    /** 查询下拉框 example*/
    @RequestMapping("/query_example_data")
    @ResponseBody
    public SingleResult query_example_data(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.query_example_data( requestVo);
    }

    /** 添加 example*/
    @RequestMapping("/add_search_example_data")
    @ResponseBody
    public SingleResult add_example_data(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.add_example_data( requestVo);
    }

    /** 反显 example*/
    @RequestMapping("/query_search_example_data")
    @ResponseBody
    public SingleResult query_search_example_data(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.query_search_example_data();
    }

    /** 删除 example*/
    @RequestMapping("/del_search_example_data")
    @ResponseBody
    public SingleResult del_search_example_data(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.del_search_example_data(requestVo);
    }

    /** 查蛋白数据 example*/
    @RequestMapping("/query_protein_data")
    @ResponseBody
    public SingleResult query_protein_data(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.query_protein_data( requestVo);
    }


    /** 前端搜搜  browse
     * */
    @RequestMapping("/sel_browse")
    @ResponseBody
    public SingleResult sel_browse(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.sel_browse( requestVo);
    }

    /** 前端搜搜  数据库简介
     * */
    @RequestMapping("/sel_databases_brief")
    @ResponseBody
    public SingleResult sel_databases_brief(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.sel_databases_brief( requestVo);
    }

    /**  合作机构 - 列表 */
    @RequestMapping("/sys_cooperate_manage_list")
    public SingleResult sys_cooperate_manage_list( @RequestBody JSONObject requestVo)throws ServiceException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        return service.sys_cooperate_manage_list(requestVo);
    }

    /**  data statistics information */
    @RequestMapping("/data_statistics_information")
    public SingleResult data_statistics_information( @RequestBody JSONObject requestVo)throws ServiceException {
        return service.data_statistics_information(requestVo);
    }


    /**  clinical_research */
    @RequestMapping("/clinical_research")
    public SingleResult clinical_research( @RequestBody JSONObject requestVo)throws ServiceException {
        return service.clinical_research(requestVo);
    }
    /** 栏目列表 */
    @RequestMapping("/sel_item_list")
    @ResponseBody
    public SingleResult sel_item_list(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.sel_item_list( requestVo);
    }

    /** 搜索详情 */
    @RequestMapping("/sel_search_details")
    @ResponseBody
    public SingleResult sel_search_details(@RequestBody JSONObject requestVo)throws ServiceException {
//        return service.sel_search_details( requestVo);
        return service.sel_search_details_show( requestVo);
    }



//
//
//    /** 抓取搜索条件 */
//    @RequestMapping("/down_search_data")
//    @ResponseBody
//    public void down_search_data(@RequestBody JSONObject requestVo)throws ServiceException {
//        String result= WuUtils.sendPost("https://rest.uniprot.org/uniprotkb/search?facets=reviewed%2Cmodel_organism%2Cproteins_with%2Cexistence%2Cannotation_score%2Clength&query=%28*%29+AND+%28proteins_with%3A24%29+AND+%28proteins_with%3A14%29&size=0","" );
//        List<JSONObject> facets=JSONObject.parseObject(result).getJSONArray("facets").toJavaList(JSONObject.class);
//        for (JSONObject facet : facets) {
//            JSONObject v1=new JSONObject();
//            v1.put("name",facet.getString("label"));
//            service.add_down_first(v1);
//            List<JSONObject> l=facet.getJSONArray("values").toJavaList(JSONObject.class);
//            for (JSONObject jsonObject : l) {
//                jsonObject.put("p_id",v1.getString("id"));
//            }
//            service.add_down_second(l);
//        }
//    }

    @RequestMapping("/run")
    public String run(HttpServletRequest request) throws ServiceException {
        try {
            // 获取表单参数
            Map<String, String[]> formParams = request.getParameterMap();

            // 构建POST请求的URL
            URL url = new URL("https://www.ebi.ac.uk/Tools/services/rest/ncbiblast/run");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // 设置Content-Type为form-data
            String boundary = "----" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            // 构建请求体
            try (OutputStream os = conn.getOutputStream();
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true)) {

                // 添加表单参数
                for (Map.Entry<String, String[]> entry : formParams.entrySet()) {
                    String key = entry.getKey();
                    String[] values = entry.getValue();
                    if (values != null && values.length > 0) {
                        writer.append("--" + boundary).append("\r\n");
                        writer.append("Content-Disposition: form-data; name=\"" + key + "\"").append("\r\n");
                        writer.append("\r\n").append(values[0]).append("\r\n");
                    }
                }

                // 如果序列数据是从请求体读取的
                if (request.getParameter("sequence") == null) {
                    BufferedReader reader = request.getReader();
                    StringBuilder sequence = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sequence.append(line).append("\n");
                    }

                    if (sequence.length() > 0) {
                        writer.append("--" + boundary).append("\r\n");
                        writer.append("Content-Disposition: form-data; name=\"sequence\"").append("\r\n");
                        writer.append("\r\n").append(sequence.toString()).append("\r\n");
                    }
                }

                // 结束边界
                writer.append("--" + boundary + "--").append("\r\n");
                writer.flush();
            }

            // 获取响应
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                return response.toString();
            } else {
                // 如果请求失败，获取错误信息
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;

                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();

                return errorResponse.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("调用NCBI BLAST服务失败: " + e.getMessage(), Code.ERROR.getStatus());
        }
    }


    @RequestMapping("/runAli")
    public String runAli(HttpServletRequest request) throws ServiceException {
        try {
            // 获取表单参数
            Map<String, String[]> formParams = request.getParameterMap();

            // 构建POST请求的URL
            URL url = new URL("https://www.ebi.ac.uk/Tools/services/rest/clustalo/run");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // 设置Content-Type为form-data
            String boundary = "----" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            // 构建请求体
            try (OutputStream os = conn.getOutputStream();
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true)) {

                // 添加表单参数
                for (Map.Entry<String, String[]> entry : formParams.entrySet()) {
                    String key = entry.getKey();
                    String[] values = entry.getValue();
                    if (values != null && values.length > 0) {
                        writer.append("--" + boundary).append("\r\n");
                        writer.append("Content-Disposition: form-data; name=\"" + key + "\"").append("\r\n");
                        writer.append("\r\n").append(values[0]).append("\r\n");
                    }
                }

                // 如果序列数据是从请求体读取的
                if (request.getParameter("sequence") == null) {
                    BufferedReader reader = request.getReader();
                    StringBuilder sequence = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sequence.append(line).append("\n");
                    }

                    if (sequence.length() > 0) {
                        writer.append("--" + boundary).append("\r\n");
                        writer.append("Content-Disposition: form-data; name=\"sequence\"").append("\r\n");
                        writer.append("\r\n").append(sequence.toString()).append("\r\n");
                    }
                }

                // 结束边界
                writer.append("--" + boundary + "--").append("\r\n");
                writer.flush();
            }

            // 获取响应
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                return response.toString();
            } else {
                // 如果请求失败，获取错误信息
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;

                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();

                return errorResponse.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("调用NCBI BLAST服务失败: " + e.getMessage(), Code.ERROR.getStatus());
        }
    }

}
