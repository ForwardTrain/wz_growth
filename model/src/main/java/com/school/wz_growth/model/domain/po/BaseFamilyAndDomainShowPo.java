package com.school.wz_growth.model.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 家庭和域名展示
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:17
 */
@Data
public class BaseFamilyAndDomainShowPo implements Serializable {
    private static final long serialVersionUID = 1L;

	/** */
	private Integer id;
	/**  base_drgf_id  */
	private Integer pId;
	/**  Protein family  */
	private String proteinFamily;
	/**  InterPro Accession  */
	private String interproAccession;
	/**  Domain */
	private String domain;
	/**  */
	private Date createTime;
	/**   */
	private Date modifyTime;

}