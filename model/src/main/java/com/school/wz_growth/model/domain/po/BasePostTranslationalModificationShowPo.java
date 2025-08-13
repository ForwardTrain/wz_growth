package com.school.wz_growth.model.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 翻译后修饰展示
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:16
 */
@Data
public class BasePostTranslationalModificationShowPo implements Serializable {
    private static final long serialVersionUID = 1L;

	private Integer id;
	/** base_drgf_id */
	private Integer pId;
	/**PTM1 */
	private String ptm1;
	/** PTM2 */
	private String ptm2;
	/** */
	private Date createTime;
	/**  */
	private Date modifyTime;

}