package com.patterncat.rpc.common.exception;

/**
 * Created by patterncat on 2016-04-07.
 */
public class ServerException extends RpcException{

    private String traceId;

    public ServerException(String traceId, final Exception cause) {
        super(cause);
        this.traceId = traceId;
    }

    public String getTraceId() {
        return traceId;
    }
}
