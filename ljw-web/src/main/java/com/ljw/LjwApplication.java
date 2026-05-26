package com.ljw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类。
 *
 * <p>scanBasePackages 用来扫描 com.ljw 下的 Controller、Service、Component 等 Bean；
 * MapperScan 用来扫描 MyBatis-Plus 的 Mapper 接口。</p>
 */
@MapperScan("com.ljw.service.mapper")
@SpringBootApplication(scanBasePackages = "com.ljw")
public class LjwApplication {

    public static void main(String[] args) {
        SpringApplication.run(LjwApplication.class, args);
    }
}
