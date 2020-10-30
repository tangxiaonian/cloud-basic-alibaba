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