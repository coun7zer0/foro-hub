package com.alura.forohub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

  // @Bean
  // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  //   http
  //     .cors(AbstractHttpConfigurer::disable) // Deshabilitar CORS explícitamente
  //     .sessionManagement(session -> session
  //       .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
  //     .authorizeHttpRequests(authorize -> authorize
  //       .requestMatchers(HttpMethod.POST, "/login").permitAll()
  //       .requestMatchers(HttpMethod.GET, "/topicos").permitAll()
  //       .requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/topicos/[0-9]+")).permitAll()
  //       .anyRequest().authenticated());
  //   return http.build();
  // }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf().disable().sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Le indicamos a Spring el tipo de sesion
      .and().authorizeRequests()
      .requestMatchers(HttpMethod.POST, "/login").permitAll()
      .requestMatchers(HttpMethod.GET ,"/topicos").permitAll()
      .requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/topicos/[0-9]+")).permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}