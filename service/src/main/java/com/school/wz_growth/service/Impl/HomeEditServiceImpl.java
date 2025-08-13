package com.school.wz_growth.service.Impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.qiniu.QiniuUpload;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.BaseGeneralInfomationShowMapper;
import com.school.wz_growth.model.domain.po.BaseGeneralInfomationShowPo;
import com.school.wz_growth.service.HomeEditService;
//import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeEditServiceImpl implements HomeEditService {

    @Autowired
    private BaseGeneralInfomationShowMapper mapper;


//    @Autowired
//    private BaseGeneralInfomationShowDao gen_info_dao;
//    @Autowired
//    private BaseProteinSequenceShowDao  seqDao;
//    @Autowired
//    private BaseProteinFunctionShowDao funDao;
//    @Autowired
//    private BaseExpressionAndLocationShowDao expDao;
//    @Autowired
//    private BaseExpressionAndLocationShowHpaDao hpaDao;
//    @Autowired
//    private  BaseProteinStructureShowDao strucDao;
//    @Autowired
//    private BaseProteinInteractionShowDao interDao;
//    @Autowired
//    private BaseMutationAndDiseaseShowDao mutDao;
//    @Autowired
//    private BaseDrgfShowDao drgfDao;
//    @Autowired
//    private BasePtmDetailsShowDao ptmDao;


    @Override
    public SingleResult<DataResponse> sel_search_details_show(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
            }};
            JSONObject result=new JSONObject();
            Map<String,Object> general_information=mapper.base_general_information_show2(params.getString("id"));
            if (StringUnits.isNotNullOrEmpty(general_information.get("gene_id")))
                general_information.put("gene_url","https://www.ncbi.nlm.nih.gov/gene/"+general_information.get("gene_id").toString());
            else
                general_information.put("gene_url","");

            if (StringUnits.isNotNullOrEmpty(general_information.get("organism"))) {
                String organism = general_information.get("organism").toString().replaceAll("\\(\\s*null\\s*\\)", "")
                        .replaceAll("\\((?=\\S)", " (");
                general_information.put("organism", organism);
            }

            //家族
            Map<String,Object> family_map = mapper.base_general_information_familys_show(general_information);
            general_information.put("family_id",(family_map!=null?family_map.get("id"):""));
            general_information.put("family_name",(family_map!=null?family_map.get("name"):""));
            result.put("general_information",general_information);

            Map<String,Object> protein_sequence=mapper.base_protein_sequence_show2(params.getString("id"));
            List<String> complete_list = new ArrayList<>();
            String complete_form_str ="";
            if (StringUnits.isNotNullOrEmpty(protein_sequence.get("complete_form")))
            {
                complete_form_str=protein_sequence.get("complete_form").toString();
//                int length = complete_form_str.length();
//                for (int i = 0; i < length; i += 10)
//                {
//                    // 计算截取的终点，避免超出字符串长度
//                    int end = Math.min(length, i + 10);
//                    // 截取子串并添加到结果列表中
//                    complete_list.add(complete_form_str.substring(i, end));
//                }
            }
