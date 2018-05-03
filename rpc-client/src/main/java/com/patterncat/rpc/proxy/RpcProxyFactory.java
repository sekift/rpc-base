package com.patterncat.rpc.proxy;

import com.patterncat.rpc.client.NettyClient;
import com.patterncat.rpc.client.NettyClientFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * Created by patterncat on 2016-04-12.
 */
@Component
public class RpcProxyFactory {

    public <T> T proxyBean(Class<?> targetInterface,long timeoutInMillis){
        NettyClient client = NettyClientFactory.get(targetInterface);
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{targetInterface}, new RpcProxy(client,Pair.of(timeoutInMillis,TimeUnit.MILLISECONDS)));
    }
}
