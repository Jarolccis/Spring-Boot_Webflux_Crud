
package com.jcc.CrudWebflux.Service;

import com.jcc.CrudWebflux.Entity.dto.RateDto;

import com.jcc.CrudWebflux.Entity.dto.CustomerDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDto> getAllCustomers();
    Mono<CustomerDto> createCustomer(CustomerDto customerDto);
    Mono<CustomerDto> updateCustomer(Long id, CustomerDto customerDto);
    Mono<CustomerDto> updateCustomerExchangeRate(Long id, RateDto rateDto);
    Mono<CustomerDto> getCustomerById(Long id);
} 