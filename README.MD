# rpc-base
根据 https://github.com/seasidesky/netty-rpc 做的netty-rpc，主要是为bubbt提供一些基础服务，例如屏蔽敏感词、查询IP归属地、查询手机号归属地等。<br />

## 更新
2018-05-03 初版 <br />
1.完成netty基本功能，添加检测/屏蔽敏感词功能。<br />

## 使用和运行
分为四个项目；
- rpc-base：父项目
 - rpc-common：公共类
 - rpc-service-api：对外API
 - rpc-server：服务端
 - rpc-client：客户端
 
部署和运行都比较简单，不介绍。<br />

## 问题
- 暂时未发现问题。<br />

## rpc todo
- client的失败重连<br />
- service mapping 采用注解形式<br />
- 基于spring的远程代理参考[navi](https://github.com/neoremind/navi)<br />
- 服务注册\发现\路由<br />

## base todo
- 查询IP归属地<br />
- 查询手机号归属地<br />
