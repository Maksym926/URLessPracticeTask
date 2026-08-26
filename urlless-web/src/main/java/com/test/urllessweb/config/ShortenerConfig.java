package com.test.urllessweb.config;

import com.test.gateway.CollectionGateway;
import com.test.gateway.CollectionGatewayFake;
import com.test.gateway.UrlGateway;
import com.test.gateway.UrlGatewayFake;
import com.test.generator.IdGenerator;
import com.test.generator.SHA1Generator;
import com.test.generator.StringIdGenerator;
import com.test.interactor.CollectionInteractor;
import com.test.interactor.ShortenerInteractor;
import com.test.usecase.CollectionUseCase;
import com.test.usecase.ShortenerUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShortenerConfig {


    //Url

    @Bean
    public ShortenerUseCase shortener(UrlGateway urlGateway, @Qualifier("urlIdGenerator")IdGenerator generator){
        return new ShortenerInteractor(urlGateway, generator);
    }

    @Bean
    public UrlGateway urlGatewayFake(){
        return new UrlGatewayFake();
    }
    @Bean
    public IdGenerator urlIdGenerator(){
        return  new SHA1Generator();
    }

    //Collection

    @Bean
    public CollectionUseCase collectionInteractor(CollectionGateway collectionGateway, ShortenerUseCase shortenerInteractor, @Qualifier("collectionIdGenerator") IdGenerator generatorForCollection){
        return new CollectionInteractor(collectionGateway, shortenerInteractor, generatorForCollection);
    }

    @Bean
    public CollectionGateway collectionGatewayFake(){
        return new CollectionGatewayFake();
    }

    @Bean
    public IdGenerator collectionIdGenerator(){
        return  new StringIdGenerator();
    }





}
