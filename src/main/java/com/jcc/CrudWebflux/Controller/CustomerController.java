package com.jcc.CrudWebflux.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.jcc.CrudWebflux.Entity.dto.CustomerDto;
import com.jcc.CrudWebflux.Entity.dto.ExchangeRateDto;
import com.jcc.CrudWebflux.Entity.dto.RateDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.jcc.CrudWebflux.Service.CustomerService;
import com.jcc.CrudWebflux.Service.ExchangeRateService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.jcc.CrudWebflux.Entity.dto.RateDto;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping
    public Flux<CustomerDto> getAllCustomers() {
        logger.info("GET /customers - Listando todos los clientes");
        return customerService.getAllCustomers();
    }


    @PostMapping
    public Mono<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        logger.info("POST /customers - Creando cliente: {}", customerDto);
        return customerService.createCustomer(customerDto);
    }

    @PostMapping("/exchange-rate")
    public Mono<ExchangeRateDto> getExchangeRates(@RequestBody RateDto rateDto) {
        logger.info("POST /customers/exchange-rate - Consultando tipo de cambio: base={}, symbols={}", rateDto.getBase(), rateDto.getSymbols());
        return exchangeRateService.getExchangeRates(rateDto);
    }

    @PutMapping("/{id}")
    public Mono<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        logger.info("PUT /customers/{} - Actualizando cliente: {}", id, customerDto);
        return customerService.updateCustomer(id, customerDto);
    }

    @PostMapping("/{id}/exchange-rate")
    public Mono<CustomerDto> updateCustomerExchangeRate(@PathVariable Long id, @RequestBody RateDto rateDto) {
        logger.info("POST /customers/{}/exchange-rate - Actualizando tipo de cambio: base={}, symbols={}", id, rateDto.getBase(), rateDto.getSymbols());
        return customerService.updateCustomerExchangeRate(id, rateDto);
    }

    @GetMapping("/{id}")
    public Mono<CustomerDto> getCustomerById(@PathVariable Long id) {
        logger.info("GET /customers/{} - Consultando cliente por ID", id);
        return customerService.getCustomerById(id);
    }
    

}
