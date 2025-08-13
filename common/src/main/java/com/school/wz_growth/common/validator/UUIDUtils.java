package com.school.wz_growth.common.validator;

import org.apache.commons.codec.net.URLCodec;

import java.net.URLDecoder;
import java.util.UUID;

public class UUIDUtils {

	/**
	 * 获取UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 去掉字母和-
	 * 
	 * @param count
	 *            指定位数
	 */
	public static String getUUID(int count) {
		String s = UUID.randomUUID().toString();
		return s.replaceAll("[a-z|-]", "").substring(0, count);
	}


	public static void main(String[] args) {




	}
}
