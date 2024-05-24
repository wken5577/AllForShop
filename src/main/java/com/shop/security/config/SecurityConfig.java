package com.shop.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.shop.security.authhandlers.MyAuthenticationFailureHandler;
import com.shop.security.authhandlers.RestAuthenticationEntryPoint;
import com.shop.security.filter.UserRegisterAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;
	private final UserRegisterAuthenticationFilter userRegisterAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			.and()
			.csrf(csrf -> csrf.ignoringAntMatchers("/join", "/h2-console/**")
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.headers().frameOptions().disable()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(restAuthenticationEntryPoint)
			.and()
			.authorizeRequests()
			.antMatchers("/join", "/h2-console/**", "/swagger-ui/**", "/swagger-ui**",
				"/v3/api-docs/**", "/v3/api-docs**", "/api-docs").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginProcessingUrl("/login")
			.loginPage("/loginForm")
			.defaultSuccessUrl("/")
			.and()
			.logout()
			.logoutSuccessUrl("/")
			.and()
			.oauth2Login()
			.defaultSuccessUrl("/")
			.failureHandler(myAuthenticationFailureHandler)
			.and()
			.addFilterBefore(userRegisterAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
