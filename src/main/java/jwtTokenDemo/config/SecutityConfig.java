package jwtTokenDemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jwtTokenDemo.security.CustomUserDetails;

@Configuration
public class SecutityConfig {
	
	private UserDetailsService userDetailsService;
	
	private CustomUserDetails customUserDetails;

	public SecutityConfig(UserDetailsService userDetailsService, CustomUserDetails customUserDetails) {
		super();
		this.userDetailsService = userDetailsService;
		this.customUserDetails = customUserDetails;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception
	{
		return configuration.getAuthenticationManager();
	}
	
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception
	{
		http.formLogin(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize->authorize.requestMatchers("/login/**","/hello/**").permitAll()
						.anyRequest().authenticated()).build();
				
	}

}
