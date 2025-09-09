package com.jcc.CrudWebflux.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.jcc.CrudWebflux.Entity.dto.CustomerDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.jcc.CrudWebflux.Service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Flux<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    @PostMapping
    public Mono<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }

}
