package com.patterncat.rpc.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {
	/**
	 * 判断是否汉字
	 * 
	 * @param c
	 *            char 要判断的字符
	 * @return boolean
	 */
	public static boolean isChinese(char c) {
		String tmp = String.valueOf(c);
		return tmp.matches("[\u4e00-\u9fa5]+");
	}

	/**
	 * 判断整个字符串是否都是汉字
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isChinese(String str) {
		return str.matches("[\u4e00-\u9fa5]+");
	}

	/**
	 * 判断字符串是否包含汉字
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean containsChinese(String str) {
		if (!hasText(str)) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是超链接
	 * 
	 * @param a
	 *            String
	 * @return boolean
	 */
	public static boolean isA(String a) {
		if (!hasText(a)) {
			return false;
		}
		return a.matches("(<a\\s*href=[^>]*>)");
	}

	/**
	 * 判断是否URL
	 * 
	 * @param url
	 *            String
	 * @return boolean
	 */
	public static boolean isURL(String url) {
		if (!hasText(url)) {
			return false;
		}
		return url.matches("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
	}

	/**
	 * 判断是否Email字符串
	 * 
	 * @param email
	 *            String
	 * @return boolean
	 */
	public static boolean isEmail(String email) {
		if (!hasText(email)) {
			return false;
		}
		return email.matches("[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*");
	}

	/**
	 * 获得字符的低八位的值
	 * 
	 * @param c
	 *            char
	 * @return int
	 */
	public static int getLowBit(char c) {
		int intValue = (int) c;
		String hexValue = Integer.toHexString(intValue);
		int len = hexValue.length();
		if (len < 4) {
			for (int i = len; i < 4; i++) {
				hexValue = "0" + hexValue;
			}
		}
		hexValue = hexValue.substring(2, 4);
		return Integer.parseInt(hexValue, 16);
	}

	/**
	 * 获得字符的高八位的值
	 * 
	 * @param c
	 *            char
	 * @return int
	 */
	public static int getHighBit(char c) {
		int intValue = (int) c;
		String hexValue = Integer.toHexString(intValue);
		int len = hexValue.length();
		if (len < 4) {
			for (int i = len; i < 4; i++) {
				hexValue = "0" + hexValue;
			}
		}
		hexValue = hexValue.substring(0, 2);
		return Integer.parseInt(hexValue, 16);
	}

	/**
	 * 判断是否是一个整数串
	 * 
	 * @param value
	 *            String
	 * @return boolean
	 */
	public static boolean isInteger(String value) {
		if ((value.startsWith("+")) || (value.startsWith("-"))) {
			value = value.substring(1);
		}
		return isNumber(value);
	}

	/**
	 * 判断是否是一个数字串
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNumber(String value) {
		if (!hasText(value)) {
			return false;
		}
		boolean isInt = true;
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (!Character.isDigit(ch)) {
				isInt = false;
				break;
			}// if
		}// for
		return isInt;
	}

	/**
	 * 删除文本中的HTML标记
	 * 
	 * @param htmlText
	 *            String HTML文本
	 * @return String
	 */
	public static String removeHtmlTag(String htmlText) {
		String regex = "<.+?>";
		return htmlText.replaceAll(regex, "");
	}

	/**
	 * 根据正则表达式替换字符串
	 * 
	 * @param regex
	 *            String 正则表达式
	 * @param input
	 *            CharSequence 要匹配的字符序列
	 * @param replacement
	 *            替换字符串
	 * @param index
	 *            int
	 *            替换第几个匹配的,0表示替换所有匹配的(相当于replaceAll),1表示替换第一个匹配的(相当于replaceFirst
	 *            ),2表示替换第二个匹配的,以此类推.
	 * @return String
	 */
	public static String replace(String regex, CharSequence input, String replacement, int index) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		for (int i = 1; m.find(); i++) {
			if ((index == 0) || (index == i)) {
				m.appendReplacement(sb, replacement);
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static String replace(String regex, String excludeString, CharSequence input, String replacement, int index) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		for (int i = 1; m.find(); i++) {
			boolean flag = true;
			if (m.start() + excludeString.length() <= input.length()) {
				String sub = input.subSequence(m.start(), m.start() + excludeString.length()).toString();
				if (sub.equals(excludeString)) {
					flag = false;
				}
			}
			if (((index == 0) || (index == i)) && flag) {
				m.appendReplacement(sb, replacement);
			}

		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 根据正则表达式查找第count次匹配的开始索引
	 * 
	 * @param regex
	 *            正则表达式
	 * @param input
	 *            CharSequence 要匹配的字符序列
	 * @param count
	 *            第几次匹配
	 * @return int
	 */
	public static int indexOf(String regex, CharSequence input, int count) {
		int index = -1;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		for (int i = 1; m.find(); i++) {
			if (count == i) {
				index = m.start();
				break;
			}
		}
		return index;
	}

	/**
	 * 截取两段文本中间的文本
	 * 
	 * @param sourceText
	 *            String 源文本
	 * @param beginText
	 *            String 前段文本
	 * @param endText
	 *            String 后段文本
	 * @return String[]
	 */
	public static String[] splitText(String sourceText, String beginText, String endText) {
		String[] textArr = new String[3];

		if (beginText == null || endText == null) {
			textArr[0] = "";
			textArr[1] = sourceText;
			textArr[2] = "";
			return textArr;
		}
		int preBeginIndex = sourceText.indexOf(beginText.trim());
		// 前端字符串
		if (preBeginIndex < 0)
			return new String[] { "", sourceText, "" };
		else
			textArr[0] = sourceText.substring(0, preBeginIndex) + beginText;
		int preEndIndex = preBeginIndex + beginText.length();

		int posBeginIndex = sourceText.indexOf(endText.trim());
		// 中间字符串
		if (posBeginIndex <= 0) {
			textArr[1] = sourceText.substring(preEndIndex);
			textArr[2] = "";
			return textArr;
		} else
			textArr[1] = sourceText.substring(preEndIndex, posBeginIndex);

		int posEndIndex = posBeginIndex + endText.length();
		// 后端字符串
		if (posEndIndex >= sourceText.length()) {
			textArr[2] = "";
			return textArr;
		} else {
			textArr[2] = endText + sourceText.substring(posEndIndex);
		}
		return textArr;
	}

	public static void matcher(String regex, CharSequence input) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while (m.find()) {
			System.out.println("[" + m.start() + "," + m.end() + ") " + m.group());
		}
	}

	public static int matchCount(String regex, CharSequence input) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		int count = 0;
		while (m.find()) {
			count++;
		}
		return count;
	}

	public static boolean like(String inputText, String likeText) {
		StringBuilder regex = new StringBuilder();
		regex.append(".*");
		for (int i = 0; i < likeText.length(); i++) {
			char c = likeText.charAt(i);
			regex.append("[");
			regex.append(Character.toLowerCase(c));
			regex.append(Character.toUpperCase(c));
			regex.append("]");
		}
		regex.append(".*");
		return Pattern.matches(regex.toString(), inputText);
	}

	public static boolean likeSentence(String inputText, String likeText) {
		return likeSentence(inputText, likeText, ';', ' ');
	}

	public static boolean likeSentence(String inputText, String likeText, char sentenceSeparator, char wordSeparator) {
		String[] sentenceArr = inputText.split(sentenceSeparator + "");
		for (String sentence : sentenceArr) {
			if (startsWithIgnoreCase(sentence, likeText)) {
				return true;
			}
			String[] wordArr = sentence.split(wordSeparator + "");
			if (wordArr.length < likeText.length()) {
				continue;
			}
			boolean wordMatch = true;
			for (int i = 0; i < likeText.length(); i++) {
				if (!startsWithIgnoreCase(wordArr[i], likeText.charAt(i) + "")) {
					wordMatch = false;
					break;
				}
			}
			if (wordMatch) {
				return true;
			}
		}

		return false;
	}

	public static boolean likeSentence2(String inputText, String likeText) {
		return likeSentence2(inputText, likeText, ';', ' ');
	}

	public static boolean likeSentence2(String inputText, String likeText, char sentenceSeparator, char wordSeparator) {
		String[] sentenceArr = inputText.split(sentenceSeparator + "");

		String[] wordArr = sentenceArr[0].split(wordSeparator + "");
		for (String word : wordArr) {
			if (startsWithIgnoreCase(word, likeText)) {
				return true;
			}
		}
		if (sentenceArr.length < likeText.length()) {
			return false;
		}
		for (int i = 0; i < likeText.length(); i++) {
			String[] wordArr2 = sentenceArr[i].split(wordSeparator + "");
			boolean wordMatch = false;
			for (String word : wordArr2) {
				if (startsWithIgnoreCase(word, likeText.charAt(i) + "")) {
					wordMatch = true;
					break;
				}
			}
			if (!wordMatch) {
				return false;
			}
		}

		return true;
	}

	public static boolean startsWithIgnoreCase(String inputText, String prefix) {
		StringBuilder regex = new StringBuilder();
		for (int i = 0; i < prefix.length(); i++) {
			char c = prefix.charAt(i);
			regex.append("[");
			regex.append(Character.toLowerCase(c));
			regex.append(Character.toUpperCase(c));
			regex.append("]");
		}
		regex.append(".*");
		return Pattern.matches(regex.toString(), inputText);
	}

	public static boolean endsWithIgnoreCase(String inputText, String suffix) {
		StringBuilder regex = new StringBuilder();
		regex.append(".*");
		for (int i = 0; i < suffix.length(); i++) {
			char c = suffix.charAt(i);
			regex.append("[");
			regex.append(Character.toLowerCase(c));
			regex.append(Character.toUpperCase(c));
			regex.append("]");
		}
		return Pattern.matches(regex.toString(), inputText);
	}

	/**
	 * 当 text 不为 null 且长度不为 0
	 * 
	 * @param text
	 *            String
	 * @return boolean
	 */
	public static boolean hasLength(String text) {
		return (text != null) && (text.length() > 0);
	}

	/**
	 * text 不能为 null 且必须至少包含一个非空格的字符
	 * 
	 * @param text
	 *            String
	 * @return boolean
	 */
	public static boolean hasText(String text) {
		return hasLength(text) && Pattern.matches(".*\\S.*", text);
	}

	/**
	 * 根据URL截取不带后缀的文件名
	 * 
	 * @param url
	 *            String
	 * @return String
	 */
	public static String getPageName(String url) {
		int fullNameEndIndex = url.indexOf("?");
		if (fullNameEndIndex == -1) {
			fullNameEndIndex = url.length();
		}
		String temp = url.substring(0, fullNameEndIndex);
		String fullFileName = temp.substring(temp.lastIndexOf("/") + 1);
		String pageName = "";
		int fileNameEndIndex = fullFileName.lastIndexOf(".");
		if (fileNameEndIndex == -1) {
			fileNameEndIndex = fullFileName.length();
		}
		pageName = fullFileName.substring(0, fileNameEndIndex);
		return pageName;
	}

	public static String toString(String[] arr, String separator) {
		StringBuilder resultBuilder = new StringBuilder();
		for (String el : arr) {
			resultBuilder.append(el).append(separator);
		}
		String result = resultBuilder.toString();
		if (result.endsWith(separator)) {
			result = result.substring(0, result.length() - separator.length());
		}
		return result;
	}

	public static StringBuilder compareAndDeleteLastChar(StringBuilder sb, char c) {
		if ((sb.length() > 0) && (sb.charAt(sb.length() - 1) == c)) {
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		return sb;
	}

	public static StringBuffer compareAndDeleteLastChar(StringBuffer sb, char c) {
		if ((sb.length() > 0) && (sb.charAt(sb.length() - 1) == c)) {
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		return sb;
	}

	public static StringBuilder compareAndDeleteFirstChar(StringBuilder sb, char c) {
		if ((sb.length() > 0) && (sb.charAt(0) == c)) {
			sb = sb.deleteCharAt(0);
		}
		return sb;
	}

	public static StringBuffer compareAndDeleteFirstChar(StringBuffer sb, char c) {
		if ((sb.length() > 0) && (sb.charAt(0) == c)) {
			sb = sb.deleteCharAt(0);
		}
		return sb;
	}

	public static String lineSeparator() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.println();
		pw.close();
		return sw.toString();
	}

	/**
	 * 判断是否合法的手机号码. 手机号码为11位数字。 国家号码段分配如下： 　　 *
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188 　　 *
	 * 联通：130、131、132、152、155、156、185、186 　　 * 电信：133、153、180、189、（1349卫通）
	 * 
	 * @param mobiles
	 *            手机号码
	 * @return boolean
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static boolean isMobileNO2(String mobiles) {
		String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobiles);
		return m.find();
	}

	public static String firstLetterToUpperCase(String s) {
		String m = Character.toUpperCase(s.charAt(0)) + "";
		if (s.length() > 1) {
			m += s.substring(1);
		}

		return m;
	}
	
	/**
	 * 判断字符串是否为空字符。
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isBlank(String value) {
		boolean ret = false;
		if (value != null && value.equals("")) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 判断字符串是否为null。
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNull(String value) {
		return value == null ? true : false;
	}

	/**
	 * 判断字符串是否为空字符串或者null。
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrBlank(String value) {
		return isNull(value) || isBlank(value);
	}

	/**
	 * 截取字符串前面部分字符,后面加省略号.
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String trimWords(String str, int length) {
		String wordStr = str;

		if (wordStr == null) {
			return "";
		}
		if (wordStr.length() <= length) {
			return wordStr;
		}

		wordStr = wordStr.substring(0, length);
		wordStr += "...";
		return wordStr;
	}

	/**
	 * 身份证隐藏生日
	 * 
	 * @param number
	 * @return
	 */
	public static String hideIdentityNumber(String number) {
		String result = "";
		int len = number.length();
		if (isNullOrBlank(number)) {
			return result;
		}
		if (len == 18) {
			result = number.substring(0, 6) + "********" + number.substring(14);
			return result;
		} else if (len == 15) {
			result = number.substring(0, 6) + "******" + number.substring(12);
			return result;
		} else if (len >= 3) {
			result = number.substring(0, len / 3) + "******" + number.substring(2 * len / 3);
			return result;
		} else {
			return number;
		}
	}

	/**
	 * 姓名隐藏字
	 * 
	 * @param name
	 * @return
	 */
	public static String hideTureName(String name) {
		String result = "";
		if (isNullOrBlank(name)) {
			return result;
		}
		if (name.length() <= 1) {
			result = name;
			return result;
		} else if (name.length() == 2) {
			result = name.substring(0, 1) + "*";
			return result;
		} else {
			result = name.substring(0, 1) + "*" + name.substring(2);
			return result;
		}
	}

	public static void main(String args[]) {
		// System.out.println(getsubstring1("14545643&^*(&*("));
		// System.out.println(getsubstring1("*"));
		// System.out.println(getsubstring1("14545643&^*(&*("));
		System.out.println(hideTureName("陈国"));
		System.out.println(hideIdentityNumber("11010519710923582X"));
		System.out.println(hideIdentityNumber("11010519710923582X"));
		System.out.println(hideIdentityNumber("1101051971092358"));
		System.out.println(hideIdentityNumber("1"));
		System.out.println(hideIdentityNumber("11"));
		System.out.println(hideIdentityNumber("111"));
		System.out.println(hideIdentityNumber("11111"));
		System.out.println(hideIdentityNumber("111111"));
		System.out.println(hideIdentityNumber("11111111"));
		System.out.println(hideIdentityNumber("1111111111"));
		System.out.println(hideIdentityNumber("11111111111"));
		// System.out.println(getHideIdentityNumber("130503670401001"));
		// System.out.println(getHideIdentityNumber("1305036704010"));
		// String s1 = "http://www.baidu.com/index?url=http://www.baidu.com";
		// String s2 = "yuancihang@tom.com_";
		// System.out.println(StringUtil.isURL(s1));
		// System.out.println(StringUtil.isEmail(s2));
		// System.out.println(MessageFormat.format("{0}2222{1}----",
		// 10.1133f,"eee"));
		// System.out.println(NumberFormat.getPercentInstance().format(0.123));
		// System.out.println(NumberFormat.getCurrencyInstance().format(0.123));
		// System.out.println(String.format("qq%.2frr", 0.12345));
		// System.out.println(like("yuan", "AN"));
		// System.out.println(likeSentence("yuan ci hang;xi xi", "ych"));
		// System.out.println(likeSentence2("yuan xi;ci xi;hang", "x"));
		// System.out.println(isMobileNO("12016155153"));
	}
}
