package kr.co.fastcampus.eatgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = EatgoAdminApplication.class)
public class EatgoAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatgoAdminApplication.class, args);
	}

}
