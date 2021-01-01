package com.soict.reviewshopfood;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.soict.reviewshopfood.properties.FileStorageProperties;

@SpringBootApplication(scanBasePackages = { "com.soict.reviewshopfood" })
@EntityScan(basePackages = { "com.soict.reviewshopfood.entity" })
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class ReviewShopFoodApplication{
	
	// extends WebMvcConfigurationSupport
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		//register recource handler for images
//		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/")
//				.setCacheControl(CacheControl.maxAge(2,TimeUnit.HOURS).cachePublic());
//	}
	public static void main(String[] args) {
		SpringApplication.run(ReviewShopFoodApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}
}
