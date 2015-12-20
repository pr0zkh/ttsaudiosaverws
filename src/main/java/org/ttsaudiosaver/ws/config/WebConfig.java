package org.ttsaudiosaver.ws.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "org.ttsaudiosaver.ws")
public class WebConfig extends WebMvcConfigurerAdapter {
	
	private static final Logger logger = Logger.getLogger(WebConfig.class);
	
	private static final String AUDIO_RESOURCE_LOCATION = "/output/";
	private static final String AUDIO_RESOURCE_URI_PATTERN = "/output/**";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(AUDIO_RESOURCE_URI_PATTERN).addResourceLocations(AUDIO_RESOURCE_LOCATION);
	}
}