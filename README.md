# Spring Cloud Hoxton.SR3- RestartEndpoint cannot be autowired [![Build Status](https://travis-ci.org/brunomendola/spring-cloud-restartendpoint-demo.svg?branch=master)](https://travis-ci.org/brunomendola/spring-cloud-restartendpoint-demo)

Since Spring Cloud Hoxton.SR3, autowiring RestartEndpoint which was working with Hoxton.SR2, no longer works.

The endpoint is enabled with the following property:

`management.endpoint.restart.enabled: true`

The startup fails with the following cause:
`NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.cloud.context.restart.RestartEndpoint' available: expected at least 1 bean which qualifies as autowire candidate.`

## Solution

Looks like it was a mix of breaking changes in Spring Boot (2.2.x) and Actuator:

1. Each individual endpoint can be enabled or disabled. This controls whether or not the endpoint is created and its bean exists in the application context. To be remotely accessible an endpoint also has to be exposed via JMX or HTTP. (https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-endpoints)
1. By default, this feature _(JMX)_ is not enabled and can be turned on by setting the configuration property `spring.jmx.enabled` to `true`. (https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-jmx)
1. As the test context framework caches context, JMX is disabled by default to prevent identical components to register on the same domain. (https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications-jmx)

So.... the endpoints must also be exposed, via HTTP or JMX. Since I just want to use the RestartEndpoint programmatically, I'll go with JMX.

To make it work in production, the following properties must be set in application.yml:

```yaml
spring:
  jmx.enabled: true
management:
  endpoint.restart.enabled: true
```

All JMX endpoints are exposed by default, but if you customized the `management.endpoints.jmx.exposure.include` property with a list of endpoints (the default value is `*`), you will need to also add `restart`.

**This is not enough to make it also work in a Spring Boot test!**
To make it work in a Spring Boot test, you must annotate the test with `@SpringBootTest(properties = "spring.jmx.enabled=true")`, because the value in application.yml is always overridden by default with `false`.

What I really can't explain is why it works with Spring Boot 2.2.5.RELEASE and Spring Cloud Hoxton.SR2.

Or maybe it is an Actuator bug? After all, the documentation says "To be remotely accessible an endpoint also has to be exposed via JMX or HTTP", but I only want to access it programmatically!