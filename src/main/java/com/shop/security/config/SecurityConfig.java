package com.shop.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.common.config.properties.CorsProperties;
import com.shop.security.authhandlers.MyAuthenticationFailureHandler;
import com.shop.security.authhandlers.RestAuthenticationEntryPoint;
import com.shop.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.shop.security.filter.UserRegisterAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final ObjectMapper objectMapper;
	private final CorsProperties corsProperties;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()
			.csrf(csrf -> csrf.ignoringAntMatchers("/join", "/h2-console/**", "/login")
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.headers().frameOptions().disable()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(restAuthenticationEntryPoint())
			.and()
			.authorizeRequests()
			.antMatchers("/join", "/h2-console/**", "/swagger-ui/**", "/swagger-ui**",
				"/v3/api-docs/**", "/v3/api-docs**", "/api-docs","/login").permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginProcessingUrl("/login")
			.and()
			.logout()
			.and()
			.oauth2Login()
			.failureHandler(myAuthenticationFailureHandler())
			.successHandler(authenticationSuccessHandler())
			.and()
			.addFilterAt(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(userRegisterAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Bean
	public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
		CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter(objectMapper);
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		return filter;
	}

	@Bean
	public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public MyAuthenticationFailureHandler myAuthenticationFailureHandler() {
		return new MyAuthenticationFailureHandler();
	}

	@Bean
	public UserRegisterAuthenticationFilter userRegisterAuthenticationFilter() {
		return new UserRegisterAuthenticationFilter();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return (request, response, authentication) -> response.setStatus(HttpStatus.NO_CONTENT.value());
	}


	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfig = new CorsConfiguration();

		corsConfig.setAllowCredentials(true);
		corsConfig.addAllowedOrigin("http://localhost:3000"); // 클라이언트 도메인
		corsConfig.addAllowedHeader("*");
		corsConfig.addAllowedMethod("*"); // 모든 HTTP 메서드 허용

		source.registerCorsConfiguration("/**", corsConfig);
		return new CorsFilter(source);
	}
}
