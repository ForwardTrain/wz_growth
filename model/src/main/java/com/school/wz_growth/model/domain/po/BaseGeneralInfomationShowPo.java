package com.school.wz_growth.model.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基因一般信息展示
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:16
 */
@Data
public class BaseGeneralInfomationShowPo implements Serializable {
    private static final long serialVersionUID = 1L;

	private Integer id;
	/**  蛋白质名称  */
	private String proteinName;
	/**  蛋白简称  */
	private String shortNames;
	/**  基因名称  */
	private String geneNames;
	/**  别名  */
	private String alsoKnownAs;
	/**  基因id  */
	private String geneId;
	/**  生物  */
	private String organism;
	/** unipro入口 */
	private String uniproEntry;
	/**   */
	private Date createTime;
	/**    */
	private Date modifyTime;
	/**  拉取时间 */
	private String cSDate;
	/**  批次  */
	private String batch;
	/** 操作人  */
	private String operator;
	/**  蛋白名称加入口编码做唯一判断  */
	private String proteinNameAndUniproEntry;
	/** 关键字  */
	private String keyWords;
	/**   */
	private String proteinStatus;
	/**   */
	private String family;
	/**   */
	private String functionPubmedIds;
	/**    */
	private String geneOfficialSymbol;
	/**   */
	private String geneAlsoKnownAs;
	/**  */
	private String geneSummary;

}