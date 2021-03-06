# cloud-basic-alibaba

spring-cloud-alibaba 基础设施测试

## sentinel 说明
版本：v1.8

java -jar sentinel-dashboard.jar
直接启动即可。

默认用户名,密码都是 sentinel。

常见的参数配置：
- -Dserver.port=8080 
- -Dcsp.sentinel.dashboard.server=localhost:8080 
- -Dproject.name=sentinel-dashboard
- -Dserver.servlet.session.timeout=7200

## nacos 说明
版本：v1.3

解压，进入bin目录打开cmd执行 **startup.cmd -m standalone**

默认用户名，密码都是nacos。

-m 指定启动模式。standalone 单节点，cluster 集群，默认集群启动方式。

启动项目nacos中需要增加的配置：

**1.service-order.yaml**

```yml
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
```

**2.service-user-flowRule.json**

``` json
[
    {
        "resource": "sentinelTest",  // 资源名称
        "limitApp": "default", // 针对流控来源,default 不区分来源
        "grade": 1,  // 阈值类型:1.QPS 0.线程数
        "count": 2,  // 阈值
        "strategy": 0, // 调用关系限流策略
        "controlBehavior": 0, // 流控效果
        "clusterMode": false
    }
]
```

**3.gateway-sentinel.json**

```json
[
    {
        "resource": "service_user",
        "resourceMode": 0,
        "grade": 1, // 限流指标维度 1 QPS,0 线程数。
        "count": 100, // 阈值
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
```

4.service-user-degradeRule.json

```json
[
    {
        "resource":"downGradeTest",
        "grade": 0, // 熔断策略，支持慢调用比例0/异常比例1/异常数策略2
        "count": 500, // 慢调用比例模式下为慢调用临界 RT（超出该值计为慢调用）；异常比例/异常数模式下为对应的阈值
        "timeWindow": 2, // 熔断时长，单位为 s
        "minRequestAmount":2, // 熔断触发的最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断
        "statIntervalMs":1000, // 统计时长（单位为 ms）
        "slowRatioThreshold": 0.3 // 慢调用比例阈值，仅慢调用比例模式有效
    }
]
```