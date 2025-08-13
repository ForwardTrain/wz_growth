package com.school.wz_growth.web.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.WuUtils;
import com.school.wz_growth.common.request.URLConnectionHelper;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.service.DataManageService;
//import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.school.wz_growth.common.excel.ImportExcel;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.service.TestDataService;
import com.school.wz_growth.web.BaseController;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;


/**
 * 测试抓取数据，等通了再起个类
 */
@RequestMapping("/TestData")
@RestController
public class TestDataController extends BaseController {

    @Autowired
    private   TestDataService service;
    @Autowired
    private DataManageService dservice;
    private Predicate predicate;

    //excel原始数据初始化
    @RequestMapping("/importInfo")
    @ResponseBody
    public void importInfo(@RequestParam("file") MultipartFile file) throws ServiceException, IOException {

        String fileName = file.getOriginalFilename();
        try {
            Workbook wb = null;
            InputStream is = file.getInputStream();
            if (fileName.contains(".xlsx"))
                wb = new XSSFWorkbook(is);
            else if (fileName.contains(".xls"))
                wb = new HSSFWorkbook(is);
            else
                throw new ServiceException("上传的不是excel", 0);
            Sheet sheet = wb.getSheetAt(0);
            int row=2;
            for (;row<sheet.getLastRowNum();row++){
                if (sheet.getRow(row)==null) {
                    continue;
                }
                Row r=sheet.getRow(row);
                JSONObject base_general_infomation=new JSONObject();
                base_general_infomation.put("protein_name",ImportExcel.getCellValue(r.getCell(2)));
                base_general_infomation.put("gene_names",ImportExcel.getCellValue(r.getCell(3)));
                base_general_infomation.put("also_known_as",ImportExcel.getCellValue(r.getCell(4)));
                base_general_infomation.put("gene_id",ImportExcel.getCellValue(r.getCell(5)));
                base_general_infomation.put("organism",ImportExcel.getCellValue(r.getCell(6)));
                base_general_infomation.put("unipro_entry",ImportExcel.getCellValue(r.getCell(7)));
                base_general_infomation.put("protein_name_and_unipro_entry",base_general_infomation.getString("protein_name")
                        +base_general_infomation.getString("unipro_entry"));
                base_general_infomation.put("c_s_date","2024-12-07");
                base_general_infomation.put("batch","1");
                service.add_base_general_infomation(base_general_infomation);
                JSONObject base_drgf=new JSONObject();
                base_drgf.put("entry_name", ImportExcel.getCellValue(r.getCell(0)));
                base_drgf.put("drgf_id",ImportExcel.getCellValue(r.getCell(1)));
                base_drgf.put("pathways",ImportExcel.getCellValue(r.getCell(40)));
                base_drgf.put("p_id",base_general_infomation.get("id"));
                service.add_base_drgf(base_drgf);
                JSONObject base_protein_sequence=new JSONObject();
                base_protein_sequence.put("complete_form",ImportExcel.getCellValue(r.getCell(8)));
                base_protein_sequence.put("length",ImportExcel.getCellValue(r.getCell(9)));
                base_protein_sequence.put("precursor",ImportExcel.getCellValue(r.getCell(10)));
                base_protein_sequence.put("precursor_length",ImportExcel.getCellValue(r.getCell(11)));
                base_protein_sequence.put("mature_form",ImportExcel.getCellValue(r.getCell(12)));
                base_protein_sequence.put("mature_form_length",ImportExcel.getCellValue(r.getCell(13)));
                base_protein_sequence.put("p_id",base_general_infomation.get("id"));
                service.add_base_protein_sequence(base_protein_sequence);
                JSONObject base_protein_function=new JSONObject();
                base_protein_function.put("function",ImportExcel.getCellValue(r.getCell(14)));
                base_protein_function.put("gene_ontology_biological_process",ImportExcel.getCellValue(r.getCell(15)));
                base_protein_function.put("gene_ontology_cellular_component",ImportExcel.getCellValue(r.getCell(16)));
                base_protein_function.put("gene_ontology_molecular_function",ImportExcel.getCellValue(r.getCell(17)));
                base_protein_function.put("p_id",base_general_infomation.get("id"));
                service.add_base_protein_function(base_protein_function);
                JSONObject base_expression_and_location=new JSONObject();
                base_expression_and_location.put("developmental_stage",ImportExcel.getCellValue(r.getCell(18)));
                base_expression_and_location.put("induction",ImportExcel.getCellValue(r.getCell(19)));
                base_expression_and_location.put("tissue_specificity1",ImportExcel.getCellValue(r.getCell(20)));
                base_expression_and_location.put("tissue_specificity2",ImportExcel.getCellValue(r.getCell(21)));
                base_expression_and_location.put("tissue_specificity3",ImportExcel.getCellValue(r.getCell(24)));
                base_expression_and_location.put("cell_type_specificity2",ImportExcel.getCellValue(r.getCell(22)));
                base_expression_and_location.put("subcellular_location",ImportExcel.getCellValue(r.getCell(23)));
                base_expression_and_location.put("p_id",base_general_infomation.get("id"));
                service.add_base_expression_and_location(base_expression_and_location);
                JSONObject base_protein_structure=new JSONObject();
                base_protein_structure.put("structure_3D",ImportExcel.getCellValue(r.getCell(25)));
                base_protein_structure.put("PDB_ID",ImportExcel.getCellValue(r.getCell(26)));
                base_protein_structure.put("alphaFold_identifier",ImportExcel.getCellValue(r.getCell(27)));
                base_protein_structure.put("beta_strand",ImportExcel.getCellValue(r.getCell(28)));
                base_protein_structure.put("helix",ImportExcel.getCellValue(r.getCell(29)));
                base_protein_structure.put("turn",ImportExcel.getCellValue(r.getCell(30)));
                base_protein_structure.put("p_id",base_general_infomation.get("id"));
                service.add_base_protein_structure(base_protein_structure);
                JSONObject base_family_and_domain=new JSONObject();
                base_family_and_domain.put("protein_family",ImportExcel.getCellValue(r.getCell(31)));
                base_family_and_domain.put("interPro_accession",ImportExcel.getCellValue(r.getCell(32)));
                base_family_and_domain.put("domain",ImportExcel.getCellValue(r.getCell(33)));
                base_family_and_domain.put("p_id",base_general_infomation.get("id"));
                service.add_base_family_and_domain(base_family_and_domain);
                JSONObject base_protein_interaction=new JSONObject();
                base_protein_interaction.put("protein_protein_interaction",ImportExcel.getCellValue(r.getCell(34)));
                base_protein_interaction.put("protein_complex",ImportExcel.getCellValue(r.getCell(35)));
                base_protein_interaction.put("ligand_receptor_interaction",ImportExcel.getCellValue(r.getCell(36)));
                base_protein_interaction.put("pubmed_ID",ImportExcel.getCellValue(r.getCell(37)));
                base_protein_interaction.put("p_id",base_general_infomation.get("id"));
                service.add_base_protein_interaction(base_protein_interaction);
                JSONObject base_mutation_and_disease=new JSONObject();
                base_mutation_and_disease.put("involvement_in_disease",ImportExcel.getCellValue(r.getCell(38)));
                base_mutation_and_disease.put("mutagenesis",ImportExcel.getCellValue(r.getCell(39)));
                base_mutation_and_disease.put("p_id",base_general_infomation.get("id"));
                service.add_base_mutation_and_disease(base_mutation_and_disease);
                JSONObject base_post_translational_modification=new JSONObject();
                base_post_translational_modification.put("PTM1",ImportExcel.getCellValue(r.getCell(41)));
                base_post_translational_modification.put("PTM2",ImportExcel.getCellValue(r.getCell(42)));
                base_post_translational_modification.put("p_id",base_general_infomation.get("id"));
                service.add_base_post_translational_modification(base_post_translational_modification);
            }
            wb.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        JSONObject j_params=new JSONObject();
//        j_params.put("fields","accession");
//        j_params.put("query","((*) AND (reviewed:true) AND (keyword:KW-0339))");
//        JSONObject j=JSONObject.parseObject(WuUtils.sendPost("https://rest.uniprot.org/uniprotkb/search?fields=accession&query=%28%28*%29+AND+%28reviewed%3Atrue%29+AND+%28keyword%3AKW-0339%29%29",j_params.toJSONString() ));
//        JSONObject bgee=JSONObject.parseObject(WuUtils.sendPost("https://www.bgee.org/api/?display_type=json&page=gene&action=expression&gene_id=ENSSSCG00000014725&species_id=9823&cond_param=anat_entity&cond_param=cell_type&data_type=all",j_params.toJSONString() ));
//
//        System.out.println(bgee);
//        String url = "https://www.proteinatlas.org/ENSG00000182585-EPGN/single+cell";
//        Document doc = Jsoup.connect(url).get();
//        Elements elements = doc.getElementsByTag("tr");
//        for (Element element : elements) {
//            if (ObjectUtils.isEmpty(element.getElementsByTag("th")))
//                continue;
//            if (element.getElementsByTag("th").get(0).getElementsByTag("span").get(0).text().equals("Single cell type specificityi")){
//                System.out.println(element.getElementsByTag("td").get(0).text());
//            }
//        }

        //https://www.bgee.org/api/?display_type=json&page=gene&action=expression&gene_id=ENSG00000070193&species_id=9606&cond_param=anat_entity&cond_param=cell_type&data_type=all
//      try {
//          JSONObject bgee = JSONObject.parseObject(WuUtils.sendPost("https://www.bgee.org/api/?display_type=json&page=gene&action=expression&gene_id=" + "ENSG00000070193" + "&species_id=9606&cond_param=anat_entity&cond_param=cell_type&data_type=all", j_params.toJSONString()));
//          List<JSONObject> l = bgee.getJSONObject("data").getJSONArray("calls").toJavaList(JSONObject.class);
//          String expression_specificity = "";
//          int length = l.size()>6 ? 6 : l.size();
//          for (int i = 0; i < length; i++)
//          {
//              expression_specificity += l.get(i).getJSONObject("condition").getJSONObject("anatEntity").getString("name");
//              expression_specificity += ((i<length-1) ? " >= " : "");
//          }
////        base_expression_and_location.put("expression_specificity", expression_specificity);
//          System.out.println("=======");
//      }catch (Throwable t){
//          t.printStackTrace();
//      }
//
//        System.out.println("=======");
//        System.out.println("MWKWILTHCASAFPHLPGCCCCCFLLLFLVSSVPVTCQALGQDMVSPEATNSSSSSFSSPSSAGRHVRSYNHLQGDVRWRKLFSFTKYFLKIEKNGKVSGTKKENCPYSILEITSVEIGVVAVKAINSNYYLAMNKKGKLYGSKEFNNDCKLKERIEENGYNTYASFNWQHNGRQMYVALNGKGAPRRGQKTRRKNTSAHFLPMVVHS".substring(0,37));

    }

    /** 数据拉取
     *    http://127.0.0.1:8082/WZGrowth/TestData/upd_base_data
     * */
    @RequestMapping("/upd_base_data")
    @ResponseBody
    public void upd_base_data() throws Exception {

        JSONObject c_a_b=service.sel_c_s_date_and_batch();
        JSONObject dbold = dservice.sel_database_info();
        try {
            ubd(c_a_b,dbold);
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            System.out.println("over");
        }
    }

    public void ubd(JSONObject c_a_b,JSONObject dbold)  throws Exception{
        try {

            String cursor = "";
            JSONObject j_params = new JSONObject();
            j_params.put("fields", "accession");
            j_params.put("query", "((*) AND (reviewed:true) AND (keyword:KW-0339))");
            j_params.put("size", "500");
            String r = WuUtils.sendPost1("https://rest.uniprot.org/uniprotkb/search?fields=accession&query=%28%28*%29+AND+%28reviewed%3Atrue%29+AND+%28keyword%3AKW-0339%29%29&size=500", j_params.toJSONString());
            if (r.contains("</cursor>"))
            {
                cursor = r.split("<cursor>:")[1].split("</cursor>")[0];
                r = r.split("</cursor>")[1];
            }
            JSONObject j = JSONObject.parseObject(r);
            List<JSONObject> list = j.getJSONArray("results").toJavaList(JSONObject.class);
            //list = list.subList(0,50);//测试抓取数据用
            while (ObjectUtils.getDisplayString(cursor).length() > 0)
            {
                System.out.println(cursor);
                j_params.put("cursor", cursor);
                String r1 = WuUtils.sendPost1("https://rest.uniprot.org/uniprotkb/search?fields=accession&query=%28%28*%29+AND+%28reviewed%3Atrue%29+AND+%28keyword%3AKW-0339%29%29&size=500&cursor=" + cursor, j_params.toJSONString());
                if (r1.contains("</cursor>")) {
                    cursor = r1.split("<cursor>:")[1].split("</cursor>")[0];
                    r1 = r1.split("</cursor>")[1];
                } else cursor = "";

                JSONObject j1 = JSONObject.parseObject(r1);
                List<JSONObject> list1 = j1.getJSONArray("results").toJavaList(JSONObject.class);
                list.addAll(list1);
            }

            //加入excel导入的数据
            List<JSONObject> other_exist_list = service.sel_excel_data_list();
            for (int k = 0; k < other_exist_list.size(); k++)
            {
                JSONObject temp_obj = other_exist_list.get(k);
                temp_obj.put("primaryAccession",temp_obj.get("unipro_entry"));
                Predicate<Map> predicate =  mapper -> temp_obj.get("primaryAccession").equals(mapper.get("primaryAccession"));
                List<Map> res_temp_child_list = list.stream().filter(predicate).collect(Collectors.toList());

                if (res_temp_child_list.size()==0)
                    list.add(temp_obj);
            }

//            List<JSONObject> list = new ArrayList(){{
//                add(new JSONObject(){{
//                    put("primaryAccession","Q02297");
//                }});
//            }};


            //一、数据过滤用，把已经存在的数据过滤掉
            List<JSONObject> ex_list = service.sel_ex_list(c_a_b);
            for (JSONObject jsonObject : list)
            {
                String unipro_entry = jsonObject.getString("primaryAccession");
                boolean flag = false;
                for (JSONObject object : ex_list)
                {
                    if (object.getString("unipro_entry").equals(unipro_entry)) {
                        flag = true;
                    }
                }
                if (flag) //全数据拉取
                    continue;

                System.out.println("start+++++++++++++ "+unipro_entry);

//                if ("O95393".equals(unipro_entry))
//                    System.out.println("+++++");

                //二、处理获取数据
                //1.基本信息general_information  UniProtKB reviewed (Swiss-Prot)
                JSONObject info = JSONObject.parseObject(WuUtils.sendPost("https://rest.uniprot.org/uniprotkb/" + unipro_entry, new JSONObject().toJSONString()));
                JSONObject base_general_infomation = new JSONObject();
                base_general_infomation.put("protein_status", info.get("entryType").toString().replaceAll("(Swiss-Prot)",""));
                base_general_infomation.put("protein_name", info.getJSONObject("proteinDescription")
                        .getJSONObject("recommendedName")
                        .getJSONObject("fullName").getString("value"));
                if (ObjectUtils.isEmpty(info.get("genes")) || ObjectUtils.isEmpty(info.getJSONArray("genes").toJavaList(JSONObject.class).get(0)
                        .get("geneName")))
                    base_general_infomation.put("gene_names", "");
                else
                    base_general_infomation.put("gene_names", info.getJSONArray("genes").toJavaList(JSONObject.class).get(0)
                            .getJSONObject("geneName").getString("value"));
                if (ObjectUtils.isEmpty(info.get("genes")) || ObjectUtils.isEmpty(info.getJSONArray("genes").toJavaList(JSONObject.class).get(0).get("orfNames")))
                    base_general_infomation.put("also_known_as", "");
                else
                    base_general_infomation.put("also_known_as", info.getJSONArray("genes").toJavaList(JSONObject.class).get(0)
                            .getJSONArray("orfNames")
                            .toJavaList(JSONObject.class).get(0).getString("value"));
                List<JSONObject> uniProtKBCrossReferences = info.getJSONArray("uniProtKBCrossReferences").toJavaList(JSONObject.class);
                base_general_infomation.put("gene_id",
                        j_reesult(uniProtKBCrossReferences, "GeneID").getString("id"));
                base_general_infomation.put("organism", info.getJSONObject("organism")
                        .getString("scientificName") + "(" + info.getJSONObject("organism")
                        .getString("commonName") + ")");
                base_general_infomation.put("unipro_entry", unipro_entry);
                base_general_infomation.put("protein_name_and_unipro_entry", base_general_infomation.getString("protein_name")
                        + base_general_infomation.getString("unipro_entry"));
                base_general_infomation.put("c_s_date", c_a_b.getString("c_s_date"));
                base_general_infomation.put("batch", c_a_b.getString("batch"));
                //gene数据摘取
                base_general_infomation.putAll(get_gene_data(StringUnits.isNotNullOrEmpty(base_general_infomation.get("gene_id"))?base_general_infomation.get("gene_id").toString():""));

                base_general_infomation.put("key_words",info.getJSONArray("keywords").toJSONString() );
                List<JSONObject> comments = info.getJSONArray("comments").toJavaList(JSONObject.class);
                String family = "";
                for (JSONObject feature : comments)
                {
                    if (!ObjectUtils.isEmpty(feature) &&
                            !ObjectUtils.isEmpty(feature.get("commentType")) &&
                            feature.getString("commentType").equals("SIMILARITY") && !ObjectUtils.isEmpty(feature.get("texts")))
                    {
                        List<JSONObject> edv = feature.getJSONArray("texts").toJavaList(JSONObject.class);
                        for (JSONObject object : edv)
                        {
                            family += object.getString("value") + ";";
                        }
                    }
                }
                base_general_infomation.put("family", family);
                service.add_base_general_infomation(base_general_infomation);
                service.add_DRGF_code(base_general_infomation);


//                JSONObject base_go_term_data=new JSONObject();
//                for (JSONObject uniProtKBCrossReference : uniProtKBCrossReferences) {
//                    if (uniProtKBCrossReference.getString("database").equals("GO"))
//                }

                //KEGG数据库 gene_id基因id
                JSONObject base_drgf = new JSONObject();
                base_drgf.put("entry_name", info.getString("uniProtkbId"));
                base_drgf.put("drgf_id", "");
                base_drgf.put("pathways", "https://www.genome.jp/dbget-bin/www_bget?" + j_reesult(uniProtKBCrossReferences, "KEGG").getString("id"));
                base_drgf.put("p_id", base_general_infomation.get("id"));
                if (!ObjectUtils.isEmpty(j_reesult(uniProtKBCrossReferences, "KEGG").getString("id")))
                {
                    String url = base_drgf.getString("pathways");
                    System.out.println(url);
                    Document doc = Jsoup.connect(url).get();
                    Elements elements = doc.getElementsByTag("tr");
                    List<JSONObject> pathway_data_list = new ArrayList<>();
                    for (Element element : elements)
                    {
                        if (!ObjectUtils.isEmpty(element.getElementsByTag("span")) &&
                                element.getElementsByTag("span").get(0).text().equals("Pathway"))
                        {
                            for (Element element1 : element.getElementsByTag("td").get(0).getElementsByTag("tr"))
                            {
                                JSONObject kegg_temp_data_ob = new JSONObject();
                                Element a_element = element1.getElementsByTag("a").get(0);
                                kegg_temp_data_ob.put("pathway_url", "https://www.genome.jp"+a_element.attr("href"));
                                kegg_temp_data_ob.put("pathway_id", a_element.text());
                                kegg_temp_data_ob.put("pathway_name", element1.getElementsByTag("td").get(1).text());
                                pathway_data_list.add(kegg_temp_data_ob);
                            }
                            break;
                        }
                    }
                    if (pathway_data_list.size() > 0)
                        base_drgf.put("pathway_data", pathway_data_list.toString());
                    else
                        base_drgf.put("pathway_data", "");
                } else {
                    base_drgf.put("pathway_data", "");
                }
                service.add_base_drgf(base_drgf);


                //2.seqquence模块，kegg, gene
                JSONObject base_protein_sequence = new JSONObject();
                base_protein_sequence.put("complete_form", info.getJSONObject("sequence").getString("value"));
                base_protein_sequence.put("length", info.getJSONObject("sequence").getString("length"));
                List<JSONObject> features = info.getJSONArray("features").toJavaList(JSONObject.class);
                List<JSONObject> Signal = new ArrayList<>();
                List<JSONObject> Chain = new ArrayList<>();

                for (JSONObject feature : features) {
                    if (feature.getString("type").equals("Signal") ||
                            feature.getString("type").equals("Propeptide"))
                        Signal .add(feature);
                    if (feature.getString("type").equals("Chain"))
                        Chain .add( feature);
                }
                base_protein_sequence.put("precursor", "/");
                base_protein_sequence.put("precursor_length", "/");
                String precursor="";

                List<JSONObject> single_list = new ArrayList<>();
                for (JSONObject object : Signal)
                {
                    if (ObjectUtils.isEmpty(object) || ObjectUtils.isEmpty(object.get("location"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").get("start"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").getJSONObject("start").get("value"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").get("end"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").getJSONObject("end").get("value"))) {

                    } else {
                        int a = object.getJSONObject("location").getJSONObject("start").getInteger("value");
                        int b = object.getJSONObject("location").getJSONObject("end").getInteger("value");
                        precursor+=base_protein_sequence.getString("complete_form").substring(a-1, b)+"";

                        JSONObject tempDataJsonObject=new JSONObject();
                        tempDataJsonObject.put("start",a);
                        tempDataJsonObject.put("end",b);
                        tempDataJsonObject.put("type","signal");
                        tempDataJsonObject.put("length",(b-(a-1)));
                        tempDataJsonObject.put("data",precursor);
                        tempDataJsonObject.put("description",object.get("description"));
                        single_list.add(tempDataJsonObject);
                    }
                }
                if (single_list.size()>0)
                    base_protein_sequence.put("precursor", single_list.toString());


                base_protein_sequence.put("mature_form", "/");
                base_protein_sequence.put("mature_form_length", "/");
                String mature_form="";
                List<JSONObject> chain_list = new ArrayList<>();
                for (JSONObject object : Chain)
                {
                    if (ObjectUtils.isEmpty(object) || ObjectUtils.isEmpty(object.get("location"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").get("start"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").getJSONObject("start").get("value"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").get("end"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").getJSONObject("end").get("value")))
                    {
                    } else {
                        int a = object.getJSONObject("location").getJSONObject("start").getInteger("value");
                        int b = object.getJSONObject("location").getJSONObject("end").getInteger("value");
                        mature_form+=base_protein_sequence.getString("complete_form").substring(a-1, b )+"";

                        JSONObject tempDataJsonObject=new JSONObject();
                        tempDataJsonObject.put("start",a);
                        tempDataJsonObject.put("end",b);
                        tempDataJsonObject.put("type","China");
                        tempDataJsonObject.put("length",(b-(a-1)));
                        tempDataJsonObject.put("data",mature_form);
                        tempDataJsonObject.put("description",object.get("description"));

                        chain_list.add(tempDataJsonObject);
                    }
                }
                if (chain_list.size()>0)
                    base_protein_sequence.put("mature_form", chain_list.toString());

//                base_protein_sequence.put("precursor",ObjectUtils.isEmpty(Signal)?"":("Signal peptide "
//                        +Signal.getJSONObject("location").getJSONObject("start").getString("value")
//                        +".."
//                        +Signal.getJSONObject("location").getJSONObject("start").getString("value")));
//                base_protein_sequence.put("precursor_length","/");

//
//                base_protein_sequence.put("mature_form",ObjectUtils.isEmpty(Chain)?"":("Signal peptide "
//                        +Chain.getJSONObject("location").getJSONObject("start").getString("value")
//                        +".."
//                        +Chain.getJSONObject("location").getJSONObject("start").getString("value")));
//                base_protein_sequence.put("mature_form_length","/");

                base_protein_sequence.put("p_id", base_general_infomation.get("id"));
                service.add_base_protein_sequence(base_protein_sequence);
                JSONObject base_protein_function = new JSONObject();
                JSONObject function = new JSONObject();
                for (JSONObject comment : comments)
                {
                    if (comment.getString("commentType").equals("FUNCTION"))
                        function = comment;
                }
                if (ObjectUtils.isEmpty(function.get("texts")))
                    base_protein_function.put("function", "");
                else
                    base_protein_function.put("function", function.getJSONArray("texts")
                            .toJavaList(JSONObject.class).toString());
                String  pubmed_id="";
                if (!ObjectUtils.isEmpty(function.get("texts")))
                {
                    for (JSONObject texts : function.getJSONArray("texts").toJavaList(JSONObject.class))
                    {
                        if (!ObjectUtils.isEmpty(texts.get("evidences")))
                        {
                            for (JSONObject evidences : texts.getJSONArray("evidences").toJavaList(JSONObject.class))
                            {
                                if (StringUnits.isNotNullOrEmpty(evidences.get("source"))&&
                                        evidences.getString("source").equals("PubMed"))
                                    pubmed_id+=evidences.getString("id")+";";
                            }
                        }
                    }
                }
                base_protein_function.put("pubmed_id", pubmed_id);
                List<JSONObject> go = new ArrayList<>();
                for (JSONObject uniProtKBCrossReference : uniProtKBCrossReferences)
                {
                    if (uniProtKBCrossReference.getString("database").equals("GO"))
                        go.add(uniProtKBCrossReference);
                }
                String gene_ontology_biological_process = "",
                        gene_ontology_cellular_component = "",
                        gene_ontology_molecular_function = "";

                JSONArray temp_function_go_array = new JSONArray();
                for (JSONObject object : go)
                {
                    JSONObject temp_res_go_data_obj = new JSONObject();
                    JSONObject gop_term = object.getJSONArray("properties").toJavaList(JSONObject.class)
                            .stream().filter(t -> t.getString("key").equals("GoTerm")).collect(Collectors.toList())
                            .get(0);
                    JSONObject gop_evidence_type = object.getJSONArray("properties").toJavaList(JSONObject.class)
                            .stream().filter(t -> t.getString("key").equals("GoEvidenceType")).collect(Collectors.toList())
                            .get(0);
                    temp_res_go_data_obj.put("gop_evidence_type",gop_evidence_type.get("value"));
                    temp_res_go_data_obj.put("goId",object.get("id"));
                    temp_res_go_data_obj.put("goUrl","https://www.ebi.ac.uk/QuickGO/term/GO:"+object.get("id"));

                    if (gop_term.getString("value").contains("C:"))
                    {
                        temp_res_go_data_obj.put("ASPECT","Cellular Component");
                        temp_res_go_data_obj.put("ASPECT_type","C:");
                        temp_res_go_data_obj.put("TERM",gop_term.getString("value").replaceFirst("C:",""));
                    }
                    if (gop_term.getString("value").contains("P:"))
                    {
                        temp_res_go_data_obj.put("ASPECT","Biological Process");
                        temp_res_go_data_obj.put("ASPECT_type","P:");
                        temp_res_go_data_obj.put("TERM",gop_term.getString("value").replaceFirst("P:",""));
                    }
                    if (gop_term.getString("value").contains("F:"))
                    {
                        temp_res_go_data_obj.put("ASPECT","Molecular Function");
                        temp_res_go_data_obj.put("ASPECT_type","F:");
                        temp_res_go_data_obj.put("TERM",gop_term.getString("value").replaceFirst("F:",""));
                    }

                    if (!ObjectUtils.isEmpty(object.get("evidences")))
                        temp_res_go_data_obj.put("evidences",object.getJSONArray("evidences").toJavaList(JSONObject.class));

                    temp_function_go_array.add(temp_res_go_data_obj);
                }
                base_protein_function.put("gene_ontology_biological_process", temp_function_go_array.toJSONString());
                base_protein_function.put("gene_ontology_cellular_component", gene_ontology_cellular_component);
                base_protein_function.put("gene_ontology_molecular_function", gene_ontology_molecular_function);
                base_protein_function.put("p_id", base_general_infomation.get("id"));
                service.add_base_protein_function(base_protein_function);


                //HPA数据库、bgee数据库
                JSONObject base_expression_and_location = new JSONObject();
                String proteinatlas_id = "";
                for (JSONObject uniProtKBCrossReference : uniProtKBCrossReferences)
                {
                    if (ObjectUtils.getDisplayString(base_general_infomation.get("organism")).contains("Human"))
//                    if (false)
                    {
                        if (uniProtKBCrossReference.getString("database").equals("HPA"))
                        {
                            //tissue_specificity_rna数据
                            List<JSONObject> temp_sub_structure_list = uniProtKBCrossReference.getJSONArray("properties").toJavaList(JSONObject.class);
                            Predicate<JSONObject> predicate = temp_sub_obj -> "ExpressionPatterns".equals(temp_sub_obj.get("key"));
                            List<JSONObject> temp_sub_structure_res_list = temp_sub_structure_list.stream().filter(predicate).collect(Collectors.toList());
                            if (temp_sub_structure_res_list.size() > 0)
                                base_expression_and_location.put("tissue_specificity_rna", temp_sub_structure_res_list.get(0).get("value"));

                            String single_cell_type_specificity = "", RNA_EXPRESSION_OVERVIEW = "", PROTEIN_EXPRESSION_OVERVIEW = "";
                            proteinatlas_id = uniProtKBCrossReference.getString("id");
                            String url2 = "https://www.proteinatlas.org/" + proteinatlas_id + "-EPGN/single+cell";
                            Document doc2 = Jsoup.connect(url2).get();
                            Elements elements2 = doc2.getElementsByTag("tr");
                            for (Element element : elements2)
                            {
                                if (ObjectUtils.isEmpty(element.getElementsByTag("th")))
                                    continue;
                                if (element.getElementsByTag("th").get(0).getElementsByTag("span").get(0).text().equals("Single cell type specificityi"))
                                {
                                    single_cell_type_specificity = element.getElementsByTag("td").get(0).text();
                                    base_expression_and_location.put("single_cell_type_specificity", single_cell_type_specificity);
                                    break;
                                }
                            }

                            String url1 = "https://www.proteinatlas.org/" + proteinatlas_id + "-TEC/tissue#rna_expression";
//                    WebDriverManager.chromedriver().setup();
//
//                    // 初始化 WebDriver
//                    WebDriver driver = new ChromeDriver();
//                    driver.get(url1);
//
//                    // 找到SVG元素
//                    List<WebElement> svgElements = driver.findElements(By.tagName("tbody"));
//                    for (WebElement element : svgElements) {
//                        if (ObjectUtils.isEmpty(element.findElements(By.tagName("tr")).get(0).findElements(By.tagName("th"))))
//                            continue;
//                        if (element.findElements(By.tagName("tr")).get(0).findElements(By.tagName("th"))
//                                .get(0).findElements(By.tagName("span")).get(0).getText().equals("PROTEIN EXPRESSION OVERVIEWi")) {
//                            PROTEIN_EXPRESSION_OVERVIEW = element.findElements(By.tagName("tr")).get(1).findElement(By.tagName("svg")).getAttribute("outerHTML");
//                            base_expression_and_location.put("PROTEIN_EXPRESSION_OVERVIEW", PROTEIN_EXPRESSION_OVERVIEW);
//                        }
//                        if (element.findElements(By.tagName("tr")).get(0).findElements(By.tagName("th"))
//                                .get(0).findElements(By.tagName("span")).get(0).getText().equals("RNA EXPRESSION OVERVIEWi")) {
//                            RNA_EXPRESSION_OVERVIEW = element.findElements(By.tagName("tr")).get(1).findElement(By.tagName("svg")).getAttribute("outerHTML");
//                            base_expression_and_location.put("RNA_EXPRESSION_OVERVIEW", RNA_EXPRESSION_OVERVIEW);
//                        }
//                    }
//                    if (driver != null)
//                        driver.quit();
//                            Document doc1 = Jsoup.connect(url1).get();
//                            Elements elements1 = doc1.getElementsByTag("tbody");
//                            for (Element element : elements1)
//                            {
//                                if (ObjectUtils.isEmpty(element.getElementsByTag("tr").get(0).getElementsByTag("th")))
//                                    continue;
//                                if (element.getElementsByTag("tr").get(0).getElementsByTag("th").get(0).getElementsByTag("span").get(0).text().equals("PROTEIN EXPRESSION OVERVIEWi"))
//                                {
//                                    PROTEIN_EXPRESSION_OVERVIEW=element.getElementsByTag("tr").get(1).getElementsByTag("svg").outerHtml();
//                                    base_expression_and_location.put("PROTEIN_EXPRESSION_OVERVIEW",PROTEIN_EXPRESSION_OVERVIEW);
//                                }
//                                if (element.getElementsByTag("tr").get(0).getElementsByTag("th").get(0).getElementsByTag("span").get(0).text().equals("RNA EXPRESSION OVERVIEWi"))
//                                {
//                                    RNA_EXPRESSION_OVERVIEW=element.getElementsByTag("tr").get(1).getElementsByTag("svg").outerHtml();
//                                    base_expression_and_location.put("RNA_EXPRESSION_OVERVIEW",RNA_EXPRESSION_OVERVIEW);
//                                }
//                            }

                            break;
                        }
                    } else {
                        if (uniProtKBCrossReference.getString("database").equals("Bgee"))
                        {//https://www.bgee.org/api/?display_type=json&page=gene&action=expression&gene_id=ENSBTAG00000005198&species_id=9913&cond_param=anat_entity&cond_param=cell_type&data_type=all
                            proteinatlas_id = uniProtKBCrossReference.getString("id");
                            String expression_specificity = "";

                            JSONObject bgee_base_info = JSONObject.parseObject(URLConnectionHelper.sendGet("https://www.bgee.org/api/?display_type=json&page=gene&action=general_info&gene_id=" + proteinatlas_id, null));
                            if (bgee_base_info!=null&&
                                    bgee_base_info.get("data")!=null &&
                                    bgee_base_info.getJSONObject("data").get("genes")!=null && bgee_base_info.getJSONObject("data").getJSONArray("genes").size()>0 &&
                                    bgee_base_info.getJSONObject("data").getJSONArray("genes").getJSONObject(0).get("species")!=null
                            )
                            {
                                String anatomical_entity_id = bgee_base_info.getJSONObject("data").getJSONArray("genes").getJSONObject(0).getJSONObject("species").getString("id");
                                JSONObject bgee = JSONObject.parseObject(URLConnectionHelper.sendGet("https://www.bgee.org/api/?display_type=json&page=gene&action=expression&gene_id=" + proteinatlas_id + "&species_id="+anatomical_entity_id+"&cond_param=anat_entity&cond_param=cell_type&data_type=all", null));
                                List<JSONObject> l = bgee.getJSONObject("data").getJSONArray("calls").toJavaList(JSONObject.class);

                                int length = l.size()>6 ? 6 : l.size();
                                for (int i = 0; i < length; i++)
                                {
                                    expression_specificity += l.get(i).getJSONObject("condition").getJSONObject("anatEntity").getString("name");
                                    expression_specificity += ((i<length-1) ? " >= " : "");
                                }
                            }

//                            species

//                            JSONObject bgee = JSONObject.parseObject(WuUtils.sendPost("https://www.bgee.org/api/?display_type=json&page=gene&action=expression&gene_id=" + proteinatlas_id + "&species_id=9913&cond_param=anat_entity&cond_param=cell_type&data_type=all", j_params.toJSONString()));


                            base_expression_and_location.put("expression_specificity", expression_specificity);
                            break;
                        }
                    }
                }

                String subcellular_location = "", tissue_specificity3 = "";
//                if ("O95393".equals(unipro_entry))//测试用
//                    System.out.println("+++++++");
                for (JSONObject comment : comments)
                {
                    if (comment.getString("commentType").equals("SUBCELLULAR LOCATION"))
                    {
                        subcellular_location += comment.getString("molecule") +
                                comment.getJSONArray("subcellularLocations").toJavaList(JSONObject.class)
                                        .get(0).getString("value") + ";";
                    }
                    if (comment.getString("commentType").equals("DEVELOPMENTAL STAGE"))
                    {  //induction  developmental_stage
                        base_expression_and_location.put("developmental_stage", comment.getJSONArray("texts").toJavaList(JSONObject.class).toString());
//                        if (!ObjectUtils.isEmpty(comment.getJSONArray("texts").
//                                toJavaList(JSONObject.class).get(0).get("evidences")))
//                        {
//                            base_expression_and_location.put("developmental_stage",
//                                    comment.getJSONArray("texts").
//                                            toJavaList(JSONObject.class).get(0)
//                                            .getString("value")
//                                            + comment.getJSONArray("texts").
//                                            toJavaList(JSONObject.class).get(0).getJSONArray("evidences").
//                                            toJavaList(JSONObject.class)
//                                            .get(0).getString("source") + comment.getJSONArray("texts").
//                                            toJavaList(JSONObject.class).get(0).getJSONArray("evidences").
//                                            toJavaList(JSONObject.class)
//                                            .get(0).getString("id"));
//                        }
                    }

                    if (comment.getString("commentType").equals("INDUCTION"))
                    {
                        base_expression_and_location.put("induction", comment.getJSONArray("texts").toJavaList(JSONObject.class).toString());
//                        if (!ObjectUtils.isEmpty(comment.getJSONArray("texts").
//                                toJavaList(JSONObject.class).get(0).get("evidences")))
//                        {
//                            base_expression_and_location.put("induction",
//                                    comment.getJSONArray("texts").
//                                            toJavaList(JSONObject.class).get(0)
//                                            .getString("value")
//                                            + comment.getJSONArray("texts").
//                                            toJavaList(JSONObject.class).get(0).getJSONArray("evidences").
//                                            toJavaList(JSONObject.class)
//                                            .get(0).getString("evidenceCode")
//                                            + comment.getJSONArray("texts").
//                                            toJavaList(JSONObject.class).get(0).getJSONArray("evidences").
//                                            toJavaList(JSONObject.class).get(0).getString("source")
//                                            + comment.getJSONArray("texts").
//                                            toJavaList(JSONObject.class).get(0).getJSONArray("evidences").
//                                            toJavaList(JSONObject.class)
//                                            .get(0).getString("id"));
//                        }

                    }
                    if (comment.getString("commentType").equals("TISSUE SPECIFICITY")) {

                        String c1 = "", c2 = "", c3 = "", c4 = "";
                        c1 = comment.getJSONArray("texts").
                                toJavaList(JSONObject.class).get(0)
                                .getString("value");
                        if (!ObjectUtils.isEmpty(comment.getJSONArray("texts").
                                toJavaList(JSONObject.class).get(0).get("evidences"))) {
                            c2 = comment.getJSONArray("texts").
                                    toJavaList(JSONObject.class).get(0).getJSONArray("evidences").
                                    toJavaList(JSONObject.class)
                                    .get(0).getString("evidenceCode");
                            c3 = comment.getJSONArray("texts").
                                    toJavaList(JSONObject.class).get(0).getJSONArray("evidences").
                                    toJavaList(JSONObject.class).get(0).getString("source");
                            c4 = comment.getJSONArray("texts").
                                    toJavaList(JSONObject.class).get(0).getJSONArray("evidences").
                                    toJavaList(JSONObject.class).get(0).getString("id");
                        }


                        base_expression_and_location.put("tissue_specificity3",
                                c1 + c2 + c3 + c4);
                    }
                }
                for (JSONObject uniProtKBCrossReference : uniProtKBCrossReferences) {
                    if (uniProtKBCrossReference.getString("database").equals("Bgee"))
                        base_expression_and_location.put("tissue_specificity1",
                                uniProtKBCrossReference.getString("id"));
                    if (uniProtKBCrossReference.getString("database").equals("HPA")) {
                        base_expression_and_location.put("Tissue_specificity_RNA",
                                uniProtKBCrossReference.getString("id"));
                        base_expression_and_location.put("cell_type_specificity2",
                                uniProtKBCrossReference.getString("id"));
                    }

                }
                base_expression_and_location.put("subcellular_location", subcellular_location);
                base_expression_and_location.put("p_id", base_general_infomation.get("id"));
                service.add_base_expression_and_location(base_expression_and_location);

                // structure 三维结构
                JSONObject base_protein_structure = new JSONObject();
                int structure_num = 0, structure_num1 = 0;
                List<JSONObject> temp_sub_structure_all_list = new ArrayList<>();
                for (JSONObject uniProtKBCrossReference : uniProtKBCrossReferences)
                {
                    if (uniProtKBCrossReference.getString("database").equals("PDB") ||
                            uniProtKBCrossReference.getString("database").equals("AlphaFoldDB"))
                    {
                        JSONObject temp_sub_structure_obj = new JSONObject();
                        temp_sub_structure_obj.put("source", uniProtKBCrossReference.get("database"));
                        temp_sub_structure_obj.put("identifier", uniProtKBCrossReference.get("id"));

                        List<JSONObject> temp_sub_structure_list = uniProtKBCrossReference.getJSONArray("properties").toJavaList(JSONObject.class);

                        Predicate<JSONObject> predicate = temp_sub_obj -> "Method".equals(temp_sub_obj.get("key"));
                        List<JSONObject> temp_sub_structure_res_list = temp_sub_structure_list.stream().filter(predicate).collect(Collectors.toList());
                        if (temp_sub_structure_res_list.size() > 0)
                            temp_sub_structure_obj.put("method", temp_sub_structure_res_list.get(0).get("value"));

                        predicate =temp_sub_obj -> "Resolution".equals(temp_sub_obj.get("key"));
                        temp_sub_structure_res_list = temp_sub_structure_list.stream().filter(predicate).collect(Collectors.toList());
                        if (temp_sub_structure_res_list.size() > 0)
                            temp_sub_structure_obj.put("resolution", temp_sub_structure_res_list.get(0).get("value"));

                        predicate =temp_sub_obj -> "Description".equals(temp_sub_obj.get("key"));
                        temp_sub_structure_res_list = temp_sub_structure_list.stream().filter(predicate).collect(Collectors.toList());
                        if (temp_sub_structure_res_list.size() > 0)
                            temp_sub_structure_obj.put("description", temp_sub_structure_res_list.get(0).get("value"));

                        predicate =temp_sub_obj -> "Chains".equals(temp_sub_obj.get("key"));
                        temp_sub_structure_res_list = temp_sub_structure_list.stream().filter(predicate).collect(Collectors.toList());
                        if (temp_sub_structure_res_list.size() > 0)
                        {
                            String temp_chains_str = temp_sub_structure_res_list.get(0).getString("value");
                            if (temp_chains_str.contains("=")) {
                                temp_sub_structure_obj.put("chain", temp_chains_str.split("=")[0]);
                                temp_sub_structure_obj.put("positions", temp_chains_str.split("=")[1]);
                            }
                        }
                        temp_sub_structure_all_list.add(temp_sub_structure_obj);
                        structure_num++;
//                        base_protein_structure.put("PDB_ID",
//                                ObjectUtils.getDisplayString(base_protein_structure.get("id")) + "," +
//                                        uniProtKBCrossReference.getString("id"));
                    }
                    ;
//                    if (uniProtKBCrossReference.getString("database").equals("AlphaFoldDB"))
//                    {
//                        structure_num++;
//                    }

                }
                base_protein_structure.put("PDB_ID",temp_sub_structure_all_list.toString());
                base_protein_structure.put("structure_num", structure_num);
                base_protein_structure.put("structure_num1", structure_num1);
                String beta_strand = "", turn = "", helix = "";
                for (JSONObject feature : features) {
                    if (feature.getString("type").equals("Beta strand")) {
                        JSONObject edv = feature.getJSONArray("evidences").toJavaList(JSONObject.class).get(0);
                        beta_strand += "STRAND " + feature.getJSONObject("location")
                                .getJSONObject("start").getString("value")
                                + ".." + feature.getJSONObject("location")
                                .getJSONObject("end").getString("value") + " "
                                + "evidenceCode=" + edv.getString("evidenceCode")
                                + "||source=" + edv.getString("source") + ";";
                    }
                    if (feature.getString("type").equals("Turn")) {
                        JSONObject edv = feature.getJSONArray("evidences").toJavaList(JSONObject.class).get(0);
                        turn += "Turn " + feature.getJSONObject("location")
                                .getJSONObject("start").getString("value")
                                + ".." + feature.getJSONObject("location")
                                .getJSONObject("end").getString("value") + " "
                                + "evidenceCode=" + edv.getString("evidenceCode")
                                + "||source=" + edv.getString("source") + ";";
                    }
                    if (feature.getString("type").equals("Helix")) {
                        JSONObject edv = feature.getJSONArray("evidences").toJavaList(JSONObject.class).get(0);
                        helix += "Helix " + feature.getJSONObject("location")
                                .getJSONObject("start").getString("value")
                                + ".." + feature.getJSONObject("location")
                                .getJSONObject("end").getString("value") + " "
                                + "evidenceCode=" + edv.getString("evidenceCode")
                                + "||source=" + edv.getString("source") + ";";
                    }
                }
                for (JSONObject uniProtKBCrossReference : uniProtKBCrossReferences) {
                    if (uniProtKBCrossReference.getString("database").equals("AlphaFoldDB"))
                        base_protein_structure.put("alphaFold_identifier",
                                uniProtKBCrossReference.getString("id"));
                }
//                base_protein_structure.put("structure_3D",);
                base_protein_structure.put("beta_strand", beta_strand);
                base_protein_structure.put("helix", helix);
                base_protein_structure.put("turn", turn);
                base_protein_structure.put("p_id", base_general_infomation.get("id"));
                service.add_base_protein_structure(base_protein_structure);
                JSONObject base_family_and_domain = new JSONObject();
                String domain = "";
                for (JSONObject feature : features) {
                    if (feature.getString("type").equals("Domain") && !ObjectUtils.isEmpty(feature.get("evidences"))) {
                        List<JSONObject> edv = feature.getJSONArray("evidences").toJavaList(JSONObject.class);
                        for (JSONObject object : edv) {
                            domain += feature.getJSONObject("location").getJSONObject("start").getString("value") + "-" +
                                    feature.getJSONObject("location").getJSONObject("end").getString("value") + ","
                                    + feature.getString("description") + ","
                                    + "https://prosite.expasy.org/rule/"+feature.getJSONArray("evidences").toJavaList(JSONObject.class).get(0)
                                    .getString("id")+";";

                        }
                    }
                }

//                base_family_and_domain.put("interPro_accession",);
                base_family_and_domain.put("domain", domain);
                base_family_and_domain.put("p_id", base_general_infomation.get("id"));
                service.add_base_family_and_domain(base_family_and_domain);
                JSONObject base_protein_interaction = new JSONObject();
                for (JSONObject uniProtKBCrossReference : uniProtKBCrossReferences) {
                    if (uniProtKBCrossReference.getString("database").equals("STRING")) {
                        base_protein_interaction.put("protein_protein_interaction",
                                uniProtKBCrossReference.getString("id"));
                        base_protein_interaction.put("svg",
                                "https://cn.string-db.org/svg/" + uniProtKBCrossReference.getString("id").replace(";", "") + ".svg");
                    }
                }


                for (JSONObject comment : comments) {
                    if (comment.getString("commentType").equals("SUBUNIT"))
                    {
                        List<JSONObject> texts = comment.getJSONArray("texts").toJavaList(JSONObject.class);
                        base_protein_interaction.put("protein_complex",texts.toString());
                    }

                }
//                base_protein_interaction.put("ligand_receptor_interaction",);
//                base_protein_interaction.put("pubmed_ID",);
                base_protein_interaction.put("p_id", base_general_infomation.get("id"));
                service.add_base_protein_interaction(base_protein_interaction);
                JSONObject base_mutation_and_disease = new JSONObject();
                String involvement_in_disease = "", mutagenesis = "", ptm2 = "";
                for (JSONObject comment : comments) {
                    if (comment.getString("commentType").equals("DISEASE") && !ObjectUtils.isEmpty(comment.get("disease"))) {
                        involvement_in_disease +=
                                comment.getJSONObject("disease").getString("diseaseId") + " " + comment.getJSONObject("disease").getString("description") + ";";
                    }

                }

                List<JSONObject> disease_list = new ArrayList<>();//突变数据集
                for (JSONObject feature : features)
                {
//                    if (feature.getString("type").equals("Mutagenesis")) {
//                        String fs = "";
//                        if (!ObjectUtils.isEmpty(feature.get("alternativeSequence")) &&
//                                !ObjectUtils.isEmpty(feature.getJSONObject("alternativeSequence").get("alternativeSequences"))) {
//                            fs = feature.getJSONObject("alternativeSequence").getJSONArray("alternativeSequences").get(0).toString();
//                        }
//                        mutagenesis += "MUTAGEN " + feature.getJSONObject("location").getJSONObject("start").getString("value") + ";"
//                                + "note=" + feature.getJSONObject("alternativeSequence").getString("originalSequence") + "->"
//                                + fs + ";";
//                    }

                    if (feature.getString("type").equals("Natural variant"))
                    {
                        JSONObject disease_temp_data_map = new JSONObject();

                        disease_temp_data_map.put("type","Natural variant");
                        disease_temp_data_map.put("positions",feature.getJSONObject("location").getJSONObject("start").getString("value"));

                        String disease_alternativeSequence = parseStringJSONArray(feature.getJSONObject("alternativeSequence").getJSONArray("alternativeSequences"));
                        disease_temp_data_map.put("sequence",feature.getJSONObject("alternativeSequence").getString("originalSequence")+" -> "+""+disease_alternativeSequence);
                        disease_temp_data_map.put("description",feature.get("description"));
                        if (feature.get("evidences")==null || feature.getJSONArray("evidences").size()==0)
                            disease_temp_data_map.put("evidences","");
                        else
                            disease_temp_data_map.put("evidences",feature.getJSONArray("evidences").toJSONString());

                        List<JSONObject> featureCrossReferences_list = new ArrayList<>();
                        if (feature.get("featureCrossReferences")!=null && feature.getJSONArray("featureCrossReferences").size()>0)
                            featureCrossReferences_list = feature.getJSONArray("featureCrossReferences").toJavaList(JSONObject.class);

                        for (JSONObject featureCrossReferencesObj : featureCrossReferences_list)
                            featureCrossReferencesObj.put("url","https://www.ncbi.nlm.nih.gov/snp/"+featureCrossReferencesObj.getString("id"));

                        if (featureCrossReferences_list.size()>0)
                            disease_temp_data_map.put("featureCrossReferences",featureCrossReferences_list.toString());
                        else
                            disease_temp_data_map.put("featureCrossReferences",new ArrayList<>());

                        disease_temp_data_map.put("source_json",feature.toJSONString());
                        disease_list.add( disease_temp_data_map);
                    }
                }
                base_mutation_and_disease.put("involvement_in_disease", involvement_in_disease);
                base_mutation_and_disease.put("mutagenesis", mutagenesis);
                base_mutation_and_disease.put("p_id", base_general_infomation.get("id"));
                base_mutation_and_disease.put("disease_json", disease_list.toString());
                service.add_base_mutation_and_disease(base_mutation_and_disease);
                JSONObject base_post_translational_modification = new JSONObject();


                for (JSONObject comment : comments) {
                    if (comment.getString("commentType").equals("PTM")) {
                        ptm2 += "PTM:" +
                                comment.getJSONArray("texts").toJavaList(JSONObject.class).get(0).getString("value") + ";";
                    }

                }
                base_post_translational_modification.put("PTM2", ptm2);
                if (ptm2.length() > 0)
                    base_post_translational_modification.put("PTM1", unipro_entry);
                base_post_translational_modification.put("p_id", base_general_infomation.get("id"));
                service.add_base_post_translational_modification(base_post_translational_modification);
                String url4 = "https://research.bioinformatics.udel.edu/iptmnet/entry/" + unipro_entry;
                System.out.println(unipro_entry);
                Document doc4 = Jsoup.connect(url4).get();
                Element elements4 = doc4.getElementById("asSubTable-" + unipro_entry);
                List<JSONObject> lptm = new ArrayList<>();
                if (!ObjectUtils.isEmpty(elements4))
                    for (Element element : elements4.getElementsByTag("tr")) {
                        JSONObject base_ptm_details = new JSONObject();
                        base_ptm_details.put("p_id", base_general_infomation.get("id"));
                        if (!ObjectUtils.isEmpty(element.getElementsByTag("td"))) {
                            base_ptm_details.put("site", element.getElementsByTag("td").get(1).text());
                            base_ptm_details.put("ptm_type", element.getElementsByTag("td").get(2).text());
                            base_ptm_details.put("ptm_enzyme", element.getElementsByTag("td").get(3).text());
                            if (!ObjectUtils.isEmpty(element.getElementsByTag("td").get(5).getElementsByTag("a"))) {
                                base_ptm_details.put("source_all",
                                        element.getElementsByTag("td").get(5).getElementsByTag("a").get(0).attr("href"));
                                base_ptm_details.put("source_txt",
                                        element.getElementsByTag("td").get(5).text());

                            } else
                                base_ptm_details.put("source_all", "");
                            if (!ObjectUtils.isEmpty(element.getElementsByTag("td").get(6))) {
                                base_ptm_details.put("pmid",
                                        element.getElementsByTag("td").get(5).text());
                            } else
                                base_ptm_details.put("pmid", "");
                            lptm.add(base_ptm_details);
                        }

                    }
                if (lptm.size() > 0)
                    service.add_lptm(lptm);


                System.out.println("==========end "+unipro_entry);
            }

            JSONObject db = dservice.sel_database_info();
            JSONObject p = new JSONObject();
            p.put("sort", c_a_b.getString("batch"));
            p.put("status_name", "未更新");
            p.put("operator", "admin");
            p.put("database_size", (db.getDouble("data")
                    - Double.parseDouble(dbold.getString("data"))) + "M");
            p.put("database_length", (db.getInteger("rows")
                    - Integer.parseInt(dbold.getString(
                    "rows"))));
            service.add_his(p);

        }catch (Exception e){
            e.printStackTrace();
//            ubd(c_a_b,dbold);
        }
    }

    //25年3月27号， 抓取go 数据和 信号肽数据
    public void ubd2(JSONObject c_a_b,JSONObject dbold)  throws Exception{
        try {


//            List<JSONObject> list = new ArrayList(){{
//                add(new JSONObject(){{
//                    put("primaryAccession","Q02297");
//                }});
//            }};

            c_a_b.put("batch","1.0");

            //一、数据过滤用，把已经存在的数据过滤掉
            List<JSONObject> ex_list = service.sel_ex_list(c_a_b);
            for (JSONObject jsonObject : ex_list)
            {
                String unipro_entry = jsonObject.getString("unipro_entry");
//                boolean flag = false;
//                for (JSONObject object : ex_list)
//                {
//                    if (object.getString("unipro_entry").equals(unipro_entry)) {
//                        flag = true;
//                    }
//                }
//                if (flag) //全数据拉取
//                    continue;

                System.out.println("start+++++++++++++ "+unipro_entry);

//                if ("O95393".equals(unipro_entry))
//                    System.out.println("+++++");

                //二、处理获取数据
                //1.基本信息general_information  UniProtKB reviewed (Swiss-Prot)
                JSONObject info = JSONObject.parseObject(WuUtils.sendPost("https://rest.uniprot.org/uniprotkb/" + unipro_entry, new JSONObject().toJSONString()));
                JSONObject base_general_infomation = new JSONObject();



                //2.seqquence模块，kegg, gene
                JSONObject base_protein_sequence = new JSONObject();
                base_protein_sequence.put("complete_form", info.getJSONObject("sequence").getString("value"));
                base_protein_sequence.put("length", info.getJSONObject("sequence").getString("length"));
                List<JSONObject> features = info.getJSONArray("features").toJavaList(JSONObject.class);
                List<JSONObject> Signal = new ArrayList<>();
                List<JSONObject> Chain = new ArrayList<>();

                for (JSONObject feature : features) {
                    if (feature.getString("type").equals("Signal") ||
                            feature.getString("type").equals("Propeptide"))
                        Signal .add(feature);
                    if (feature.getString("type").equals("Chain"))
                        Chain .add( feature);
                }
                base_protein_sequence.put("precursor", "/");
                base_protein_sequence.put("precursor_length", "/");
                String precursor="";

                List<JSONObject> single_list = new ArrayList<>();
                for (JSONObject object : Signal)
                {
                    if (ObjectUtils.isEmpty(object) || ObjectUtils.isEmpty(object.get("location"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").get("start"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").getJSONObject("start").get("value"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").get("end"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").getJSONObject("end").get("value"))) {

                    } else {
                        int a = object.getJSONObject("location").getJSONObject("start").getInteger("value");
                        int b = object.getJSONObject("location").getJSONObject("end").getInteger("value");
                        precursor+=base_protein_sequence.getString("complete_form").substring(a-1, b)+"";

                        JSONObject tempDataJsonObject=new JSONObject();
                        tempDataJsonObject.put("start",a);
                        tempDataJsonObject.put("end",b);
                        tempDataJsonObject.put("type","signal");
                        tempDataJsonObject.put("length",(b-(a-1)));
                        tempDataJsonObject.put("data",precursor);
                        tempDataJsonObject.put("description",object.get("description"));
                        single_list.add(tempDataJsonObject);
                    }
                }
                if (single_list.size()>0)
                    base_protein_sequence.put("precursor", single_list.toString());


                base_protein_sequence.put("mature_form", "/");
                base_protein_sequence.put("mature_form_length", "/");
                String mature_form="";
                List<JSONObject> chain_list = new ArrayList<>();
                for (JSONObject object : Chain)
                {
                    if (ObjectUtils.isEmpty(object) || ObjectUtils.isEmpty(object.get("location"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").get("start"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").getJSONObject("start").get("value"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").get("end"))
                            || ObjectUtils.isEmpty(object.getJSONObject("location").getJSONObject("end").get("value")))
                    {
                    } else {
                        int a = object.getJSONObject("location").getJSONObject("start").getInteger("value");
                        int b = object.getJSONObject("location").getJSONObject("end").getInteger("value");
                        mature_form+=base_protein_sequence.getString("complete_form").substring(a-1, b )+"";

                        JSONObject tempDataJsonObject=new JSONObject();
                        tempDataJsonObject.put("start",a);
                        tempDataJsonObject.put("end",b);
                        tempDataJsonObject.put("type","China");
                        tempDataJsonObject.put("length",(b-(a-1)));
                        tempDataJsonObject.put("data",mature_form);
                        tempDataJsonObject.put("description",object.get("description"));

                        chain_list.add(tempDataJsonObject);
                    }
                }
                if (chain_list.size()>0)
                    base_protein_sequence.put("mature_form", chain_list.toString());
                base_protein_sequence.put("p_id", jsonObject.get("id"));
                service.update_base_protein_sequence(base_protein_sequence);


                List<JSONObject> comments = info.getJSONArray("comments").toJavaList(JSONObject.class);
                JSONObject base_protein_function = new JSONObject();
                JSONObject function = new JSONObject();
                for (JSONObject comment : comments)
                {
                    if (comment.getString("commentType").equals("FUNCTION"))
                        function = comment;
                }
                if (ObjectUtils.isEmpty(function.get("texts")))
                    base_protein_function.put("function", "");
                else
                    base_protein_function.put("function", function.getJSONArray("texts")
                            .toJavaList(JSONObject.class).toString());
                String  pubmed_id="";
                if (!ObjectUtils.isEmpty(function.get("texts")))
                {
                    for (JSONObject texts : function.getJSONArray("texts").toJavaList(JSONObject.class))
                    {
                        if (!ObjectUtils.isEmpty(texts.get("evidences")))
                        {
                            for (JSONObject evidences : texts.getJSONArray("evidences").toJavaList(JSONObject.class))
                            {
                                if (StringUnits.isNotNullOrEmpty(evidences.get("source"))&&
                                        evidences.getString("source").equals("PubMed"))
                                    pubmed_id+=evidences.getString("id")+";";
                            }
                        }
                    }
                }
                base_protein_function.put("pubmed_id", pubmed_id);

                List<JSONObject> uniProtKBCrossReferences = info.getJSONArray("uniProtKBCrossReferences").toJavaList(JSONObject.class);
                List<JSONObject> go = new ArrayList<>();
                for (JSONObject uniProtKBCrossReference : uniProtKBCrossReferences)
                {
                    if (uniProtKBCrossReference.getString("database").equals("GO"))
                        go.add(uniProtKBCrossReference);
                }
                String gene_ontology_biological_process = "",
                        gene_ontology_cellular_component = "",
                        gene_ontology_molecular_function = "";

                JSONArray temp_function_go_array = new JSONArray();
                for (JSONObject object : go)
                {
                    JSONObject temp_res_go_data_obj = new JSONObject();
                    JSONObject gop_term = object.getJSONArray("properties").toJavaList(JSONObject.class)
                            .stream().filter(t -> t.getString("key").equals("GoTerm")).collect(Collectors.toList())
                            .get(0);
                    JSONObject gop_evidence_type = object.getJSONArray("properties").toJavaList(JSONObject.class)
                            .stream().filter(t -> t.getString("key").equals("GoEvidenceType")).collect(Collectors.toList())
                            .get(0);
                    temp_res_go_data_obj.put("gop_evidence_type",gop_evidence_type.get("value"));
                    temp_res_go_data_obj.put("goId",object.get("id"));
                    temp_res_go_data_obj.put("goUrl","https://www.ebi.ac.uk/QuickGO/term/GO:"+object.get("id"));

                    if (gop_term.getString("value").contains("C:"))
                    {
                        temp_res_go_data_obj.put("ASPECT","Cellular Component");
                        temp_res_go_data_obj.put("ASPECT_type","C:");
                        temp_res_go_data_obj.put("TERM",gop_term.getString("value").replaceFirst("C:",""));
                    }
                    if (gop_term.getString("value").contains("P:"))
                    {
                        temp_res_go_data_obj.put("ASPECT","Biological Process");
                        temp_res_go_data_obj.put("ASPECT_type","P:");
                        temp_res_go_data_obj.put("TERM",gop_term.getString("value").replaceFirst("P:",""));
                    }
                    if (gop_term.getString("value").contains("F:"))
                    {
                        temp_res_go_data_obj.put("ASPECT","Molecular Function");
                        temp_res_go_data_obj.put("ASPECT_type","F:");
                        temp_res_go_data_obj.put("TERM",gop_term.getString("value").replaceFirst("F:",""));
                    }

                    if (!ObjectUtils.isEmpty(object.get("evidences")))
                        temp_res_go_data_obj.put("evidences",object.getJSONArray("evidences").toJavaList(JSONObject.class));

                    temp_function_go_array.add(temp_res_go_data_obj);
                }
                base_protein_function.put("gene_ontology_biological_process", temp_function_go_array.toJSONString());
                base_protein_function.put("gene_ontology_cellular_component", gene_ontology_cellular_component);
                base_protein_function.put("gene_ontology_molecular_function", gene_ontology_molecular_function);
                base_protein_function.put("p_id", jsonObject.get("id"));
                service.update_base_protein_fuction(base_protein_function);

                System.out.println("==========end "+unipro_entry);
            }

            System.out.println("跳出数据环了");
        }catch (Exception e){
            e.printStackTrace();
//            ubd(c_a_b,dbold);
        }
    }
    JSONObject j_reesult(List<JSONObject> l,String p){
        JSONObject result=new JSONObject();
        for (JSONObject jsonObject : l) {
            if (jsonObject.getString("database").equals(p)){
                result=jsonObject;
                break;
            }
        }
        return result;
    }

//    //抓取数据测试
//    public static void main1(String[] args) {
//        JSONObject j=JSONObject.parseObject(WuUtils.sendPost("https://rest.uniprot.org/uniprotkb/Q6UW88", new JSONObject().toJSONString()));
//        //蛋白名称
//        String recommended_name=j.getJSONObject("proteinDescription")
//                .getJSONObject("recommendedName")
//                .getJSONObject("fullName").getString("value");
//        //蛋白别名
//        String alternative_name=j.getJSONObject("proteinDescription")
//                .getJSONArray("alternativeNames").toJavaList(JSONObject.class).get(0)
//                .getJSONObject("fullName").getString("value");
//        //基因名称
//        String gene_name=j.getJSONArray("genes").toJavaList(JSONObject.class).get(0)
//                .getJSONObject("geneName").getString("value");
//        //基因别名
//        String gene_orf_name=j.getJSONArray("genes").toJavaList(JSONObject.class).get(0)
//                .getJSONArray("orfNames")
//                .toJavaList(JSONObject.class).get(0).getString("value");
//        //种属
//        String organism =j.getJSONObject("organism")
//                .getString("scientificName");
//        //基因id
//        String Gene_ID  ="";
//        //function功能
//        String function ="";
//        JSONObject j_params=new JSONObject();
//        j_params.put("fields","structure_3d,ft_strand,ft_helix,ft_turn,protein_families,cc_domain,ft_domain,accession,go_p,go_c,go_f,cc_induction,cc_developmental_stage,cc_tissue_specificity,xref_geneid,cc_disease,ft_mutagen");
//        j_params.put("query","Q6UW88");
//        JSONObject j1=JSONObject.parseObject(WuUtils.sendPost("https://rest.uniprot.org/uniprotkb/search?fields=accession,structure_3d,ft_strand,ft_helix,ft_turn,protein_families,cc_domain,ft_domain,accession,go_p,go_c,go_f,cc_induction,cc_developmental_stage,cc_tissue_specificity,xref_geneid,cc_disease,ft_mutagen&query=Q6UW88",j_params.toJSONString() ));
//        List<JSONObject> list0= j1.getJSONArray("results").toJavaList(JSONObject.class).get(0).getJSONArray("uniProtKBCrossReferences").toJavaList(JSONObject.class);
//        List<JSONObject> list=new ArrayList<>();
//        for (JSONObject jsonObject : list0)
//        {
//            if (jsonObject.getString("database").equals("GeneID")) {
//                Gene_ID=jsonObject.getString("id");
//            }
//        }
////        //gene数据库 拿数据 https://www.ncbi.nlm.nih.gov/gene/2255
////        if (StringUnits.isNotNullOrEmpty(Gene_ID))
////        {
////            get_gene_data();
////        }
//
//
//        //基因本体论（生物过程）F
//        String Gene_ontology_biological_process="";
//        //基因本体论（细胞成分）C
//        String Gene_ontology_cellular_component="";
//        //基因本体论（分子功能）P
//        String Gene_ontology_molecular_function="";
//        for (JSONObject jsonObject1 : list0) {
//            if (jsonObject1.getString("database").equals("GO")) {
//                list = jsonObject1.getJSONArray("properties").toJavaList(JSONObject.class);
//                for (JSONObject jsonObject : list) {
//                    if (jsonObject.getString("value").contains("F:")){
//                        Gene_ontology_biological_process+=jsonObject.getString("value")
//                                +"-"+jsonObject1.getJSONArray("evidences").toJavaList(JSONObject.class).get(0)
//                                .getString("id")+"-"+jsonObject1.getString("id").replaceAll("GO:","")+";";
//                    }
//                    if (jsonObject.getString("value").contains("C:")){
//                        Gene_ontology_cellular_component+=jsonObject.getString("value") +"-"+jsonObject1.getJSONArray("evidences").toJavaList(JSONObject.class).get(0)
//                                .getString("id")+"-"+jsonObject1.getString("id").replaceAll("GO:","")+";";
//                    }
//                    if (jsonObject.getString("value").contains("P:")){
//                        Gene_ontology_molecular_function+=jsonObject.getString("value") +"-"+jsonObject1.getJSONArray("evidences").toJavaList(JSONObject.class).get(0)
//                                .getString("id")+"-"+jsonObject1.getString("id").replaceAll("GO:","")+";";
//                    }
//                }
//            }
//        }
////        .get(0).getJSONArray("properties").toJavaList(JSONObject.class);
//
//
//        List<JSONObject> list3= j1.getJSONArray("results").toJavaList(JSONObject.class)
//                .get(0).getJSONArray("features").toJavaList(JSONObject.class);
//        //信号肽
//        String Signal="";
//        //信号肽
//        String Chain="";
//        for (JSONObject jsonObject : list3) {
//            if (jsonObject.getString("type").equals("Signal"))
//                Signal+=jsonObject.getJSONObject("location").getJSONObject("start")
//                        .getString("value")+"-"+jsonObject.getJSONObject("location").getJSONObject("end")
//                        .getString("value")+";";
//
//            if (jsonObject.getString("type").equals("Chain"))
//                Chain+=jsonObject.getJSONObject("location").getJSONObject("start")
//                        .getString("value")+"-"+jsonObject.getJSONObject("location").getJSONObject("end")
//                        .getString("value")+";";
//        }
//        List<JSONObject> features=j1.getJSONArray("results").toJavaList(JSONObject.class).get(0).getJSONArray("features").toJavaList(JSONObject.class);
//        List<JSONObject> comments=j1.getJSONArray("results").toJavaList(JSONObject.class).get(0).getJSONArray("comments").toJavaList(JSONObject.class);
//        //结构域
//        List<JSONObject> Domain=new ArrayList<>();
////        三维结构
//        String _3D_structure="";
//        List<JSONObject> Beta_strand=new ArrayList<>();
//        List<JSONObject> Helix=new ArrayList<>();
//        List<JSONObject> Turn=new ArrayList<>();
//        List<JSONObject> Protein_family=new ArrayList<>();
//        //诱变
//        List<JSONObject> Mutagenesis=new ArrayList<>();
//        //疾病
//        List<JSONObject> Disease=new ArrayList<>();
//
//        //发育阶段
//        List<JSONObject> Developmental_stage=new ArrayList<>();
//        //就职
//        List<JSONObject> Induction=new ArrayList<>();
//        //组织分布
//        List<JSONObject> Tissue_specificity_RNA  =new ArrayList<>();
//
//        for (JSONObject cs : comments) {
//            if (cs.getString("commentType").equals("FUNCTION"))
//                function=cs.getJSONArray("texts").toJavaList(JSONObject.class).get(0).getString("value");
//            if (cs.getString("commentType").equals("INDUCTION"))
//                Induction.add(cs);
//            if (cs.getString("commentType").equals("TISSUE SPECIFICITY"))
//                Tissue_specificity_RNA.add(cs);
//            if (cs.getString("commentType").equals("Developmental stage"))
//                Developmental_stage.add(cs);
//            if (cs.getString("commentType").equals("DISEASE")){
//                Disease.add(cs);
//            }
//        }
//        for (JSONObject feature : features) {
//            if (feature.getString("type").equals("Domain")){
//                Domain.add(feature);
//            }
//            if (feature.getString("type").equals("Beta strand")){
//                Beta_strand.add(feature);
//            }
//            if (feature.getString("type").equals("Helix")){
//                Helix.add(feature);
//            }
//            if (feature.getString("type").equals("Turn")){
//                Turn.add(feature);
//            }
//            if (feature.getString("type").equals("Mutagenesis")){
//                Mutagenesis.add(feature);
//            }
//        }
//
//    }



    // gene基因数据 https://www.ncbi.nlm.nih.gov/gene/2255
    private Map<String, String> get_gene_data(String gene_id) {

        String official_symbol = "";
        String also_known_as = "";
        String summary = "";
        Map<String, String> result_map = new HashMap<>();
        try {
            String url = "https://www.ncbi.nlm.nih.gov/gene/"+gene_id;
            Document doc = Jsoup.connect(url).get();

            if (doc.getElementsByTag("dl").size()==0)
                return result_map;

            Elements elements = doc.getElementsByTag("dl").get(0).children();
            boolean is_need_data = false;
            int type=0;
            for (Element element : elements)
            {
                String temp_tlement_ext = element.toString();
                if (temp_tlement_ext.indexOf("Official Symbol")>=0)
                {
                    is_need_data=true;
                    type=1;
                    continue;
                }
                if (temp_tlement_ext.indexOf("Also known as")>=0)
                {
                    is_need_data=true;
                    type=2;
                    continue;
                }
                if (temp_tlement_ext.indexOf("Summary")>=0)
                {
                    is_need_data=true;
                    type=3;
                    continue;
                }
                if (is_need_data&&type==1)
                {
                    official_symbol= parse_gene_data(temp_tlement_ext);
                    is_need_data=false;
                    continue;
                }
                if (is_need_data&&type==2)
                {
                    also_known_as=parse_gene_data(temp_tlement_ext);
                    is_need_data=false;
                    continue;
                }
                if (is_need_data&&type==3)
                {
                    summary=parse_gene_data(temp_tlement_ext);
                    is_need_data=false;
//                continue;
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        result_map.put("gene_official_symbol",official_symbol);
        result_map.put("gene_also_known_as",also_known_as);
        result_map.put("gene_summary",summary);
        return result_map;
    }
    private  String parse_gene_data(String gene_data_str) {

        String new_temp_tlement_ext = "";
        List<String> temp_tlement_ext_list = Arrays.asList(gene_data_str.split("\n"));
        if (temp_tlement_ext_list.size()>=3)
        {
            for (int gene_j = 1; gene_j < temp_tlement_ext_list.size()-1; gene_j++)
            {
                if (gene_j>1)
                    new_temp_tlement_ext = new_temp_tlement_ext+" ";
                new_temp_tlement_ext = new_temp_tlement_ext+temp_tlement_ext_list.get(gene_j);
            }
        }
        return new_temp_tlement_ext;
    }

    public String parseStringJSONArray(JSONArray arr)
    {
        if (arr==null || arr.size()==0)
            return "";


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.size(); i++)
        {
            if (i>0)
                sb.append(",");

            Object obj = arr.get(i);
            sb.append(obj.toString());
        }

        return sb.toString();
    }
    
//    private JSONObject changeListToMap(List list){
//        JSONObject res_ob = new JSONObject();
//
//        for (int i = 0; i < list.size(); i++)
//        {
//            Map<String,Object> temp_go_obj = (Map<String, Object>) list.get(i);
//            if (temp_go_obj.keySet().contains("key"))
//            {
//                JSONObject gop = object.getJSONArray("properties").toJavaList(JSONObject.class)
//                        .stream().filter(t -> t.getString("key").equals("GoTerm")).collect(Collectors.toList())
//                        .get(0);
//            }
//
//        }
//
//        return res_ob;
//    }



}
