package com.school.wz_growth.model.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ptm详情
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:16
 */
@Data
public class BasePtmDetailsShowPo implements Serializable {
    private static final long serialVersionUID = 1L;

	private Integer id;
	/** */
	private String site;
	/**  */
	private String ptmType;
	/**  */
	private String ptmEnzyme;
	/** */
	private String sourceAll;
	/** */
	private String scoreAll;
	/** */
	private Date createTime;
	/**  */
	private Integer pId;
	/** */
	private String pmid;
	/** */
	private String sourceTxt;

}