
package com.jcc.CrudWebflux.Repository;

import java.math.BigDecimal;

import com.jcc.CrudWebflux.Entity.dto.CustomerDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {
    Flux<CustomerDto> findAll();
    Mono<CustomerDto> save(CustomerDto customerDto);
    Mono<CustomerDto> update(Long id, CustomerDto customerDto);
    Mono<Void> updateExchangeRate(Long id, BigDecimal exchangeRate);
    Mono<CustomerDto> findById(Long id);
}
