package com.school.wz_growth.service.Impl;


import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.DrugInfoMapper;
import com.school.wz_growth.dao.model.DrugInfo;
import com.school.wz_growth.dao.model.DrugInfoQueryDTO;
import com.school.wz_growth.model.PageResult;
import com.school.wz_growth.service.DrugInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 药品信息服务实现类
 */
@Service
public class DrugInfoServiceImpl implements DrugInfoService {

    @Autowired
    private DrugInfoMapper drugInfoMapper;

    @Override
    @Transactional
    public int save(DrugInfo drugInfo) {
        return drugInfoMapper.insert(drugInfo);
    }

    @Override
    @Transactional
    public int update(DrugInfo drugInfo) {
        return drugInfoMapper.update(drugInfo);
    }

    @Override
    public DrugInfo getById(Integer id) {
        return drugInfoMapper.selectById(id);
    }

    @Override
    public PageResult<DrugInfo> page(DrugInfoQueryDTO queryDTO) {
        // 计算偏移量
        Integer offset = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();

        // 查询列表
        List<DrugInfo> list = drugInfoMapper.selectPage(queryDTO.getDrugName(), offset, queryDTO.getPageSize(), queryDTO.getSortOrder());
        if (StringUnits.isNotNullOrEmpty(queryDTO.getDrugName()))
        {
            String key_words = queryDTO.getDrugName();
//                String replase_str = String.format("<span style=\"background-color: yellow;\">%s</span>",key_words);
            for (DrugInfo drugInfo : list)
            {
                if (StringUnits.isNotNullOrEmpty(drugInfo.getDrugNameEn()))
                    drugInfo.setDrugNameEn(StringUnits.convertHighLight(key_words, drugInfo.getDrugNameEn()));

                if (StringUnits.isNotNullOrEmpty(drugInfo.getDrugNameCn()))
                    drugInfo.setDrugNameCn(StringUnits.convertHighLight(key_words, drugInfo.getDrugNameCn()));

                if (StringUnits.isNotNullOrEmpty(drugInfo.getApprovalNumber()))
                    drugInfo.setApprovalNumber(StringUnits.convertHighLight(key_words, drugInfo.getApprovalNumber()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getTradeName()))
                    drugInfo.setTradeName(StringUnits.convertHighLight(key_words, drugInfo.getTradeName()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getSpecifications()))
                    drugInfo.setSpecifications(StringUnits.convertHighLight(key_words, drugInfo.getSpecifications()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getDosageForm()))
                    drugInfo.setDosageForm(StringUnits.convertHighLight(key_words, drugInfo.getDosageForm()));

                if (StringUnits.isNotNullOrEmpty(drugInfo.getAtc()))
                    drugInfo.setAtc(StringUnits.convertHighLight(key_words, drugInfo.getAtc()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getTarget()))
                    drugInfo.setTarget(StringUnits.convertHighLight(key_words, drugInfo.getTarget()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getActiveIngredient()))
                    drugInfo.setActiveIngredient(StringUnits.convertHighLight(key_words, drugInfo.getActiveIngredient()));


                if (StringUnits.isNotNullOrEmpty(drugInfo.getApprovalNumber()))
                    drugInfo.setApprovalNumber(StringUnits.convertHighLight(key_words, drugInfo.getApprovalNumber()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getApprovalDate()))
                    drugInfo.setApprovalDate(StringUnits.convertHighLight(key_words, drugInfo.getApprovalDate()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getMarketingAuthorizationHolder()))
                    drugInfo.setMarketingAuthorizationHolder(StringUnits.convertHighLight(key_words, drugInfo.getMarketingAuthorizationHolder()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getAddressOfMarketingAuthorizationHolder()))
                    drugInfo.setAddressOfMarketingAuthorizationHolder(StringUnits.convertHighLight(key_words, drugInfo.getAddressOfMarketingAuthorizationHolder()));

                if (StringUnits.isNotNullOrEmpty(drugInfo.getManufacturer()))
                    drugInfo.setManufacturer(StringUnits.convertHighLight(key_words, drugInfo.getManufacturer()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getManufacturerAddress()))
                    drugInfo.setManufacturerAddress(StringUnits.convertHighLight(key_words, drugInfo.getManufacturerAddress()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getMedicalInsuranceClassification()))
                    drugInfo.setMedicalInsuranceClassification(StringUnits.convertHighLight(key_words, drugInfo.getMedicalInsuranceClassification()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getMedicalInsuranceNumber()))
                    drugInfo.setMedicalInsuranceNumber(StringUnits.convertHighLight(key_words, drugInfo.getMedicalInsuranceNumber()));

                if (StringUnits.isNotNullOrEmpty(drugInfo.getUsageScope()))
                    drugInfo.setUsageScope(StringUnits.convertHighLight(key_words, drugInfo.getUsageScope()));
                if (StringUnits.isNotNullOrEmpty(drugInfo.getOctPrescriptionDrug()))
                    drugInfo.setOctPrescriptionDrug(StringUnits.convertHighLight(key_words, drugInfo.getOctPrescriptionDrug()));

            }
//                    jsonObject.put("title", (StringUnits.isNotNullOrEmpty(jsonObject.get("title"))?(jsonObject.get("title").toString().replaceAll(key_words,replase_str)):""));
        }

        // 查询总数
        Long total = drugInfoMapper.selectCount(queryDTO.getDrugName());

        // 返回分页结果
        return new PageResult<>(list, total, queryDTO.getPageNum(), queryDTO.getPageSize());
    }
} 