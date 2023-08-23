package br.com.postech.mixfast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MixFastApplication {

	public static void main(String[] args) {
		SpringApplication.run(MixFastApplication.class, args);
	}

}
