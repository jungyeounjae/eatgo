package kr.co.fastcampus.eatgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = EatgoCustomerApiApplication.class)
public class EatgoCustomerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatgoCustomerApiApplication.class, args);
	}

}
