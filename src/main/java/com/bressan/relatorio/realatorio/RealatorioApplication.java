package com.bressan.relatorio.realatorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RealatorioApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealatorioApplication.class, args);
	}

}
