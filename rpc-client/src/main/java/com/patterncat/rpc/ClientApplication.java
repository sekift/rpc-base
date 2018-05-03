package com.patterncat.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.patterncat.rpc.common.util.JsonRequest;
import com.patterncat.rpc.proxy.RpcProxyFactory;
import com.patterncat.rpc.service.SensitiveService;

/**
 * Created by patterncat on 2016-04-11.
 */
@SpringBootApplication
public class ClientApplication {

	// @Component
	// public static class BeanScannerConfigurer implements
	// BeanFactoryPostProcessor, ApplicationContextAware {
	//
	// private ApplicationContext applicationContext;
	//
	// public void setApplicationContext(ApplicationContext applicationContext)
	// throws BeansException {
	// this.applicationContext = applicationContext;
	// }
	//
	// public void postProcessBeanFactory(ConfigurableListableBeanFactory
	// beanFactory) throws BeansException {
	// RpcScanner scanner = new RpcScanner((BeanDefinitionRegistry)
	// beanFactory);
	// scanner.setResourceLoader(this.applicationContext);
	// scanner.scan("com.patterncat.rpc.");
	// }
	// }

	@Bean
	public RpcProxyFactory rpcProxyFactory() {
		return new RpcProxyFactory();
	}

	/**
	 * 也可以采用配置文件的方式 如果不想自己proxy,可以像dubbo那样扩展schema 或者自己scan指定包,在FactoryBean里头替换
	 * 
	 * @param rpcProxyFactory
	 * @return
	 */

	/*
	 * @Bean public HelloService buildHelloService(RpcProxyFactory
	 * rpcProxyFactory) { return rpcProxyFactory.proxyBean(HelloService.class,
	 * 100); }
	 */

	@Bean
	public SensitiveService buildSensitiveService(RpcProxyFactory rpcProxyFactory) {
		return rpcProxyFactory.proxyBean(SensitiveService.class, 1000L);
	}
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ClientApplication.class);
		app.setWebEnvironment(false);
		app.run(args);
	}
}
