package com.ljw;
import com.ljw.common.util.PasswordUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ljw.service.mapper")
@SpringBootApplication(scanBasePackages = "com.ljw")
public class LjwApplication {
    public static void main(String[] args) {


        SpringApplication.run(LjwApplication.class, args);
    }
}
