package com.jcc.CrudWebflux.Repository.impl;

import com.jcc.CrudWebflux.Entity.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.DatabaseClient.GenericExecuteSpec;
import org.springframework.r2dbc.core.FetchSpec;

import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryImplTest {

    @Mock
    private DatabaseClient databaseClient;

    @InjectMocks
    private CustomerRepositoryImpl customerRepository;

    @Test
    void findAll_returnsCustomerList() {
           // Mock chain: databaseClient.sql -> GenericExecuteSpec -> map -> FetchSpec -> all
        GenericExecuteSpec genericExecuteSpec = Mockito.mock(GenericExecuteSpec.class);
        FetchSpec<CustomerDto> fetchSpec = Mockito.mock(FetchSpec.class);

        CustomerDto customer1 = new CustomerDto();
        customer1.setId(1L);
        customer1.setFirstName("Juan");
        CustomerDto customer2 = new CustomerDto();
        customer2.setId(2L);
        customer2.setFirstName("Ana");

        Mockito.when(databaseClient.sql(Mockito.anyString())).thenReturn(genericExecuteSpec);
        Mockito.when(genericExecuteSpec.map(Mockito.any(java.util.function.BiFunction.class))).thenReturn(fetchSpec);
        Mockito.when(fetchSpec.all()).thenReturn(Flux.just(customer1, customer2));

        Flux<CustomerDto> result = customerRepository.findAll();
        assertThat(result.collectList().block()).containsExactly(customer1, customer2);
    }

        @Test
        void update_returnsNonNullMono() {
            CustomerDto customer = new CustomerDto();
            customer.setId(1L);
            GenericExecuteSpec genericExecuteSpec = Mockito.mock(GenericExecuteSpec.class);
            Mockito.when(databaseClient.sql(Mockito.anyString())).thenReturn(genericExecuteSpec);
            Mockito.when(genericExecuteSpec.bind(Mockito.anyString(), Mockito.any())).thenReturn(genericExecuteSpec);
            Mockito.when(genericExecuteSpec.then()).thenReturn(reactor.core.publisher.Mono.empty());
            reactor.core.publisher.Mono<CustomerDto> result = customerRepository.update(1L, customer);
            assertThat(result).isNotNull();
        }

        @Test
        void updateExchangeRate_returnsNonNullMono() {
            java.math.BigDecimal rate = java.math.BigDecimal.valueOf(3.5);
            GenericExecuteSpec genericExecuteSpec = Mockito.mock(GenericExecuteSpec.class);
            Mockito.when(databaseClient.sql(Mockito.anyString())).thenReturn(genericExecuteSpec);
            Mockito.when(genericExecuteSpec.bind(Mockito.anyString(), Mockito.any())).thenReturn(genericExecuteSpec);
            Mockito.when(genericExecuteSpec.then()).thenReturn(reactor.core.publisher.Mono.empty());
            reactor.core.publisher.Mono<Void> result = customerRepository.updateExchangeRate(1L, rate);
            assertThat(result).isNotNull();
        }

        @Test
        void findById_returnsNonNullMono() {
            GenericExecuteSpec genericExecuteSpec = Mockito.mock(GenericExecuteSpec.class);
            FetchSpec<CustomerDto> fetchSpec = Mockito.mock(FetchSpec.class);
            Mockito.when(databaseClient.sql(Mockito.anyString())).thenReturn(genericExecuteSpec);
            Mockito.when(genericExecuteSpec.bind(Mockito.anyString(), Mockito.any())).thenReturn(genericExecuteSpec);
            Mockito.when(genericExecuteSpec.map(Mockito.any(java.util.function.BiFunction.class))).thenReturn(fetchSpec);
            CustomerDto customer = new CustomerDto();
            customer.setId(1L);
            Mockito.when(fetchSpec.one()).thenReturn(reactor.core.publisher.Mono.just(customer));
            reactor.core.publisher.Mono<CustomerDto> result = customerRepository.findById(1L);
            assertThat(result.block()).isEqualTo(customer);
        }

}
