package com.school.wz_growth.model.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 蛋白质序列展示
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:16
 */
@Data
public class BaseProteinSequenceShowPo implements Serializable {
    private static final long serialVersionUID = 1L;

	private Integer id;
	/**  base_drgf_id  */
	private Integer pId;
	/**  完整的表格  */
	private String completeForm;
	/**  */
	private String length;
	/**  信号肽 */
	private String precursor;
	/**  */
	private String precursorLength;
	/**  成熟形式  */
	private String matureForm;
	/**   */
	private String matureFormLength;
	/** */
	private Date createTime;
	/**  */
	private Date modifyTime;

}