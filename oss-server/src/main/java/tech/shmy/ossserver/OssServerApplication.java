package tech.shmy.ossserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OssServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OssServerApplication.class, args);
	}

}
