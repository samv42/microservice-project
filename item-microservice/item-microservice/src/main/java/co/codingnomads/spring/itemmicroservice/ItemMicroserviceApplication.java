package co.codingnomads.spring.itemmicroservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class ItemMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemMicroserviceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

	@Bean
	public CommandLineRunner loadInitialData(ItemService itemService) {

		return (args) -> {
			if (itemService.getAllItems().isEmpty()) {
				Item item1 = Item.builder().
						name("plate")
						.description("porceline")
						.build();
				Item item2 = Item.builder().
						name("fork")
						.description("silver")
						.build();
				Item item3 = Item.builder().
						name("sporks")
						.description("20 pack")
						.build();
				itemService.insertNewItem(item1);
				itemService.insertNewItem(item2);
				itemService.insertNewItem(item3);
			}
		};
	}
}
