package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = "com.cornelius")
@EnableDiscoveryClient//nacos注册
@EnableSwagger2
@EnableFeignClients//服务调用
public class EduApplication {
    public static void main(String[] args) {

        SpringApplication.run(EduApplication.class,args);
    }
}
