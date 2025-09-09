package com.jcc.CrudWebflux.Repository;

import com.jcc.CrudWebflux.Entity.dto.CustomerDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {
    Flux<CustomerDto> findAll();
    Mono<CustomerDto> save(CustomerDto customerDto);
}
