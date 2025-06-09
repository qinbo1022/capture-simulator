package com.tsing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author bo.qin
 */
@SpringBootApplication
@MapperScan("com.tsing.mapper")
@EnableFeignClients(basePackages = "com.tsing.feign")
public class CaptureSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptureSimulatorApplication.class, args);
	}

}
