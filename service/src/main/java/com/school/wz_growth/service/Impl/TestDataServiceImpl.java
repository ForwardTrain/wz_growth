package com.school.wz_growth.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.dao.TestDataMapper;
import com.school.wz_growth.service.TestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;


@Service
public class TestDataServiceImpl implements TestDataService {

    @Autowired
    private TestDataMapper mapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_base_drgf(JSONObject requestVo)  {
        try {
            mapper.add_base_drgf(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_lptm(List<JSONObject> list)  {
        try {
            mapper.add_lptm(list);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }
    @Override
    public JSONObject sel_c_s_date_and_batch()  {
        try {
            JSONObject params_obj = new JSONObject(){{
                put("version_str","1.0");//TODO:拉取数据的版本号
            }};
            return mapper.sel_c_s_date_and_batch(params_obj);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
//        return null;
    }
    @Override
    public List<JSONObject> sel_ex_list(JSONObject requestVo)  {
        try {
            return mapper.sel_ex_list(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
//        return null;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_his(JSONObject vo)  {
        try {
             mapper.add_his(vo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
//        return ;
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_base_general_infomation(JSONObject requestVo)  {
        try {
            mapper.add_base_general_infomation(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }

    @Override
    public void add_DRGF_code(JSONObject requestVo) {
        try {
            requestVo.put("DRGF_code",mapper.select_DRGF_code_max_code());
            mapper.add_DRGF_code(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_base_protein_sequence(JSONObject requestVo)  {
        try {
            mapper.add_base_protein_sequence(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_base_protein_function(JSONObject requestVo)  {
        try {
            mapper.add_base_protein_function(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_base_expression_and_location(JSONObject requestVo)  {
        try {
            mapper.add_base_expression_and_location(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_base_protein_structure(JSONObject requestVo)  {
        try {
            mapper.add_base_protein_structure(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_base_family_and_domain(JSONObject requestVo)  {
        try {
            mapper.add_base_family_and_domain(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_base_protein_interaction(JSONObject requestVo)  {
        try {
            mapper.add_base_protein_interaction(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_base_mutation_and_disease(JSONObject requestVo)  {
        try {
            mapper.add_base_mutation_and_disease(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void add_base_post_translational_modification(JSONObject requestVo)  {
        try {
            mapper.add_base_post_translational_modification(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(t);
        }
    }



    @Override
    public List<JSONObject> sel_excel_data_list()  {
        try {
            return mapper.sel_excel_data_list();
        } catch (Throwable t) {
//            t.printStackTrace();
            throw new RuntimeException(t);
        }
//        return null;
    }
    @Override
    public void update_base_protein_sequence(JSONObject requestVo) throws Exception {
        try {
            mapper.update_base_protein_sequence(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }
    @Override
    public void update_base_protein_fuction(JSONObject requestVo) throws Exception {
        try {
            mapper.update_base_protein_fuction(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }




}

