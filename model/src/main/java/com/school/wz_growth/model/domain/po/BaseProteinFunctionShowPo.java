package com.school.wz_growth.model.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 蛋白质功能展示
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:16
 */
@Data
public class BaseProteinFunctionShowPo implements Serializable {
    private static final long serialVersionUID = 1L;

	private Integer id;
	/** base_drgf_id*/
	private Integer pId;
	/** 功能 */
	private String function;
	/** 基因本体论（生物过程）*/
	private String geneOntologyBiologicalProcess;
	/**基因本体论（细胞成分） */
	private String geneOntologyCellularComponent;
	/**基因本体论（分子功能） */
	private String geneOntologyMolecularFunction;
	/** */
	private Date createTime;
	/** */
	private Date modifyTime;
	/** function中pubmed的id集，用分号分开 */
	private String functionPubmedIds;

}