//            protein_sequence.put("complete_list",complete_list);
            JSONArray protein_list=new JSONArray();
            if (StringUnits.isNotNullOrEmpty(complete_form_str)&&
                    StringUnits.isNotNullOrEmpty(protein_sequence.get("precursor")) &&
                    "/".equals(protein_sequence.get("precursor"))==false)
            {
                JSONArray temp_data_list= JSONArray.parseArray(protein_sequence.get("precursor").toString());
                for (int j = 0; j < temp_data_list.size(); j++)
                {
                    JSONObject jsonObject = temp_data_list.getJSONObject(j);
                    jsonObject.put("type","Propeptide");
                    jsonObject.put("description","Signal peptide");
                    List<String> temp_list = new ArrayList<>();
                    if (StringUnits.isNotNullOrEmpty(jsonObject.get("start")) &&
                            StringUnits.isNotNullOrEmpty(jsonObject.get("end")))
                    {
                        int temp_data_start = jsonObject.getInteger("start");
                        int temp_data_end = jsonObject.getInteger("end");
                        String data_form_str =complete_form_str.substring(temp_data_start-1,temp_data_end);
                        int length = data_form_str.length();
                        for (int i = 0; i < length; i += 10)
                        {
                            // 计算截取的终点，避免超出字符串长度
                            int end = Math.min(length, i + 10);
                            // 截取子串并添加到结果列表中
                            temp_list.add(data_form_str.substring(i, end));
                        }
                    }
                    jsonObject.put("list",temp_list);
                }
                protein_list.addAll(temp_data_list);
                protein_sequence.remove("precursor");
            }
            if (StringUnits.isNotNullOrEmpty(complete_form_str)  &&
                    StringUnits.isNotNullOrEmpty(protein_sequence.get("mature_form"))  &&
                    "/".equals(protein_sequence.get("mature_form"))==false)
            {
                JSONArray temp_data_list= JSONArray.parseArray(protein_sequence.get("mature_form").toString());
                for (int j = 0; j < temp_data_list.size(); j++)
                {
                    JSONObject jsonObject = temp_data_list.getJSONObject(j);
                    jsonObject.put("type","Mature form");
                    List<String> temp_list = new ArrayList<>();
                    if (StringUnits.isNotNullOrEmpty(jsonObject.get("start")) &&
                            StringUnits.isNotNullOrEmpty(jsonObject.get("end")))
                    {
                        int temp_data_start = jsonObject.getInteger("start");
                        int temp_data_end = jsonObject.getInteger("end");
                        String data_form_str =complete_form_str.substring(temp_data_start-1,temp_data_end);
                        int length = data_form_str.length();
                        for (int i = 0; i < length; i += 10)
                        {
                            // 计算截取的终点，避免超出字符串长度
                            int end = Math.min(length, i + 10);
                            // 截取子串并添加到结果列表中
                            temp_list.add(data_form_str.substring(i, end));
                        }
                    }
                    jsonObject.put("list",temp_list);
                }
                protein_list.addAll(temp_data_list);
                protein_sequence.remove("mature_form");
            }


            Map<String,Object> protein_function=mapper.base_protein_function2(params.getString("id"));
            if (StringUnits.isNotNullOrEmpty(protein_function.get("function")))
                protein_function.put("function",JSONArray.parseArray(protein_function.get("function").toString()));

            JSONArray lre=new JSONArray();
            List<String> lpf=new ArrayList<>();
            if(!ObjectUtils.isEmpty(protein_function)&&!ObjectUtils.isEmpty(protein_function.get("gene_ontology_biological_process")))
            {
                lre = JSONArray.parseArray( protein_function.get("gene_ontology_biological_process").toString());
                for (int i = 0; i < lre.size(); i++)
                {
                    JSONObject temp_lre_obj = lre.getJSONObject(i);
                    if (StringUnits.isNotNullOrEmpty(temp_lre_obj.get("goId")))
                    {
                        String go_id = temp_lre_obj.getString("goId").replace("GO:","");
                        temp_lre_obj.put("goId",go_id);
                        temp_lre_obj.put("goUrl","https://www.ebi.ac.uk/QuickGO/term/GO:"+go_id);
                    }
                }
            }

            protein_function.put("new_list",lre);
            protein_function.remove("gene_ontology_biological_process");
            result.put("protein_function",protein_function);


            params.put("qiniu_base_url", QiniuUpload.qiniu_base_url);

            Map<String,Object> expression_and_location_info = mapper.base_expression_and_location_show2(params);
//            b.
            if (StringUnits.isNotNullOrEmpty(expression_and_location_info.get("hpa_RNA_EXPRESSION_OVERVIEW")))
            {
                JSONArray temp_ex_loc_arr = JSONArray.parseArray(expression_and_location_info.get("hpa_RNA_EXPRESSION_OVERVIEW").toString());
                expression_and_location_info.put("hpa_RNA_EXPRESSION_OVERVIEW",temp_ex_loc_arr);
                double max_loc_value = 0;
                for (int i = 0; i < temp_ex_loc_arr.size(); i++)
                    max_loc_value = Math.max(max_loc_value,temp_ex_loc_arr.getJSONObject(i).getDoubleValue("value"));

                expression_and_location_info.put("rna_max_value",(temp_ex_loc_arr.size()>0?max_loc_value:null));
            }

            if (!ObjectUtils.isEmpty(expression_and_location_info)&&!ObjectUtils.isEmpty(expression_and_location_info.get("developmental_stage")))
                expression_and_location_info.put("developmental_stage",JSONArray.parseArray(expression_and_location_info.get("developmental_stage").toString()));
            if (!ObjectUtils.isEmpty(expression_and_location_info)&&!ObjectUtils.isEmpty(expression_and_location_info.get("induction")))
                expression_and_location_info.put("induction",JSONArray.parseArray(expression_and_location_info.get("induction").toString()));
            result.put("expression_and_location",expression_and_location_info);


            Map<String,Object> protein_structured=mapper.base_protein_structure_show2(params.getString("id"));
            if (!ObjectUtils.isEmpty(protein_structured)&&!ObjectUtils.isEmpty(protein_structured.get("PDB_ID")))
            {
                JSONArray temp_structured_list = JSONArray.parseArray(protein_structured.get("PDB_ID").toString());
                for (int i = 0; i < temp_structured_list.size(); i++)
                {
                    JSONObject temp_structured_obj = temp_structured_list.getJSONObject(i);
                    if (StringUnits.isNotNullOrEmpty(temp_structured_obj.get("source")) &&
                            "AlphaFoldDB".equals(temp_structured_obj.get("source")))
                        temp_structured_obj.put("source","AlphaFold");
                }
                protein_structured.put("structured_list",temp_structured_list);
                protein_structured.remove("PDB_ID");
            }

            result.put("protein_structure",protein_structured);

