package com.school.wz_growth.common.constant;

import java.util.HashSet;

public class WebConstant {

    public final static String SESSION_USER = "_session_user";

    public final static String MENU_SESSION_USER = "_menu_key";
    public final static String JWT_TOKEN_KEY_ = "MARKET_JWT_TOKEN_school_wy";
    public final static int  JWT_TOKEN_EXPIRE_= 60*60*24;




    public final static boolean is_product = false;

    public final static HashSet<String> PASS_REQUEST = new HashSet<String>(){{
        add("/SysAdmin/login");
        add("/SysMenu/all/menu");
        add("/SysMenu/xxxx");
        add("/TestData/importInfo");
        add("/DataManage/*");


        add("/SysBasic/sys_info");
        add("/HomeBasic/info");
        add("/HomeBasic/banner_sel_detail");

        add("/HomeThreeFile/sel_GM_list");
        add("/HomeThreeFile/sel_CT_list");
        add("/HomeThreeFile/sel_TL_list");
        add("/HomeThreeFile/sel_TL_list_export");
        add("/HomeThreeFile/sel_CT_list_export");
        add("/HomeThreeFile/sel_CT_list_detail");

        add("/TestData/upd_base_data");
        add("/HomeContent/sel_list");
        add("/HomeContent/sel_list_details");
        add("/HomeContent/sel_hot_list");
        add("/HomeContent/sel_support_list");
        add("/HomeContent/sel_support_list_details");
        add("/HomeContent/add_support");
        add("/HomeContent/search_combobox");
        add("/HomeContent/search_combobox2");
        add("/HomeContent/search_result_list");
        add("/HomeContent/left_search_result_list");
        add("/HomeContent/sel_by_key_words_search_result_list");
        add("/HomeContent/sel_key_words");
        add("/HomeContent/sel_key_words_example");
        add("/HomeContent/add_search_example_data");
        add("/HomeContent/query_example_data");
        add("/HomeContent/query_protein_data");
        add("/HomeContent/query_search_example_data");
        add("/HomeContent/sel_browse");
        add("/HomeContent/sel_databases_brief");
        add("/HomeContent/sys_cooperate_manage_list");
        add("/HomeContent/data_statistics_information");
        add("/HomeContent/clinical_research");
        add("/HomeContent/sel_item_list");
        add("/HomeContent/sel_search_details");
        add("/HomeBasic/sel_home_info");
        add("/HomeBasic/sel_contact_us");

        add("/HomeBasic/info");
        add("/HomeBasic/sel_view_recode");
        add("/Content/sel_support_list");
        add("/HomeBasic/sel_home_protein_num_count");
        add("/HomeContent/search_combobox_type_num_count");

        add("/Content/sel_hot_list");
        add("/Content/sel_list_details");
        add("/Content/sel_support_list_details");
        add("/HomeBasic/sel_home_family_count");

        add("/Literatures/sel_TL_list_test");
        add("/HomeThreeFile/sel_TL_list_search");
        add("/HomeContent/run");
        add("/HomeContent/runAli");
        add("/BizCount/sel_area_count_data");


        add("/durid/index.html");
        add("/protein/update");
        add("/HomeContent/export_search_result_txt");
        add("/HomeContent/export_search_result_fast");


        add("/drugInfo/save");
        add("/drugInfo/update");
        add("/drugInfo/");
        add("/drugInfo/page");
        add("/drugInfo/upload");
        add("/HomeThreeFile/dramp");
    }};



}
