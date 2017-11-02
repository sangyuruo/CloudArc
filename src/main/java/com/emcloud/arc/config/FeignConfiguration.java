package com.emcloud.arc.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.emcloud.arc")
public class FeignConfiguration {

}
