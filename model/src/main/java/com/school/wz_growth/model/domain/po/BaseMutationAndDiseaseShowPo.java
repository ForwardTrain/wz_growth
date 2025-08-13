package com.school.wz_growth.model.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 突变与疾病展示
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:16
 */
@Data
public class BaseMutationAndDiseaseShowPo implements Serializable {
    private static final long serialVersionUID = 1L;


	private Integer id;
	/**base_drgf_id */
	private Integer pId;
	/** Involvement in disease */
	private String involvementInDisease;
	/** Mutagenesis*/
	private String mutagenesis;
	/**  */
	private Date createTime;
	/***/
	private Date modifyTime;
	/** 突变数据*/
	private String diseaseJson;

}