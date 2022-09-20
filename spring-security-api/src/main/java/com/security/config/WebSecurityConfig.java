package com.security.config;

import com.security.filter.CustomAuthenticationFilter;
import com.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private UserDetailsService jwtUserDetailsService;

    Environment env;

    private final String[] PUBLIC_URL = {};
    private final String[] IGNORE_URL = {};

    @Autowired
    public WebSecurityConfig(UserDetailsService jwtUserDetailsService, Environment env) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.env = env;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(this.PUBLIC_URL).permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.apply(new CustomerFilter());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(this.IGNORE_URL);
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new UrlBasedCorsConfigurationSource();
    }

    public class CustomerFilter extends AbstractHttpConfigurer<CustomerFilter, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilter(new CustomAuthenticationFilter(
                    authenticationManager,
                    env.getProperty("jwt.secret"),
                    env.getProperty("prefix.value"),
                    env.getProperty("jwt.token.validity")));
            http.addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Bean
    public CustomAuthorizationFilter authorizationFilter() {
        return new CustomAuthorizationFilter(
                env.getProperty("jwt.secret"),
                env.getProperty("prefix.value"),
                env.getProperty("jwt.token.validity"),
                PUBLIC_URL);
    }
}
