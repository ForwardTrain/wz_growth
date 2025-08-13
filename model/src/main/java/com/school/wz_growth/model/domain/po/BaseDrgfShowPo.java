package com.school.wz_growth.model.domain.po;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * kegg数据表
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:17
 */

@Data
public class BaseDrgfShowPo implements Serializable {
    private static final long serialVersionUID = 1L;

	/** */
	private Integer id;
	/** 条目名称 */
	private String entryName;
	/**  drgf_id */
	private String drgfId;
	/**  */
	private Date createTime;
	/** */
	private Date modifyTime;
	/**  路径  */
	private String pathways;
	/** * 拉取时间 */
	private String cSDate;
	/**  批次 */
	private String batch;
	/** */
	private Integer pId;
	/**  */
	private String pathwayData;

}