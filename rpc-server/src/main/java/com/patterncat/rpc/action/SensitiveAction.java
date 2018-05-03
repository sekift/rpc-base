package com.patterncat.rpc.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.patterncat.rpc.Constants;
import com.patterncat.rpc.common.util.MapUtil;
import com.patterncat.rpc.common.util.RpcJsonUtil;
import com.patterncat.rpc.common.util.StringUtil;

public class SensitiveAction {
	private static final Logger logger = LoggerFactory.getLogger(SensitiveAction.class);
	static DirtWordFilter dirtWordService = null;
	static {
		try {
			dirtWordService = new DirtWordFilter(getResource(Constants.KEY_PATH));
			dirtWordService.init();
		} catch (IOException e) {
			logger.error("[敏感词]加载文件出错了，", e);
		}
	}

	private static String getResource(String name) throws IOException {
		return new File(new File("").getCanonicalFile(), name).getAbsolutePath();
	}

	// 返回是否是敏感词
	public String isSensitive(Map<String, Object> params) {
		// 只有一个参数：word
		String word = MapUtil.getParameter(params, "word", "");
		if (StringUtil.isNullOrBlank(word)) {
			logger.error("[敏感词]参数word为空，word=" + word);
			return RpcJsonUtil.getFailedResponse("-1", "参数word为空，word=" + word);
		}
		boolean isSensitive = false;
		try {
			isSensitive = dirtWordService.containsDirtWord(word);
		} catch (IOException e) {
			logger.error("[敏感词]判断是否敏感词出错了，", e);
			return RpcJsonUtil.getFailedResponse("-2", "判断是否敏感词出错了。");
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("isSensitive", isSensitive);
		return RpcJsonUtil.getSuccessResponse("1", "判断是否敏感词成功。", resultMap);
	}

	// 返回屏蔽后的词句
	public String replaceSensitive(Map<String, Object> params) {
		// 只有一个参数：word
		String word = MapUtil.getParameter(params, "word", "");
		if (StringUtil.isNullOrBlank(word)) {
			logger.error("[敏感词]参数word为空，word=" + word);
			return RpcJsonUtil.getFailedResponse("-1", "参数word为空，word=" + word);
		}
		String resultWord = null;
		try {
			resultWord = dirtWordService.doFilter(word);
		} catch (IOException e) {
			logger.error("[敏感词]屏蔽敏感词出错了，", e);
			return RpcJsonUtil.getFailedResponse("-2", "屏蔽敏感词出错了。");
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("replaceSensitive", resultWord);
		return RpcJsonUtil.getSuccessResponse("1", "屏蔽敏感词成功。", resultMap);
	}

}
