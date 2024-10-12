package com.sky.homework.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private static final String API_DOCS_PATH = "/api-docs/**";
	private static final String ACTUATOR_PATH = "/actuator/**";

}
