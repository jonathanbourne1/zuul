package com.restaurants.zuul.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    Environment environment;
            WebSecurity(Environment environment){
                this.environment= environment;
            }

            @Override
    protected void configure(HttpSecurity http) throws Exception{
    http.csrf().disable();
    http.headers().frameOptions().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    //for H2 console
                http.authorizeRequests().antMatchers(environment.getProperty("h2.console_allowed")).permitAll();
                //for register and login
    http.authorizeRequests().antMatchers(HttpMethod.POST,environment.getProperty("user.registration")).permitAll();
    http.authorizeRequests().antMatchers(HttpMethod.POST,environment.getProperty("user.endpoint_allowed")).permitAll()
    .anyRequest().authenticated()
    .and()
    .addFilter(new AuthorizationFilter(authenticationManager(),environment));


    }
}
