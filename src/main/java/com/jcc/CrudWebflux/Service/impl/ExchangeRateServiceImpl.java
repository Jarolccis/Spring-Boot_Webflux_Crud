package com.jcc.CrudWebflux.Service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jcc.CrudWebflux.Entity.dto.ExchangeRateDto;
import com.jcc.CrudWebflux.Entity.dto.RateDto;
import com.jcc.CrudWebflux.Service.ExchangeRateService;

import reactor.core.publisher.Mono;


@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final WebClient webClient;
    public ExchangeRateServiceImpl(@Qualifier("webClientFixer") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ExchangeRateDto> getExchangeRates(RateDto rateDto) {
    return webClient.get()
        .uri("?base=" + rateDto.getBase() + "&symbols=" + rateDto.getSymbols())
        .header("apikey", "OErqAKvh8bLyI0rEJtKp8EyFwNcAHgYk")
        .retrieve()
        .bodyToMono(ExchangeRateDto.class);
    }

}
