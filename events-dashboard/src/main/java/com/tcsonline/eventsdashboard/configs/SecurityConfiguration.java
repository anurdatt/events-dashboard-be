package com.tcsonline.eventsdashboard.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.tcsonline.eventsdashboard.interceptors.JwtInterceptor;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableWebMvc
//@EnableSwagger2
//@Import(SwaggerConfiguration.class)
public class SecurityConfiguration extends WebMvcConfigurerAdapter{

	@Value("${api.base-url}")
	private String apiBaseUrl;
	@Value("${api.base-path}")
	private String apiBasePath;
	
	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any())
////				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
////				.paths(PathSelectors.ant("/evm/api"))
//				.build();
//	}
	
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
//		registry.addRedirectViewController("/documentation/configuration/ui","/configuration/ui");
//		registry.addRedirectViewController("/documentation/configuration/security","/configuration/security");
//		registry.addRedirectViewController("/documentation/swagger-resources", "/swagger-resources");
//		registry.addRedirectViewController("/documentation", "/documentation/swagger-ui.html");
//	    registry.addRedirectViewController("/documentation/", "/documentation/swagger-ui.html");
//	}
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
////		registry.addResourceHandler("/documentation/**")
////		.addResourceLocations("classpath:/META-INF/resources/");
//		
//		registry.addResourceHandler("/webjars/**")
//		.addResourceLocations("classpath:/META-INF/resources/webjars/");
//		
//		registry.addResourceHandler("/swagger-resources/**")
//		.addResourceLocations("classpath:/META-INF/resources/");
//		
//		super.addResourceHandlers(registry);
//	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor);
		super.addInterceptors(registry);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**").allowedOrigins("http://localhost:4200");
		super.addCorsMappings(registry);
	}
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.addPathPrefix(apiBasePath, HandlerTypePredicate.forAnnotation(RestController.class));
		super.configurePathMatch(configurer);
	}
}
