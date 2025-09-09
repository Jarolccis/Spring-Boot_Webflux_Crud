package com.jcc.CrudWebflux.Service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.jcc.CrudWebflux.Entity.dto.CustomerDto;
import com.jcc.CrudWebflux.Repository.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.jcc.CrudWebflux.Service.CustomerService;

@Service
public class CustomerServiceImpl  implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Flux<CustomerDto> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Mono<CustomerDto> createCustomer(CustomerDto customerDto) {
		return customerRepository.save(customerDto);
	}


}
