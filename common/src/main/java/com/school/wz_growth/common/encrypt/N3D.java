package com.school.wz_growth.common.encrypt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class N3D {
	private long lower;
	private long upper;
	private int radix;
	private long keyCode;
	private char[][] dict;
	
	public N3D(String key, long lower, long upper) {
		this.lower = lower;
		this.upper = upper;
		if (!isUnsigned(this.lower)||!isUnsigned(upper)) {
			throw new IllegalArgumentException("Parameter is error.");
		}
		
		if (this.upper <= this.lower) {
			throw new IllegalArgumentException("The upper must be greater than the lower.");
		}
		
		if (key == null || key.length() == 0) {
			throw new IllegalArgumentException("The key is error.");
		}
		
		char[] charMap = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		this.radix = charMap.length;
		this.keyCode = 0;
		for(int i = 0, n = 0, ref = key.length(); 0 <= ref ? n < ref:n > ref; i = (0 <= ref ? ++n:--n)) {
			int charCode = (int)key.charAt(i);
			if (charCode > 127) {
				throw new IllegalArgumentException("The key is error.");
			}
			this.keyCode += charCode * Math.pow(128, i % 7);
		}
		
		if ((this.keyCode + this.radix) < this.upper) {
			throw new IllegalArgumentException("The secret key is too short.");
		}
		long diff = this.keyCode - this.radix;
		int j = 0;
		this.dict = new char[this.radix][this.radix];
		while (diff < this.keyCode) {
			int k = this.radix;
			int l = 0;
			
			while (k > 0) {
				int s = (int) (diff % k);
				this.dict[j][l] = charMap[s];
				charMap[s] = charMap[k - 1];
				k--;
				l++;
			}
			charMap = Arrays.copyOf(this.dict[j], this.radix);
			diff++;
			j++;
		}
		
	}
	
	private boolean isUnsigned(long num) {
		return num > 0;
	}
	
	public String encrypt(long num) {
		if (!isUnsigned(num) || num > this.upper || num < this.lower) {
			throw new IllegalArgumentException("Parameter is error.");
		}
		long diff = this.keyCode - num;
		int m = (int)(diff % this.radix);
		char[] map = this.dict[m];
		List<Character> result = new ArrayList<>();
		int s = 0;
		result.add(this.dict[0][m]);
		while (diff > this.radix) {
			diff = (diff -m) / this.radix;
			m = (int)(diff % this.radix);
			if ((s = m + s) >= this.radix) {
				s -= this.radix;
			}
			result.add(map[s]);
		}
		StringBuilder sb = new StringBuilder();
		for(Character ch: result) {
			sb.append(ch.toString());
		}
		return sb.toString();
	}
	
	public long decrypt(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("Parameter is error.");
		}
		char chars[] = str.toCharArray();
		int len = chars.length;
		
		
		long result = joinChars(this.dict[0]).indexOf(chars[0]);
		if (result < 0) {
			throw new IllegalArgumentException("Invalid string.");
		}
		String map = joinChars(this.dict[(int)result]);
		int s = 0, t = 0;
		for (int i =1, n = 1, ref = len; 1 <= ref ? n < ref: n > ref; i = (1 <= ref ? ++n : --n)) {
			int j = map.indexOf(chars[i]);
			if (j < 0) {
				throw new IllegalArgumentException("Invalid string.");
			}
			if ((s = j - t) < 0) {
				s += this.radix;
			}
			result += s * Math.pow(this.radix, i);
			t = j;
		}
		result = this.keyCode - result;
		if (result > this.upper || result < this.lower) {
			throw new IllegalArgumentException("Invalid string.");
		}
		return result;
	}
	
	private String joinChars(char[] chars) {
		StringBuilder sb = new StringBuilder();
		for(char ch: chars) {
			sb.append(ch);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		N3D n3d = new N3D("11EdDIaqpcim", 1, 4294967295L);
		String encodedStr = n3d.encrypt(27);
		System.out.println(encodedStr);
		System.out.println(n3d.decrypt(encodedStr));
	}
}
