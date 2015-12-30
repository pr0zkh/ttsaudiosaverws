package org.ttsaudiosaver.ws.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "org.ttsaudiosaver.ws")
public class WebConfig extends WebMvcConfigurerAdapter {
	
	private static final Logger logger = Logger.getLogger(WebConfig.class);
	
//	private static final String AUDIO_RESOURCE_LOCATION = "/output/";
//	private static final String COMPILED_AUDIO_RESOURCE_LOCATION = "/output/compiled/";
//	private static final String AUDIO_RESOURCE_URI_PATTERN = "/output/**";
//	private static final String COMPILED_AUDIO_RESOURCE_URI_PATTERN = "/output/compiled/**";
//
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler(AUDIO_RESOURCE_URI_PATTERN).addResourceLocations(AUDIO_RESOURCE_LOCATION);
//		registry.addResourceHandler(COMPILED_AUDIO_RESOURCE_URI_PATTERN).addResourceLocations(COMPILED_AUDIO_RESOURCE_LOCATION);
//	}
}