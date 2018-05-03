package com.patterncat.rpc.common.exception;

/**
 * Created by patterncat on 2016/4/7.
 */
public class ClientCloseException extends RpcException{

    private static final String MESSAGE = "Can't close this client, beacuse the client didn't connect a server.";

    public ClientCloseException() {
        super(MESSAGE);
    }
}
