package com.school.wz_growth.dao.model;

import lombok.Data;

/**
 * 药品信息实体类
 */
@Data
public class DrugInfo {
    private Integer id; // 编号

    // 基础信息
    private String drugNameCn; // 药品名称（中文）
    private String drugNameEn; // 药品名称（英文）
    private String tradeName; // 商品名称
    private String dosageForm; // 剂型
    private String specifications; // 规格
    private String target; // 靶点
    private String activeIngredient; // 活性成分
    private String atc;//ATC分类
    private String indications; // 适应症

    // 上市信息
    private String approvalNumber; // 批准文号
    private String approvalDate; // 批准日期
    private String marketingAuthorizationHolder; // 上市许可持有人
    private String addressOfMarketingAuthorizationHolder; // 上市许可持有人地址
    private String manufacturer; // 生产企业
    private String manufacturerAddress; // 生产企业地址
    private String listedCountry;//上市国家地区

    // 医保信息
    private String medicalInsuranceClassification; // 医保分类
    private String medicalInsuranceNumber; // 医保编号
    private String usageScope; // 医保限制使用范围
    private String octPrescriptionDrug; // OTC/处方药
} 