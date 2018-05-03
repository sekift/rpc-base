package com.patterncat.rpc.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json操作工具
 */
public class RpcJsonUtil {

	/**
	 * 构造执行失败响应
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	public static String getFailedResponse(String code, String message) {
		String response = null;
		try {
			Map<Object,Object> map = new HashMap<Object,Object>();
			Map<Object,Object> responseMap = new HashMap<Object,Object>();
			responseMap.put("success", 0);
			responseMap.put("code", code);
			responseMap.put("message", message);
			responseMap.put("data", map);
			// response = JSONObject.fromObject(responseMap).toString();
			response = JsonUtil.toJson(responseMap);
		} catch (Exception e) {
		}

		return response;
	}

	/**
	 * 构造执行成功响应
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	public static String getSuccessResponse(String code, String message) {
		Map<Object,Object> map = new HashMap<Object,Object>();
		return getSuccessResponse(code, message, map);
	}

	/**
	 * 构造执行成功响应
	 * 
	 * @param code
	 * @param message
	 * @param list
	 * @return
	 */
	public static String getSuccessResponse(String code, String message,
			List<Object> list) {
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("list", list);
		return getSuccessResponse(code, message, map);
	}

	/**
	 * 构造执行成功响应
	 * 
	 * @param code
	 * @param message
	 * @param map
	 * @return
	 */
	public static String getSuccessResponse(String code, String message, Map<? extends Object,Object> map) {
		String response = null;
		try {
			Map<Object,Object> responseMap = new HashMap<Object,Object>();
			responseMap.put("success", 1);
			responseMap.put("code", code);
			responseMap.put("message", message);
			responseMap.put("data", map);
			// response = JSONObject.fromObject(responseMap).toString();
			response = JsonUtil.toJson(responseMap);
		} catch (Exception e) {
		}
		return response;
	}

	/**
	 * 构造执行成功响应
	 * 
	 * @param code
	 * @param message
	 * @param map
	 * @param list
	 * @return
	 */
	public static String getSuccessResponse(String code, String message,
			Map<Object,Object> map, List<Object> list) {
		map.put("list", list);
		return getSuccessResponse(code, message, map);
	}
	
	/**
	 * 把json字符串转换为Map
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map getRequestMap(String request){
		// JSONObject jsonObjct = JSONObject.fromObject(request);
		Map<Object,Object> requestMap = JsonUtil.toBean(request,
				HashMap.class);
		return requestMap;
	}
	
	public static void main(String[] args) {
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("id", "123");
		map1.put("name", "a");
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("id", "321");
		map2.put("name", "b");
		
		List<Object> list = new ArrayList<Object>();
		list.add(map1);
		list.add(map2);
		
		Map<String,Object> dMap = new HashMap<String,Object>();
		dMap.put("total", "2");
		dMap.put("list",list);
		
		System.out.println(getSuccessResponse("1","ok",list));
	}
}
