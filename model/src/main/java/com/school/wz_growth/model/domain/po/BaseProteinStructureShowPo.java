package com.school.wz_growth.model.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 蛋白质结构展示
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:16
 */
@Data
public class BaseProteinStructureShowPo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**   */
	private Integer id;
	/** base_drgf_id  */
	private Integer pId;
	/**  三维结构  */
	private String structure3d;
	/**  蛋白库编号  */
	private String pdbId;
	/**  AlphaFold identifier  */
	private String alphafoldIdentifier;
	/**  Beta strand  */
	private String betaStrand;
	/**  Helix  */
	private String helix;
	/**  Turn  */
	private String turn;
	/**   */
	private Date createTime;
	/**   */
	private Date modifyTime;
	/**  3D数量  */
	private Integer structureNum;

}