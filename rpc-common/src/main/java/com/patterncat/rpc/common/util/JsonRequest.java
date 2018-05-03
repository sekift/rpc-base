package com.patterncat.rpc.common.util;

import java.util.HashMap;
import java.util.Map;

public class JsonRequest {

	/**
	 * 创建实例
	 * 
	 * @return
	 */
	public static JsonRequest newInstance() {
		return new JsonRequest();
	}

	/**
	 * 方法名称
	 */
	private String method = "";

	/**
	 * 参数Map
	 */
	private Map<Object, Object> params = new HashMap<Object, Object>();

	/**
	 * 头部Map
	 */
	private Map<Object, Object> header = new HashMap<Object, Object>();

	/**
	 * 私有构造函数
	 */
	private JsonRequest() {
	}

	/**
	 * 设置方法名称
	 * 
	 * @param method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 设置参数
	 * 
	 * @param key
	 * @param value
	 */
	public void setParams(Object key, Object value) {
		params.put(key, value);
	}

	/**
	 * 设置头部
	 * 
	 * @param key
	 * @param value
	 */
	public void setHeader(Object key, Object value) {
		header.put(key, value);
	}

	/**
	 * 获取Method信息
	 * 
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 获取Params信息
	 * 
	 * @return the params
	 */
	public Map<Object, Object> getParams() {
		return params;
	}

	/**
	 * 获取Header信息
	 * 
	 * @return the header
	 */
	public Map<Object, Object> getHeader() {
		return header;
	}

	/**
	 * 获取JSON请求字符串
	 * 
	 * @return
	 */
	public String toJson() {
		String request = null;
		try {
			Map<Object, Object> requestMap = new HashMap<Object, Object>();
			requestMap.put("method", method);
			requestMap.put("header", header);
			requestMap.put("params", params);
			request = JsonUtil.toJson(requestMap);
		} catch (Exception e) {
		}
		return request;
	}

	/**
	 * 获取请求字符串
	 * 
	 * @return
	 */
	public String toString() {
		String request = null;
		try {
			Map<Object, Object> requestMap = new HashMap<Object, Object>();
			requestMap.put("method", method);
			requestMap.put("header", header);
			requestMap.put("params", params);
			request = JsonUtil.toJson(requestMap);
		} catch (Exception e) {
		}
		return request;
	}

}
