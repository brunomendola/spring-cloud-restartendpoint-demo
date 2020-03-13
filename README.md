# Spring Cloud Hoxton.SR3- RestartEndpoint cannot be autowired [![Build Status](https://travis-ci.org/brunomendola/spring-cloud-restartendpoint-demo.svg?branch=master)](https://travis-ci.org/brunomendola/spring-cloud-restartendpoint-demo)

Since Spring Cloud Hoxton.SR3, autowiring RestartEndpoint which was working with Hoxton.SR2, no longer works.

The endpoint is enabled with the following property:

`management.endpoint.restart.enabled: true`

The startup fails with the following cause:
`NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.cloud.context.restart.RestartEndpoint' available: expected at least 1 bean which qualifies as autowire candidate.`
