package com.oldeighthome.heavennote.config.web;

//import com.oldeighthome.heavennote.common.handler.JwtHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(1800)
                .allowedOrigins("*");
    }
    /*
    @Bean
    public JwtHandler getJwtHandler(){
        return new JwtHandler();
    }
    */

/*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getJwtHandler())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/v1/wx/session","/api/v1/wx/test");
    }
    */

}
