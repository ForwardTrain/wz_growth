package com.school.wz_growth.common.sysEnum;

/**
 * 文件类型
 */
public enum FileType {

    日前(0),事前(1),实时(2),运营中心(3),实时电量(4),报价寻优(5),负荷预测(6);

	private final int value;

	private FileType(final int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
	
}
