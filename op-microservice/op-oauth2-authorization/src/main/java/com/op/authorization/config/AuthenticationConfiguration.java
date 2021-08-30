package com.op.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

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
                .antMatchers("/auth/getToken", "/auth/refreshToken").permitAll()
                .anyRequest().authenticated().and()
                .csrf().ignoringRequestMatchers(request -> "/introspect".equals(request.getRequestURI())).disable()
                // 需要设置 formLogin 或 basic 认证，不然授权码模式和简化模式时会报 403
//                .formLogin();
                 // 不能添加以下设置，不然授权码模式和简化模式时会报 401
                // .and()
                .exceptionHandling()
                // 自定义身份认证入口点，默认会跳转到登录页面（授权码模式或简化模式不要设置这个）
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
