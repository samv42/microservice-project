package codingnomads.spring.managementmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ManagementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementMicroserviceApplication.class, args);
	}

}
