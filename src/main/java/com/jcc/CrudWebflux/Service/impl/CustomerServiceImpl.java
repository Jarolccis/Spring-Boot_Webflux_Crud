package com.jcc.CrudWebflux.Service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.jcc.CrudWebflux.Entity.dto.CustomerDto;
import com.jcc.CrudWebflux.Repository.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.jcc.CrudWebflux.Service.CustomerService;
import com.jcc.CrudWebflux.Service.ExchangeRateService;
import com.jcc.CrudWebflux.Entity.dto.RateDto;
import java.math.BigDecimal;

@Service
public class CustomerServiceImpl  implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

    @Autowired
    private ExchangeRateService exchangeRateService;

	@Override
	public Flux<CustomerDto> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Mono<CustomerDto> createCustomer(CustomerDto customerDto) {
		return customerRepository.save(customerDto);
	}

	@Override
	public Mono<CustomerDto> updateCustomer(Long id, CustomerDto customerDto) {
		return customerRepository.update(id, customerDto);
	}

	@Override
	public Mono<CustomerDto> updateCustomerExchangeRate(Long id, RateDto rateDto) {
		return customerRepository.findById(id)
			.flatMap(customer ->
				exchangeRateService.getExchangeRates(rateDto)
					.flatMap(exchangeRateDto -> {
						Double rate = null;
						if (exchangeRateDto.getRates() != null && rateDto.getSymbols() != null) {
							rate = exchangeRateDto.getRates().getRateForCurrency(rateDto.getSymbols());
						}
						if (rate != null) {
							BigDecimal exchangeRate = BigDecimal.valueOf(rate);
							return customerRepository.updateExchangeRate(id, exchangeRate)
								.then(Mono.just(customer));
						} else {
							return Mono.error(new RuntimeException("No se encontró el tipo de cambio para la moneda: " + rateDto.getSymbols()));
						}
					})
			);
	}

	@Override
	public Mono<CustomerDto> getCustomerById(Long id) {
		return customerRepository.findById(id);
	}

}
