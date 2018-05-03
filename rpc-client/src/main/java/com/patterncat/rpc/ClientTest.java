package com.patterncat.rpc;

import com.patterncat.rpc.common.util.JsonRequest;
import com.patterncat.rpc.common.util.JsonResponse;
import com.patterncat.rpc.proxy.RpcProxyFactory;
import com.patterncat.rpc.service.SensitiveService;

public class ClientTest {
	static SensitiveService sensitiveService = null;
	static {
		RpcProxyFactory rpcProxyFactory = new RpcProxyFactory();
		sensitiveService = rpcProxyFactory.proxyBean(SensitiveService.class,
				1000L);
	}

	// isSensitive
	public static boolean isSensitive(String word) throws Exception {
		JsonRequest request = JsonRequest.newInstance();
		request.setMethod("method.isSensitive");// replaceSensitive
		request.setParams("word", word);
		String[] strs = { request.toJson() };
		String responseJson = sensitiveService.execute(strs);
		JsonResponse response = JsonResponse.newInstance(responseJson);
		System.out.println(response.getDataMap());
		return false;
	}

	// replaceSensitive
	public static String replaceSensitive(String word) throws Exception {
		JsonRequest request = JsonRequest.newInstance();
		request.setMethod("method.replaceSensitive");
		request.setParams("word", word);
		String[] strs = { request.toJson() };
		String responseJson = sensitiveService.execute(strs);
		JsonResponse response = JsonResponse.newInstance(responseJson);
		System.out.println(response.getDataMap());
		return null;
	}

	public static void main(String args[]) {
		try {
			replaceSensitive("是哪的日本，迟到都是皇军猪列，还授予了首相");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
