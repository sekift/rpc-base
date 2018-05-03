package com.patterncat.rpc.common.util;

import java.util.Map;

public class MapUtil {

	/**
	 * 取boolean参数值
	 * 
	 */
	public static boolean getBooleanParameter(Map<?,?> params, String name, boolean defaultVal) {
		boolean value = defaultVal;
		try {
			String temp = params.get(name).toString();
			if ("true".equals(temp) || "on".equals(temp)) {
				return true;
			} else if ("false".equals(temp) || "off".equals(temp)) {
				return false;
			}
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * 取double参数值
	 * 
	 */
	public static double getDoubleParameter(Map<?,?> params, String name, double defaultNum) {
		double num = defaultNum;
		try {
			Object temp = params.get(name);
			if (temp != null && !temp.equals("")) {
				num = Double.parseDouble(temp.toString());
			}
		} catch (Exception e) {
		}
		return num;
	}

	/**
	 * 取int参数值
	 * 
	 */
	public static int getIntParameter(Map<?,?> params, String name, int defaultNum) {
		int num = defaultNum;
		try {
			Object temp = params.get(name);
			if (temp != null) {
				num = Integer.parseInt(temp.toString());
			}
		} catch (Exception ignored) {
		}
		return num;
	}

	/**
	 * 取long参数值
	 * 
	 */
	public static long getLongParameter(Map<?,?> params, String name, long defaultNum) {
		long num = defaultNum;
		try {
			Object temp = params.get(name);
			if (temp != null) {
				num = Long.parseLong(temp.toString());
			}
		} catch (Exception ignored) {
		}
		return num;
	}

	/**
	 * 取string 参数值
	 * 
	 */
	public static String getParameter(Map<?,?> params, String name, String defaultValue) {
		String value = defaultValue;
		try {
			Object temp = params.get(name);
			if (temp != null) {
				value = temp.toString();
			}
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * 取参数值
	 * 
	 * @param <T>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getTParameter(Map<?,?> params, String name, T defaultValue) {
		T value = defaultValue;
		try {
			Object temp = params.get(name);
			if (temp != null) {
				value = (T) temp;
			}
		} catch (Exception e) {
		}
		return value;
	}
}
