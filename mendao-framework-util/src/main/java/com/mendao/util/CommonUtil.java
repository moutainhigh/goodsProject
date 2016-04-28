package com.mendao.util;

import com.mendao.common.util.StringUtil;

public class CommonUtil {

	/**
	 * 格式化排序字段。
	 * 其格式为"{###}.{###}.{###}.{###}"，每一段为三位数字。
	 * @param input
	 * @return
	 */
	public static String formatSortSeq(String input, int len){
		if(StringUtil.isBlank(input)){
			return StringUtil.alignRight("", len, '0');
		}
		String[] sorts = input.split("\\.");
		String sortSeq = "";
		for(String s: sorts){
			sortSeq += "." + StringUtil.alignRight(s, len, '0');
		}
		return sortSeq.substring(1);
	}
}
