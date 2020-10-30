# cloud-basic-alibaba

spring-cloud-alibaba 基础设施测试

## sentinel 说明
java -jar sentinel-dashboard.jar
直接启动即可。用户名,密码都是 sentinel。

常见的参数配置：
- -Dserver.port=8080 
- -Dcsp.sentinel.dashboard.server=localhost:8080 
- -Dproject.name=sentinel-dashboard
- -Dserver.servlet.session.timeout=7200

## nacos 说明
解压，进入bin目录打开cmd执行 startup.cmd -m standalone

启动项目nacos需要增加的配置：

``` json
service-order.yaml

server:
  port: 8082

spring:
  application:
    name: service-order
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848  #指定注册中心的位置
    sentinel:
      eager: true #是否提前触发 Sentinel 初始化
      transport:
        port: 8719  # 传输地址(默认8719)
        dashboard: localhost:8080   # dashboard 地址
      enabled: true #开启 sentinel
management:
  endpoints:
    web:
      exposure:
        include: "*"

service-user-sentinel.json
[
    {
        "resource": "sentinelTest",  // 资源名称
        "limitApp": "default",
        "grade": 1,  // 阈值类型:1.QPS 0.线程数
        "count": 2,  // 阈值
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
    }
]

gateway-sentinel.json
[
    {
        "resource": "service_user",
        "resourceMode": 0,
        "grade": 1, // 限流指标维度 1 QPS,0 线程数。
        "count": 2, // 阈值
        "intervalSec": 1, // 单位时间 秒
        "burst": 0 // 突发时额外允许的请求数
    },
     {
        "resource": "service_order",
        "resourceMode": 0,
        "grade": 1, // 限流指标维度 1 QPS,0 线程数。
        "count": 10, // 阈值
        "intervalSec": 1, // 单位时间 秒
        "burst": 1 // 突发时额外允许的请求数
    }
]
```