package com.school.wz_growth.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.jthinking.common.util.ip.IPInfo;
import com.jthinking.common.util.ip.IPInfoUtils;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.qiniu.QiniuUpload;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.DateUtils;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.BannerMapper;
import com.school.wz_growth.dao.HomeBasicMapper;
import com.school.wz_growth.service.HomeBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class HomeBasicServiceImpl implements HomeBasicService {

    @Autowired
    private HomeBasicMapper homeBasicMapper;

    @Autowired
    private BannerMapper bannerMapper;


    @Override
    public SingleResult<DataResponse> select_info(HttpServletRequest request, JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        try {

            String ipAddress = request.getRemoteAddr(); //"124.90.244.28";//
            System.out.println("客户端IP地址为: " + ipAddress);

            IPInfo ipInfo = IPInfoUtils.getIpInfo(ipAddress);
//            System.out.println(ipInfo.getCountry()); // 国家中文名称
//            System.out.println(ipInfo.getProvince()); // 中国省份中文名称
//            System.out.println(ipInfo.getAddress()); // 详细地址
//            System.out.println(ipInfo.getIsp()); // 互联网服务提供商
//            System.out.println(ipInfo.isOverseas()); // 是否是国外
//            System.out.println(ipInfo.getLat()); // 纬度
//            System.out.println(ipInfo.getLng()); // 经度

            params.put("app_name",requestVo.getString("app_name"));
            params.put("app_version",requestVo.getString("app_version"));

            params.put("country",ipInfo.getCountry());
            params.put("lat",ipInfo.getLat());
            params.put("lng",ipInfo.getLng());
            params.put("is_overseas",ipInfo.isOverseas());
            params.put("province",ipInfo.getProvince());

            params.put("address",ipInfo.getAddress());
            params.put("isp",ipInfo.getIsp());
            params.put("ip",ipAddress);
            params.put("c_s_date", DateUtils.getCurrDate());

           if (homeBasicMapper.sel_access_ip(params) == null)
               homeBasicMapper.insert_access_ip(params);

            homeBasicMapper.insert_access_ip_history(params);

           return SingleResult.buildSuccess(params);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_ip_status(HttpServletRequest request, JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        try {
            String ipAddress =request.getRemoteAddr();
            params.put("ip",ipAddress);
            return SingleResult.buildSuccess(homeBasicMapper.sel_access_ip(params));
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_home_info(HttpServletRequest request, JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        try {
//            String ipAddress =request.getRemoteAddr();
//            params.put("ip",ipAddress);

            Map<String, Object> selHomeInfo = homeBasicMapper.sel_home_info(params);
            if (StringUnits.isNotNullOrEmpty(selHomeInfo.get("last_update_day")))
            {
                String lastUpdateDay = (String) selHomeInfo.get("last_update_day");
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                // 解析日期字符串为 LocalDate 对象
                LocalDate date = LocalDate.parse(lastUpdateDay, inputFormatter);

                // 定义输出日期格式
                DateTimeFormatter outputFormatter = new DateTimeFormatterBuilder()
                        .appendPattern("MMM ") // 月份缩写
                        .appendPattern("yyyy") // 年份
                        .toFormatter(Locale.ENGLISH); // 设置语言环境为英文，确保月份是英文缩写

                // 格式化日期为指定格式
                String formattedDate = date.format(outputFormatter);
                selHomeInfo.put("since", formattedDate);
            }else {
                selHomeInfo.put("since", "");
            }

            res.put("count", selHomeInfo);
//            res.put("contact_us", homeBasicMapper.sel_contact_us_info(params));
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_contact_us(HttpServletRequest request, JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
//        Map<String, Object> res = new HashMap<>();
        try {
//            String ipAddress =request.getRemoteAddr();
//            params.put("ip",ipAddress);
//            res.put("contact_us", );
            return SingleResult.buildSuccess(homeBasicMapper.sel_contact_us_info(params));
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_view_recode(HttpServletRequest request, JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        try {
            String ipAddress =request.getRemoteAddr();
            params.put("path",requestVo.get("path"));
            params.put("ip",ipAddress);
            params.put("c_s_date", DateUtils.getCurrDate());
            homeBasicMapper.insert_view_recode(params);

            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }




    @Override

    public SingleResult<DataResponse> banner_sel_detail(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject vo = new JSONObject();

            JSONObject result = bannerMapper.banner_sel_detail(vo);
            vo.put("id", result.get("id"));
            List<JSONObject> l= bannerMapper.banner_sel_detail_d(vo);
            for (JSONObject jsonObject : l) {
                if (!ObjectUtils.isEmpty(jsonObject.get("path")))
                    jsonObject.put("path", QiniuUpload.qiniu_base_url+jsonObject.getString("path"));
            }
            result.put("details",l );
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return SingleResult.buildFailure(Code.ERROR, t.getMessage());
        }
    }


    @Override
    public SingleResult<DataResponse> sel_home_protein_num_count(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject res = new JSONObject();

            List<Map<String,Object>> res_list = bannerMapper.sel_home_protein_num_count();
            res.put("total", res_list.size());
            res.put("list",res_list);
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return SingleResult.buildFailure(Code.ERROR, t.getMessage());
        }
    }



    @Override
    public SingleResult<DataResponse> sel_home_family_count(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject res = new JSONObject();

            JSONObject params = new JSONObject();
            params.put("qiniu_base_url", QiniuUpload.qiniu_base_url);
            res.put("bg", bannerMapper.sel_home_family_count_bg(params));

            List<Map<String,Object>> res_list = bannerMapper.sel_home_family_count();
            res.put("total", res_list.size());
            res.put("list",res_list);
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return SingleResult.buildFailure(Code.ERROR, t.getMessage());
        }
    }


    @Override
    public void scheduled_task() {

//        try {
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
