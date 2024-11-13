package com.welfeki.demo.security;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/api/all/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/cours/**", "/api/matieres/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers(HttpMethod.POST, "/api/cours/**", "/api/matieres/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/cours/**", "/api/matieres/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/cours/**", "/api/matieres/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/image/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.OPTIONS, "/api/image/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/image/**").hasAnyAuthority("ADMIN", "USER")
						.anyRequest().authenticated())
				.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "multipart/form-data"));
		configuration.setExposedHeaders(Arrays.asList("Authorization"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}