package com.mendao.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WebApiUtil {

	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	private static final String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符

	private static long latestCheck = System.currentTimeMillis();
	private static long interval = 1000 * 60 * 1;

	private static Map<String, String> phoneCodes = new HashMap<String, String>();

	public static String cdnUrl;
	public static String siteUrl;

	private static Pattern p = Pattern
			.compile("<a\\s+[^<>]*\\s+href=\"[^<>\"]*id=(\\d+)\"[^<>]*>(@.*?)</a>");

	public static String getImageUrl(String imgPath) {

		return getImageUrl(imgPath, ImageStandardEnum.MIN);
	}

	public static String getImageUrl(String imgPath, ImageStandardEnum type){
		String url;
		if (cdnUrl.endsWith("/")) {
			url = cdnUrl + type.folderName;
		} else {
			url = cdnUrl + "/" + type.folderName;
		}

		if (imgPath.startsWith("/")) {
			url += imgPath;
		} else {
			url += "/" + imgPath;
		}

		return url;
	}
	
	public static String formatComment(String content, Map<String, Object> map) {
		Matcher m = p.matcher(content);
		boolean result = m.find();
		while (result) {
			if (!map.containsKey(m.group(2))) {
				map.put(m.group(2), m.group(1));
			}
			result = m.find();
		}

		return content.replaceAll("<a\\s+[^<>]*>", "")
				.replaceAll("</a>", " ");
	}

	/**
	 * @param htmlStr
	 * @return 删除Html标签
	 */
	public static String delHTMLTag(String htmlStr) {
		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_space = Pattern
				.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
		return htmlStr.trim(); // 返回文本字符串
	}

	public static String getTextFromHtml(String htmlStr) {
		htmlStr = delHTMLTag(htmlStr);
		htmlStr = htmlStr.replaceAll("&nbsp;", "");

		return htmlStr;
	}

	public static void main(String[] args) {
		// String str =
		// "<div style='text-align:center;'> 整治“四风”   清弊除垢<br/><span style='font-size:14px;'> </span><span style='font-size:18px;'>公司召开党的群众路线教育实践活动动员大会</span><br/></div>";
		// System.err.println(str);
		// System.err.println(getTextFromHtml(str));
		
		double dis2 = getDistance(108.908336, 34.228562, 108.891268, 34.225816);
		System.err.println(dis2);
	}

	/**
	 * 
	 * @param lon1
	 *            第一点的经度
	 * @param lat1
	 *            第一点的纬度
	 * @param lon2
	 *            第二点的经度
	 * @param lat2
	 *            第二点的纬度
	 * @return 返回的距离，单位m
	 */
	public static double getDistance(double lon1, double lat1, double lon2,
			double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		System.err.println(a);
		double b = rad(lon1) - rad(lon2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137;// 赤道半径(单位m)
		return s;
	}

	/**
	 * 转化为弧度(rad)
	 * */
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 记录当前验证码
	 * 
	 * @param phonenum
	 * @param code
	 */
	public static synchronized void setPhoneCode(String phonenum, String code) {
		phoneCodes.put(phonenum, code + "__" + System.currentTimeMillis());

		long curTime = System.currentTimeMillis();
		if ((curTime - latestCheck) > interval) {
			for (String key : phoneCodes.keySet()) {
				long time = Long.valueOf(phoneCodes.get(key).split("__")[1]);
				time = curTime - time;
				if (time > interval) {
					phoneCodes.remove(key);
				}
			}

			latestCheck = System.currentTimeMillis();
		}
	}

	/**
	 * 校验验证码
	 * 
	 * @param phonenum
	 * @param code
	 * @return
	 */
	public static synchronized boolean checkPhoneCode(String phonenum,
			String code) {
		if (phoneCodes.containsKey(phonenum)) {
			String tmpcode = phoneCodes.get(phonenum).split("__")[0];
			phoneCodes.remove(phonenum);
			return code.equals(tmpcode);
		}

		return false;
	}
}
