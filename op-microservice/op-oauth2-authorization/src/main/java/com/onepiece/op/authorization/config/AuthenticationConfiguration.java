package com.onepiece.op.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

/**
 * 用于配置此授权服务器识别的最终用户
 *
 * @author cdrcool
 */
@EnableWebSecurity
public class AuthenticationConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/.well-known/jwks.json").permitAll()
                .antMatchers("/auth/token").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().and()
                .csrf().ignoringRequestMatchers(request -> "/introspect".equals(request.getRequestURI())).disable();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new JdbcUserDetailsManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}