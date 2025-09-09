package com.jcc.CrudWebflux.Service;

import com.jcc.CrudWebflux.Entity.dto.CustomerDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDto> getAllCustomers();
    Mono<CustomerDto> createCustomer(CustomerDto customerDto);
} 