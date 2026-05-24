package com.ljw;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ljw")
public class LjwApplication {
    public static void main(String[] args) {
        SpringApplication.run(LjwApplication.class, args);
    }
}
