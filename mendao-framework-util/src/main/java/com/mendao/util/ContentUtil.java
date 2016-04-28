package com.mendao.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentUtil {


	public static String getFirstImg(String content, String prefix) {
		if (null != content && !content.trim().equals("")) {
			Pattern p = Pattern.compile(
					"<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>",
					Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(content);
			while (m.find()) {
				// System.out.println(m.group());
				// System.out.println(m.group(1));
				return m.group(1).replace(prefix, "");
			}
		}
		return null;
	}

	public static List<String> getAllImgs(String content, String prefix) {
		List<String> imgs = new ArrayList<String>();
		if (null != content && !content.trim().equals("")) {
			Pattern p = Pattern.compile(
					"<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>",
					Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(content);

			while (m.find()) {
				// System.out.println(m.group());
				// System.out.println(m.group(1));
				imgs.add(m.group(1).replace(prefix, ""));
			}
		}
		return imgs;
	}
	
	public static String formatContent(String content){
		//移除迅雷组件创建的html
		return content.replaceAll("<div[^>]+id\\s*=\\s*['\"]xunlei_com_thunder_helper_plugin*([^'\"]+)['\"][^>]*>\\s*</div>", "");
	}
}
