package org.moneyMinder.configurations;

import org.moneyMinder.interceptors.BasicAuthenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    /**
     * Agrega el interceptor de autenticación básica al registro de interceptores.
     *
     * @param registry El registro de interceptores al que se agrega el interceptor de autenticación básica.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BasicAuthenInterceptor());
    }
}