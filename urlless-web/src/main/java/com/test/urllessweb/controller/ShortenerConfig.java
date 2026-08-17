package com.test.urllessweb.controller;

import com.test.gateway.UrlGateway;
import com.test.gateway.UrlGatewayFake;
import com.test.generator.IdGenerator;
import com.test.interactor.ShortenerInteractor;
import com.test.usecase.ShortenerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShortenerConfig {
    @Bean
    public ShortenerUseCase shortener(UrlGateway urlGateway, IdGenerator generator){
        return new ShortenerInteractor(urlGateway, generator);
    }
    @Bean
    public UrlGateway urlGatewayFake(){
        return new UrlGatewayFake();
    }
    @Bean
    public IdGenerator idGeneratorFake(){
        return null;
    }
}
