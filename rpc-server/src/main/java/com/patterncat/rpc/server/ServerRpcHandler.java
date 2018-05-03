package com.patterncat.rpc.server;

import com.patterncat.rpc.common.dto.RpcRequest;
import com.patterncat.rpc.common.dto.RpcResponse;
import com.patterncat.rpc.common.exception.ServerException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by patterncat on 2016/4/6.
 */
public class ServerRpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(ServerRpcHandler.class);

    private final Map<String, Object> serviceMapping;

    public ServerRpcHandler(Map<String, Object> serviceMapping) {
        this.serviceMapping = serviceMapping;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        RpcResponse response = new RpcResponse();
        response.setTraceId(rpcRequest.getTraceId());
        try {
            logger.info("server handle request:{}",rpcRequest);
            Object result = handle(rpcRequest);
            response.setResult(result);
        } catch (Throwable t) {
            logger.error(t.getMessage(),t);
            response.setError(t);
        }
        channelHandlerContext.writeAndFlush(response);
    }

    private Object handle(RpcRequest request) throws Throwable {
        String className = request.getClassName();
        Object serviceBean = serviceMapping.get(className);

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        RpcResponse response = new RpcResponse();
        if(cause instanceof ServerException){
            response.setTraceId(((ServerException) cause).getTraceId());
        }
        response.setError(cause);
        ctx.writeAndFlush(response);
    }
}
