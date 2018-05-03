package com.patterncat.rpc.client;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by patterncat on 2016-04-12.
 */
public class NettyClientFactory {

    private static ConcurrentHashMap<Class<?>,NettyClient> serviceClientMap = new ConcurrentHashMap<Class<?>, NettyClient>();

    public static NettyClient get(Class<?> targetInterface){
        NettyClient client = serviceClientMap.get(targetInterface);
        if(client != null && !client.isClosed()){
            return client;
        }
        //connect
        NettyClient newClient = new NettyClient();
        //TODO get from service registry
        String host = "127.0.0.1";//120.78.214.73
        int port = 9090;
        newClient.connect(new InetSocketAddress(host,port));
        serviceClientMap.putIfAbsent(targetInterface,newClient);
        return newClient;
    }


}
