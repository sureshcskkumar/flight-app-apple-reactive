package com.flightapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.flightapp.service.CustomUserDetailsService;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@SecurityScheme(name = "appSecurity", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/api/v1.0/login", "/api/v1.0/register").permitAll()
		.antMatchers("/swagger-ui.html").permitAll()
		.antMatchers("/swagger-ui/**").permitAll()
		.antMatchers("/v3/api-docs/**").permitAll()
		.antMatchers("/api/v1.0/airline/register").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).
		and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
		and().addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/swagger-ui/**")
			.antMatchers("/swagger-ui.html")
			.antMatchers("/swagger-ui/**")
			.antMatchers("/v3/api-docs/**");
	}

}
