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
public class BaseExpressionAndLocationShowHpaPo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**  */
	private Integer id;
	/** hpa的编码  */
	private String hpaCode;
	/**  组织特异性  */
	private String tissueSpecificity1;
	/**  组织分布RNA  */
	private String rnaExpressionOverview;
	/** */
	private String macValueRnaExpressionOverview;
	/**  组织分布DNA  */
	private String proteinExpressionOverview;
	/** 细胞分布  */
	private String singleCellTypeSpecificity;
	/**  */
	private Date createTime;
	/**  */
	private Date modifyTime;
	/** */
	private String url;

}