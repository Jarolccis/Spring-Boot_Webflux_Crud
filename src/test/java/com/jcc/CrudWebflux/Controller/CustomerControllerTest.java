package com.jcc.CrudWebflux.Controller;

import com.jcc.CrudWebflux.Entity.dto.CustomerDto;
import com.jcc.CrudWebflux.Entity.dto.ExchangeRateDto;
import com.jcc.CrudWebflux.Entity.dto.RateDto;
import com.jcc.CrudWebflux.Service.CustomerService;
import com.jcc.CrudWebflux.Service.ExchangeRateService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;


    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void getAllCustomers_returnsCustomerList() {
        CustomerDto customer1 = new CustomerDto();
        customer1.setId(1L);
        customer1.setFirstName("Juan");
        CustomerDto customer2 = new CustomerDto();
        customer2.setId(2L);
        customer2.setFirstName("Ana");

        Flux<CustomerDto> customerFlux = Flux.fromIterable(Arrays.asList(customer1, customer2));
        Mockito.when(customerService.getAllCustomers()).thenReturn(customerFlux);

        Flux<CustomerDto> result = customerController.getAllCustomers();
        assertThat(result.collectList().block()).containsExactly(customer1, customer2);
    }

        @Test
        void createCustomer_returnsCreatedCustomer() {
            CustomerDto customer = new CustomerDto();
            customer.setId(1L);
            Mockito.when(customerService.createCustomer(customer)).thenReturn(Mono.just(customer));
            Mono<CustomerDto> result = customerController.createCustomer(customer);
            assertThat(result.block()).isEqualTo(customer);
        }

        @Test
        void updateCustomer_returnsUpdatedCustomer() {
            CustomerDto customer = new CustomerDto();
            customer.setId(1L);
            Mockito.when(customerService.updateCustomer(1L, customer)).thenReturn(Mono.just(customer));
            Mono<CustomerDto> result = customerController.updateCustomer(1L, customer);
            assertThat(result.block()).isEqualTo(customer);
        }

        @Test
        void updateCustomerExchangeRate_returnsCustomerWithRate() {
            CustomerDto customer = new CustomerDto();
            customer.setId(1L);
            RateDto rateDto = new RateDto("USD", "PEN");
            Mockito.when(customerService.updateCustomerExchangeRate(1L, rateDto)).thenReturn(Mono.just(customer));
            Mono<CustomerDto> result = customerController.updateCustomerExchangeRate(1L, rateDto);
            assertThat(result.block()).isEqualTo(customer);
        }

        @Test
        void getCustomerById_returnsCustomer() {
            CustomerDto customer = new CustomerDto();
            customer.setId(1L);
            Mockito.when(customerService.getCustomerById(1L)).thenReturn(Mono.just(customer));
            Mono<CustomerDto> result = customerController.getCustomerById(1L);
            assertThat(result.block()).isEqualTo(customer);
        }

        @Test
        void getExchangeRates_returnsExchangeRateDto() {
            RateDto rateDto = new RateDto("USD", "PEN");
            ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
            Mockito.when(exchangeRateService.getExchangeRates(rateDto)).thenReturn(Mono.just(exchangeRateDto));
            Mono<ExchangeRateDto> result = customerController.getExchangeRates(rateDto);
            assertThat(result.block()).isEqualTo(exchangeRateDto);
        }

        @Test
        void getAllCustomers_whenServiceThrowsError_shouldPropagateError() {
            RuntimeException serviceException = new RuntimeException("Error en el servicio");
            Mockito.when(customerService.getAllCustomers()).thenReturn(Flux.error(serviceException));

            Flux<CustomerDto> result = customerController.getAllCustomers();
            org.assertj.core.api.Assertions.assertThatThrownBy(() -> result.collectList().block())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error en el servicio");
        }

        @Test
        void getCustomerById_whenNotFound_shouldReturnEmptyMono() {
            Mockito.when(customerService.getCustomerById(99L)).thenReturn(reactor.core.publisher.Mono.empty());
            Mono<CustomerDto> result = customerController.getCustomerById(99L);
            assertThat(result.block()).isNull();
        }
}
