package com.group.exam.config;

import com.group.exam.filter.*;
import com.group.exam.service.impl.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserService userService;
    @Resource
    private UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    @Resource
    private AuthenticationProcessingFilter authenticationProcessingFilter;
    @Resource
    private CusAccessDeniedHandler cusAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http相关的配置，包括登入登出、异常处理、会话管理等

        http.authorizeRequests()
                .antMatchers("/login","/home","/user/login").permitAll()
                .antMatchers("/teacher/**").hasRole("TEACHER")
                .antMatchers("/student/**").hasRole("STUDENT")
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and().exceptionHandling().accessDeniedHandler(cusAccessDeniedHandler)
                .and().formLogin().loginProcessingUrl("/user/login")
                .and().rememberMe();
        http.sessionManagement()
                .invalidSessionUrl("/login")
                .maximumSessions(1).maxSessionsPreventsLogin(true);
        http.cors().and().csrf().disable();
        http.addFilterAt(authenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET,
                "/favicon.ico",
                "/*.html",
                "/**/*.css",
                "/**/*.js");
    }

}