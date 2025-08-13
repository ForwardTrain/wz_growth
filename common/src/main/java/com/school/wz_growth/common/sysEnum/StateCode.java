package com.school.wz_growth.common.sysEnum;

/**
 *
 */
public enum StateCode {

    不可用(0), 可用(1);
	private final int value;

	private StateCode(final int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
	
}
