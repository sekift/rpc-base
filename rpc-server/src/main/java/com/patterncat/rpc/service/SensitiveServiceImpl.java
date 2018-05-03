package com.patterncat.rpc.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.patterncat.rpc.Constants;
import com.patterncat.rpc.action.SensitiveAction;
import com.patterncat.rpc.common.annotation.ServiceExporter;
import com.patterncat.rpc.common.util.JsonUtil;
import com.patterncat.rpc.common.util.RpcJsonUtil;
import com.patterncat.rpc.common.util.StringUtil;

@ServiceExporter(value = "sensitiveSvr", targetInterface = SensitiveService.class, debugAddress = "127.0.0.1:9090")
public class SensitiveServiceImpl implements SensitiveService {
	private static Logger logger = LoggerFactory.getLogger(SensitiveServiceImpl.class);

	@Override
	public String execute(String... requestJson) {
		// json格式
		// "{'method':'vip.xxx','params':{'targetType':'2','userName':'abc','userId':'123','target':'123'}}";
		if (null == requestJson || requestJson.length < 1) {
			logger.error("请求参数为空，requestJson=" + requestJson);
			return RpcJsonUtil.getFailedResponse("0", "请求参数requestJson为空。");
		}
		String jsonStr = requestJson[0];
		if(StringUtil.isNullOrBlank(jsonStr)){
			System.out.println("jsonStr"+jsonStr);
			logger.error("请求参数为空，requestJson[0]=" + requestJson[0]);
			return RpcJsonUtil.getFailedResponse("0", "请求参数requestJson[0]为空。");
		}
		Map<String, Object> jsonReq = JsonUtil.toBean(jsonStr, Map.class); // 只取第一个即可

		Object methodObject = jsonReq.get("method");
		if (null == methodObject) {
			logger.error("方法为空，methodObject=" + methodObject);
			return RpcJsonUtil.getFailedResponse("0", "方法为空");
		}
		Object paramsObject = jsonReq.get("params");
		if (null == paramsObject) {
			logger.error("参数为空，paramsObject=" + paramsObject);
			return RpcJsonUtil.getFailedResponse("0", "参数为空");
		}
		String method = (String) methodObject;
		Map<String, Object> params = (Map<String, Object>) paramsObject;
		
		SensitiveAction action = new SensitiveAction();
		if(Constants.method.IS_SENSITIVE.equals(method)){
			return action.isSensitive(params);
		}else if(Constants.method.REPLACE_SENSITIVE.equals(method)){
			return action.replaceSensitive(params);
		}
		
		logger.error("方法不存在，method=" + method);
		return RpcJsonUtil.getFailedResponse("0", "方法不存在，method=" + method);
	}
}
