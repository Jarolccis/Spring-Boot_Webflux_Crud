package com.jcc.CrudWebflux.Service.impl;

import com.jcc.CrudWebflux.Entity.dto.CustomerDto;
import com.jcc.CrudWebflux.Repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.publisher.Flux;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void getAllCustomers_returnsCustomerList() {
        CustomerDto customer1 = new CustomerDto();
        customer1.setId(1L);
        customer1.setFirstName("Juan");
        CustomerDto customer2 = new CustomerDto();
        customer2.setId(2L);
        customer2.setFirstName("Ana");

        Flux<CustomerDto> customerFlux = Flux.fromIterable(Arrays.asList(customer1, customer2));
        Mockito.when(customerRepository.findAll()).thenReturn(customerFlux);

        Flux<CustomerDto> result = customerService.getAllCustomers();
        assertThat(result.collectList().block()).containsExactly(customer1, customer2);
    }

        @Test
        void createCustomer_returnsCreatedCustomer() {
            CustomerDto customer = new CustomerDto();
            customer.setId(1L);
            Mockito.when(customerRepository.save(customer)).thenReturn(reactor.core.publisher.Mono.just(customer));
            reactor.core.publisher.Mono<CustomerDto> result = customerService.createCustomer(customer);
            assertThat(result.block()).isEqualTo(customer);
        }

        @Test
        void updateCustomer_returnsUpdatedCustomer() {
            CustomerDto customer = new CustomerDto();
            customer.setId(1L);
            Mockito.when(customerRepository.update(1L, customer)).thenReturn(reactor.core.publisher.Mono.just(customer));
            reactor.core.publisher.Mono<CustomerDto> result = customerService.updateCustomer(1L, customer);
            assertThat(result.block()).isEqualTo(customer);
        }

        @Test
        void getCustomerById_returnsCustomer() {
            CustomerDto customer = new CustomerDto();
            customer.setId(1L);
            Mockito.when(customerRepository.findById(1L)).thenReturn(reactor.core.publisher.Mono.just(customer));
            reactor.core.publisher.Mono<CustomerDto> result = customerService.getCustomerById(1L);
            assertThat(result.block()).isEqualTo(customer);
        }
}
