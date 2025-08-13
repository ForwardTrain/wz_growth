package com.school.wz_growth.model.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 表情和位置展示
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:17
 */
@Data
public class BaseExpressionAndLocationShowPo implements Serializable {
    private static final long serialVersionUID = 1L;

	/** */
	private Integer id;
	/**  base_drgf_id  */
	private Integer pId;
	/**  发育阶段  */
	private String developmentalStage;
	/** 就职 */
	private String induction;
	/**  组织特异性1  */
	private String tissueSpecificity1;
	/** 组织特异性2 */
	private String tissueSpecificity2;
	/**  组织特异性3 */
	private String tissueSpecificity3;
	/**  细胞类型特异性2 */
	private String cellTypeSpecificity2;
	/**  亚细胞定位  */
	private String subcellularLocation;
	/**  */
	private Date createTime;
	/**  */
	private Date modifyTime;
	/** 组织分布RNA  */
	private String rnaExpressionOverview;
	/** 组织分布DNA  */
	private String proteinExpressionOverview;
	/** 细胞分布  */
	private String tissueSpecificityRna;
	/** 细胞分布 */
	private String singleCellTypeSpecificity;
	/**  非人源的bgee数据来源  */
	private String expressionSpecificity;

}