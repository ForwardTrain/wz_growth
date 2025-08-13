package com.school.wz_growth.model.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 蛋白质相互作用展示
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:16
 */
@Data
public class BaseProteinInteractionShowPo implements Serializable {
    private static final long serialVersionUID = 1L;

	private Integer id;
	/** base_drgf_id */
	private Integer pId;
	/**Protein-protein interaction*/
	private String proteinProteinInteraction;
	/** Protein complex*/
	private String proteinComplex;
	/** Ligand-Receptor interaction*/
	private String ligandReceptorInteraction;
	/** Pubmed ID */
	private String pubmedId;
	/**  */
	private Date createTime;
	/**  */
	private Date modifyTime;
	/**  */
	private String svg;

}