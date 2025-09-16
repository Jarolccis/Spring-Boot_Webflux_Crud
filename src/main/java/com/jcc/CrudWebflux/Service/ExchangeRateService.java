package com.jcc.CrudWebflux.Service;
import com.jcc.CrudWebflux.Entity.dto.ExchangeRateDto;
import com.jcc.CrudWebflux.Entity.dto.RateDto;

import reactor.core.publisher.Mono;

public interface ExchangeRateService {
    Mono<ExchangeRateDto> getExchangeRates(RateDto rateDto);
}