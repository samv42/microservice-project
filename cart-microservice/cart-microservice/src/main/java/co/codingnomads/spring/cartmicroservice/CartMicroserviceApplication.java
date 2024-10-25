package co.codingnomads.spring.cartmicroservice;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CartMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartMicroserviceApplication.class, args);
	}

	@Autowired
	EurekaClient discoveryClient;

	public String userServiceUrl() {
		InstanceInfo instance = discoveryClient.getNextServerFromEureka("USER-MICROSERVICE", false);
		return instance.getHomePageUrl();
	}
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

}
