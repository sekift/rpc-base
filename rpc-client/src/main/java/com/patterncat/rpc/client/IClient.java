package com.patterncat.rpc.client;

import com.patterncat.rpc.common.dto.RpcRequest;
import com.patterncat.rpc.common.dto.RpcResponse;
import org.apache.commons.lang3.tuple.Pair;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by patterncat on 2016/4/7.
 */
public interface IClient {
    void connect(InetSocketAddress socketAddress);
    public RpcResponse syncSend(RpcRequest request) throws InterruptedException;
    public RpcResponse asyncSend(RpcRequest request, Pair<Long, TimeUnit> timeout) throws InterruptedException;
    InetSocketAddress getRemoteAddress();
    void close();
}
