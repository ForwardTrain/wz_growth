package com.school.wz_growth.common.sysEnum;

/**
 * 模块类型
 */
public enum ModelType {

	PLATFORM(1);

	private final int value;

	private ModelType(final int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
	
}
