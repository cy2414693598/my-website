package com.seeu.workstation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类 —— 整个后端的入口。
 *
 * @SpringBootApplication 是三个注解的合体：
 *   @Configuration      本类是配置类
 *   @ComponentScan      扫描本包及子包，把带 @Service/@RestController 的类注册进容器
 *   @EnableAutoConfiguration 按依赖自动装配（有 web 依赖就配 Tomcat + MVC）
 *
 * 所以"包结构 = 扫描范围"：所有代码必须放在 com.seeu.workstation 或其子包下。
 */
@SpringBootApplication
public class WorkstationApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkstationApplication.class, args);
    }
}