//            String complete_form = protein_sequence.get("complete_form").toString();
            Map<String,Object> family_and_domain=mapper.base_family_and_domain_show2(params.getString("id"));
            if(ObjectUtils.isEmpty(family_and_domain.get("domain")))
            {
                family_and_domain.put("domain","");
            }else{
                String dm=family_and_domain.get("domain").toString();
                List<JSONObject> dml=new ArrayList<>();
                String[] dm_arr=dm.split(";");
//                List<String> img_list = new ArrayList(){{
//                    add("/img-detail-01.png");
//                    add("/img-detail-02.png");
//                    add("/img-detail-03.png");
//                    add("/img-detail-04.png");
//                    add("/img-detail-05.png");
//                }};
                for (int k=0;k<dm_arr.length;k++)
                {
                    String s = dm_arr[k];
                    if (s.startsWith(" calcium-binding"))
                        continue;  //25年7月4号，过滤一下脏数据

                    JSONObject j=new JSONObject();
                    j.put("type","Domain");
                    String[] temp_s_array = s.split(",");

                    if (temp_s_array.length>0)
                    {
                        String postion_str = temp_s_array[0];
                        j.put("position",postion_str);
                        //兼容squence显业 478-767
                        String[] postion_arr = postion_str.split("-");
                        if (postion_arr.length!=2)
                            continue;
                        j.put("start",postion_arr[0]);
                        j.put("end",postion_arr[1]);
                        j.put("length",(Integer.parseInt(postion_arr[1])-Integer.parseInt(postion_arr[0])+1));
                    }
                    if (temp_s_array.length>1)
                    {
                        //兼容squence显业
                        j.put("egfLike", temp_s_array[1]);
                        j.put("description", temp_s_array[1]);
                    }
                    if (temp_s_array.length>2)
                        j.put("url",temp_s_array[2]);

//                    j.put("img",QiniuUpload.qiniu_base_url+(img_list.get(k%img_list.size())));

                    if (StringUnits.isNotNullOrEmpty(complete_form_str)&&
                            StringUnits.isNotNullOrEmpty(j.get("position")))
                    {
                        String[] start_end_arr =  j.getString("position").split("-");
                        if (start_end_arr.length ==2)
                        {
//                            String cf =  family_and_domain.get("complete_form").toString();
//                            j.put("start",s.split(",")[0]);
                            j.put("sequence",complete_form_str.substring(Integer.parseInt(start_end_arr[0])-1,Integer.parseInt(start_end_arr[1])));
                        }
                    }else {
                        j.put("sequence","");
                    }
                    dml.add(j);
                    //把domain数据放到sequence中来
                    protein_list.add(j);
                }
                family_and_domain.put("domain",dml);
            }
            result.put("family_and_domain",family_and_domain);


            //把domain数据放到sequence中来
            protein_sequence.put("list",protein_list);
            result.put("protein_sequence",protein_sequence);

            Map<String,Object> protein_interaction=mapper.base_protein_interaction_show2(params.getString("id"));
            if (!ObjectUtils.isEmpty(protein_interaction)&&!ObjectUtils.isEmpty(protein_interaction.get("protein_complex")))
                protein_interaction.put("protein_complex",JSONArray.parseArray(protein_interaction.get("protein_complex").toString()));
            result.put("protein_interaction",protein_interaction);

            Map<String,Object> kegg_pathways=mapper.base_drgf_show2(params.getString("id"));
            kegg_pathways.put("drgfCode", general_information.get("drgfCode"));
            if (!ObjectUtils.isEmpty(kegg_pathways)&&!ObjectUtils.isEmpty(kegg_pathways.get("pathway_data")))
                kegg_pathways.put("pathway_data",JSONArray.parseArray(kegg_pathways.get("pathway_data").toString()));
            result.put("kegg_pathways",kegg_pathways);

            result.put("post_translational_modification",mapper.base_ptm_details_show2(params.getString("id")));

            Map<String,Object> mutation_and_disease=mapper.base_mutation_and_disease_show2(params.getString("id"));
            if (!ObjectUtils.isEmpty(mutation_and_disease)&&!ObjectUtils.isEmpty(mutation_and_disease.get("disease_json")))
            {
                JSONArray temp_data_jsonArray = JSONArray.parseArray(mutation_and_disease.get("disease_json").toString());
                for (int i = 0; i < temp_data_jsonArray.size(); i++)
                {
                    JSONObject temp_data_jsonObj = temp_data_jsonArray.getJSONObject(i);
                    //ids的数据集转字符串
                    if (StringUnits.isNotNullOrEmpty(temp_data_jsonObj.get("featureCrossReferences")))
                    {
//                        String description_str = StringUnits.isNotNullOrEmpty(temp_data_jsonObj.get("description"))?temp_data_jsonObj.getString("description"):"";
//                        StringBuilder desc_ids_sb = new StringBuilder();
                        JSONArray temp_featureCrossReferences_array = JSONArray.parseArray(temp_data_jsonObj.get("featureCrossReferences").toString());
                        for (int i1 = 0; i1 < temp_featureCrossReferences_array.size(); i1++)
                        {
                            JSONObject temp_featureCrossReferences_obj = temp_featureCrossReferences_array.getJSONObject(i1);
                            temp_featureCrossReferences_obj.put("database_bf",temp_featureCrossReferences_obj.get("database"));
                            temp_featureCrossReferences_obj.remove("database");
//                            desc_ids_sb.append(temp_featureCrossReferences_array.getJSONObject(i1).get("id")).append(",");
//                            desc_ids_sb.append(temp_featureCrossReferences_array.getJSONObject(i1).get("url")).append(";");
////                            String temp_data_str = String.format("%s:%s",,temp_featureCrossReferences_array.getJSONObject(i1).get("id"));
////                            description_str = description_str.replaceAll(temp_data_str,"");
                        }
//                        temp_data_jsonObj.put("ids",desc_ids_sb.toString());
//                        temp_data_jsonObj.put("description",description_str);
                        temp_data_jsonObj.put("featureCrossReferences",temp_featureCrossReferences_array);
                    }
                }
                mutation_and_disease.put("disease_json",temp_data_jsonArray);
            }
            result.put("mutation_and_disease",mutation_and_disease);
            result.put("id",requestVo.get("id"));
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_search_details_familys(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject res_obj =new JSONObject();
            Map<String,Object> params = new HashMap<>();
            params.put("name",requestVo.get("name"));
            List<Map<String,Object>> res_list = mapper.sel_protein_type_list(params);
            res_obj.put("list",res_list);
            res_obj.put("total",res_list.size());
            return SingleResult.buildSuccess(res_obj);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse>  getCode() throws ServiceException {
        try {
            JSONObject res_obj =new JSONObject();
            String code_str=mapper.getCode();
            if (ObjectUtils.isEmpty(code_str))
                code_str="DRGF0001";
            else
                code_str="DRGF"+((Integer.parseInt("1"+code_str.substring(4))+1)+"").substring(1);
            res_obj.put("drgfId",code_str);
            res_obj.put("entry","");
            return SingleResult.buildAuthentication(res_obj);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW,  rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    @Override
    public SingleResult<DataResponse>  upd_gen_data( JSONObject requestVo) throws ServiceException {
    try {
        if (StringUnits.isNotNullOrEmpty(requestVo.get("gene_name")))
            requestVo.put("",requestVo.get("gene_name"));

        //general_information
        JSONObject general_information_params= requestVo.getJSONObject("general_information");
        general_information_params.put("id",requestVo.get("id"));

        //protein_sequence
        JSONObject protein_sequence_params= requestVo.getJSONObject("protein_sequence");
        protein_sequence_params.put("p_id",requestVo.get("id"));
        JSONArray protein_sequence_sub_list = protein_sequence_params.getJSONArray("list");
        List<JSONObject> precursor_list = new ArrayList<>();
        List<JSONObject> mature_form_list = new ArrayList<>();
        List<JSONObject> domains_list = new ArrayList<>();
        for (int i = 0; i < protein_sequence_sub_list.size(); i++)
        {
            JSONObject jsonObject = protein_sequence_sub_list.getJSONObject(i);
            jsonObject.remove("list");
            if ("Propeptide".equals(jsonObject.get("type"))) //信号肽
                precursor_list.add(jsonObject);
            else if ("Domain".equals(jsonObject.get("type"))) {
                domains_list.add(jsonObject);
            } else
                mature_form_list.add(jsonObject);
        }
        protein_sequence_params.put("precursor",precursor_list.toString());
        protein_sequence_params.put("mature_form",mature_form_list.toString());
        //protein_function
        JSONObject protein_function_params= requestVo.getJSONObject("protein_function");
        protein_function_params.put("p_id",requestVo.get("id"));

        if (protein_function_params.get("new_list")!=null)
            protein_function_params.put("gene_ontology_biological_process",protein_function_params.getJSONArray("new_list").toJSONString());
        else
            protein_function_params.put("gene_ontology_biological_process","[]");
        protein_function_params.remove("new_list");
        if (protein_function_params.get("function")!=null)
            protein_function_params.put("function",protein_function_params.getJSONArray("function").toJSONString());
        else
            protein_function_params.put("function","[]");

        //expression_and_location
        JSONObject expression_and_location_params= requestVo.getJSONObject("expression_and_location");
        expression_and_location_params.put("p_id",requestVo.get("id"));
        if (!ObjectUtils.isEmpty(expression_and_location_params)&&!ObjectUtils.isEmpty(expression_and_location_params.get("developmental_stage")))
            expression_and_location_params.put("developmental_stage",expression_and_location_params.getJSONArray("developmental_stage").toJSONString());
        else
            expression_and_location_params.put("developmental_stage","[]");
        if (!ObjectUtils.isEmpty(expression_and_location_params)&&!ObjectUtils.isEmpty(expression_and_location_params.get("induction")))
            expression_and_location_params.put("induction",expression_and_location_params.getJSONArray("induction").toJSONString());
        else
            expression_and_location_params.put("induction","[]");
        if (!ObjectUtils.isEmpty(expression_and_location_params)&&!ObjectUtils.isEmpty(expression_and_location_params.get("hpa_RNA_EXPRESSION_OVERVIEW")))
            expression_and_location_params.put("hpa_RNA_EXPRESSION_OVERVIEW",expression_and_location_params.getJSONArray("hpa_RNA_EXPRESSION_OVERVIEW").toJSONString());
        else
            expression_and_location_params.put("hpa_RNA_EXPRESSION_OVERVIEW","[]");

        if (StringUnits.isNotNullOrEmpty(expression_and_location_params.get("hpa_code"))==false)
            return SingleResult.buildFailure(Code.ERROR,"HPA Code 不能为空");


        //protein_structure
        JSONObject protein_structure_params= requestVo.getJSONObject("protein_structure");
        protein_structure_params.put("p_id",requestVo.get("id"));
        if (protein_structure_params.get("structured_list")!=null)
            protein_structure_params.put("PDB_ID",protein_structure_params.getJSONArray("structured_list").toJSONString());
        else
            protein_structure_params.put("PDB_ID","[]");
        protein_structure_params.remove("structured_list");

        //protein_interaction
        JSONObject protein_interaction_params= requestVo.getJSONObject("protein_interaction");
        protein_interaction_params.put("p_id",requestVo.get("id"));
        if (!ObjectUtils.isEmpty(protein_interaction_params)&&!ObjectUtils.isEmpty(protein_interaction_params.get("protein_complex")))
        {
            JSONArray temp_interaction_array = protein_interaction_params.getJSONArray("protein_complex");
            for (int k= 0; k<temp_interaction_array.size(); k++)
            {
                JSONObject temp_interaction_obj = temp_interaction_array.getJSONObject(k);
                if (temp_interaction_obj!=null && StringUnits.isNotNullOrEmpty(temp_interaction_obj.get("evidences")))
                {
                    temp_interaction_obj.getJSONArray("evidences").forEach(sub_obj_map -> {
                        Map<String,Object> temp_sub_interaction_map = (Map<String,Object>) sub_obj_map;
                        temp_sub_interaction_map.put("source","PubMed");
                    });
                }
            }
            protein_interaction_params.put("protein_complex",temp_interaction_array.toJSONString());
        }
        else
            protein_interaction_params.put("protein_complex","[]");

        //kegg_pathways
        JSONObject kegg_pathways_params= requestVo.getJSONObject("kegg_pathways");
        kegg_pathways_params.put("p_id",requestVo.get("id"));
        kegg_pathways_params.put("drgfCode", general_information_params.get("drgfCode"));
        if (!ObjectUtils.isEmpty(kegg_pathways_params)&&!ObjectUtils.isEmpty(kegg_pathways_params.get("pathway_data")))
            kegg_pathways_params.put("pathway_data",kegg_pathways_params.getJSONArray("pathway_data").toJSONString());
        else
            kegg_pathways_params.put("pathway_data","[]");

        //mutation_and_disease
        JSONObject mutation_and_disease_params= requestVo.getJSONObject("mutation_and_disease");
        mutation_and_disease_params.put("p_id",requestVo.get("id"));
        if (!ObjectUtils.isEmpty(mutation_and_disease_params)&&!ObjectUtils.isEmpty(mutation_and_disease_params.get("disease_json")))
        {
           JSONArray mutation_and_disease_array = mutation_and_disease_params.getJSONArray("disease_json");
           //下面是ids数据集转字符串
//            for (int i = 0; i < mutation_and_disease_array.size(); i++)
//            {
//                JSONObject disease_obj = mutation_and_disease_array.getJSONObject(i);
//                String[] disease_id_arr = disease_obj.getString("ids").split(";");
//                JSONArray disease_obj_arr = new JSONArray();
//                for (String disease_id_str : disease_id_arr)
//                {
//                    String[] temp_disease_sub_id_array = disease_id_str.split(",");
//                   if ( temp_disease_sub_id_array.length != 2)
//                       continue;
//                    JSONObject temp_disease_sub_id_obj = new JSONObject();
//                    temp_disease_sub_id_obj.put("id",temp_disease_sub_id_array[0]);
//                    temp_disease_sub_id_obj.put("url",temp_disease_sub_id_array[1]);
//                    temp_disease_sub_id_obj.put("database","");//这个数据前端没有了
//                    disease_obj_arr.add(temp_disease_sub_id_obj);
//                }
//                disease_obj.put("featureCrossReferences",disease_obj_arr);
//                disease_obj.remove("ids");
//            }
            mutation_and_disease_params.put("disease_json",mutation_and_disease_array.toJSONString());
        }
        else
            mutation_and_disease_params.put("disease_json","[]");

        //post_translational_modification
        JSONArray post_translational_modification_array= new JSONArray();
        if (requestVo.get("post_translational_modification")!=null)
            post_translational_modification_array.addAll(requestVo.getJSONArray("post_translational_modification"));
        for (int i = 0; i < post_translational_modification_array.size(); i++)
        {
            JSONObject post_translational_modification_params = post_translational_modification_array.getJSONObject(i);
            post_translational_modification_params.put("p_id",requestVo.get("id"));
        }
        //family_and_domain
        JSONObject family_and_domain_params = new JSONObject();
        if (requestVo.getJSONObject("family_and_domain") != null)
            family_and_domain_params= requestVo.getJSONObject("family_and_domain");
        family_and_domain_params.put("p_id",requestVo.get("id"));


  // family_and_domain 这个模块的数据暂时不做处理了
//        String complete_form = protein_sequence.get("complete_form").toString();
//        Map<String,Object> family_and_domain=mapper.base_family_and_domain_show2(params.getString("id"));
        StringBuilder domain_sb = new StringBuilder();
        for (JSONObject jsonObject : domains_list)
        {
            String posiotion_str = String.format("%s-%s",(StringUnits.isNotNullOrEmpty(jsonObject.get("start"))?jsonObject.get("start"):""),(StringUnits.isNotNullOrEmpty(jsonObject.get("end"))?jsonObject.get("end"):""));
            domain_sb.append(posiotion_str).append(",");
            domain_sb.append((StringUnits.isNotNullOrEmpty(jsonObject.get("description"))?jsonObject.get("description"):"")).append(",");
            domain_sb.append((StringUnits.isNotNullOrEmpty(jsonObject.get("url"))?jsonObject.get("url"):"")).append(";");
        }
        family_and_domain_params.put("domain",domain_sb.toString());

        //先处理一下家族的数据
          if (StringUnits.isNotNullOrEmpty(general_information_params.get("family_id"))&&
                  mapper.select_uniprot_type_exist(general_information_params)==0)
              mapper.insert_uniprot_type_info(general_information_params);

        if (StringUnits.isNotNullOrEmpty(requestVo.get("id"))) //编辑
        {
            if (mapper.select_uniprot_code_exist(general_information_params)>0)
                return SingleResult.buildFailure(Code.ERROR,"unipro entry已经存在");


            //general_information
            mapper.update_base_general_infomation_show(general_information_params);
            //protein_sequence
            mapper.update_base_protein_sequence_show(protein_sequence_params);
            //protein_function
            mapper.update_base_protein_function_show(protein_function_params);
            //expression_and_location
            mapper.update_base_expression_and_location_show(expression_and_location_params);
            if (StringUnits.isNotNullOrEmpty(expression_and_location_params.get("hpa_code")))
                mapper.update_base_expression_and_location_show_hpa(expression_and_location_params);
            //protein_structure
            mapper.update_base_protein_structure_show(protein_structure_params);

            //protein_interaction
            mapper.update_base_protein_interaction_show(protein_interaction_params);
            //kegg_pathways
            mapper.update_base_drgf_show(kegg_pathways_params);
            //mutation_and_disease
            mapper.update_base_mutation_and_disease_show(mutation_and_disease_params);
            //post_translational_modification
            mapper.delete_base_ptm_details_show(requestVo);
            for (int i = 0; i < post_translational_modification_array.size(); i++)
            {
                JSONObject post_translational_modification_params = post_translational_modification_array.getJSONObject(i);
                mapper.insert_base_ptm_details_show(post_translational_modification_params);
            }

            //family_and_domain
            family_and_domain_params.put("p_id",requestVo.get("id"));
            mapper.update_base_family_and_domain_show(family_and_domain_params);


        }else { //新增
            if (mapper.select_uniprot_code_exist(general_information_params)>0)
                return SingleResult.buildFailure(Code.ERROR,"unipro entry已经存在");

            //general_information
            mapper.insert_base_general_infomation_show(general_information_params);
            protein_sequence_params.put("p_id",general_information_params.get("id"));
            protein_function_params.put("p_id",general_information_params.get("id"));
            expression_and_location_params.put("p_id",general_information_params.get("id"));
            protein_structure_params.put("p_id",general_information_params.get("id"));

            protein_interaction_params.put("p_id",general_information_params.get("id"));
            kegg_pathways_params.put("p_id",general_information_params.get("id"));
            mutation_and_disease_params.put("p_id",general_information_params.get("id"));
            family_and_domain_params.put("p_id",general_information_params.get("id"));
            //protein_sequence
            mapper.insert_base_protein_sequence_show(protein_sequence_params);
            //protein_function
            mapper.insert_base_protein_function_show(protein_function_params);
            //expression_and_location
            mapper.insert_base_expression_and_location_show(expression_and_location_params);
            if (StringUnits.isNotNullOrEmpty(expression_and_location_params.get("hpa_code")))
                mapper.insert_base_expression_and_location_show_hpa(expression_and_location_params);
            //protein_structure
            mapper.insert_base_protein_structure_show(protein_structure_params);

            //protein_interaction
            mapper.insert_base_protein_interaction_show(protein_interaction_params);
            //kegg_pathways
            mapper.insert_base_drgf_show(kegg_pathways_params);
            //mutation_and_disease
            mapper.insert_base_mutation_and_disease_show(mutation_and_disease_params);
            //post_translational_modification
            for (int i = 0; i < post_translational_modification_array.size(); i++)
            {
                JSONObject post_translational_modification_params = post_translational_modification_array.getJSONObject(i);
                post_translational_modification_params.put("p_id",general_information_params.get("id"));
                mapper.insert_base_ptm_details_show(post_translational_modification_params);
            }
//            family_and_domain
            mapper.insert_base_family_and_domain_show(family_and_domain_params);

        }
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

}
