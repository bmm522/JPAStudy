package com.personal.community.config;

import com.personal.community.filter.MemberCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public FilterRegistrationBean memberCheckFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new MemberCheckFilter());
        return registrationBean;
    }
}
