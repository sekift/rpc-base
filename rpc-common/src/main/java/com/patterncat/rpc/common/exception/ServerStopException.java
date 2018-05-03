package com.patterncat.rpc.common.exception;

/**
 * Created by patterncat on 2016-04-07.
 */
public class ServerStopException extends RpcException{

    private static final String MESSAGE = "Can't stop this server, because the server didn't start yet.";

    public ServerStopException() {
        super(MESSAGE);
    }
}
