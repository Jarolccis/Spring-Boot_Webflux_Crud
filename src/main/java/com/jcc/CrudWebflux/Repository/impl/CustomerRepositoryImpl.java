package com.jcc.CrudWebflux.Repository.impl;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;

import com.jcc.CrudWebflux.Entity.dto.CustomerDto;
import com.jcc.CrudWebflux.Repository.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private DatabaseClient databaseClient;


    @Override
    public Flux<CustomerDto> findAll() {
        String sql = "SELECT Id, Code, FirstName, LastName, Phone, Status, CreatedBy, CreatedDate, UpdatedBy, UpdatedDate, DeletedBy, DeletedDate FROM Customers";
        return databaseClient.sql(sql)
                .map((row, metadata) -> {
                    CustomerDto dto = new CustomerDto();
                    dto.setId(row.get("Id", Long.class));
                    dto.setCode(row.get("Code", String.class));
                    dto.setFirstName(row.get("FirstName", String.class));
                    dto.setLastName(row.get("LastName", String.class));
                    dto.setPhone(row.get("Phone", String.class));
                    dto.setStatus(row.get("Status", String.class));
                    dto.setCreatedBy(row.get("CreatedBy", String.class));
                    dto.setCreatedDate(row.get("CreatedDate", java.time.LocalDateTime.class));
                    dto.setUpdatedBy(row.get("UpdatedBy", String.class));
                    dto.setUpdatedDate(row.get("UpdatedDate", java.time.LocalDateTime.class));
                    dto.setDeletedBy(row.get("DeletedBy", String.class));
                    dto.setDeletedDate(row.get("DeletedDate", java.time.LocalDateTime.class));
                    return dto;
                })
                .all();
    }

    @Override
    public Mono<CustomerDto> save(CustomerDto customerDto) {
        String sql = "INSERT INTO Customers (Code, FirstName, LastName, Phone, Status, CreatedBy) " +
                "VALUES (:Code, :FirstName, :LastName, :Phone, :Status, :CreatedBy)";
        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(sql)
                .bind("Code", customerDto.getCode())
                .bind("FirstName", customerDto.getFirstName())
                .bind("LastName", customerDto.getLastName())
                .bind("Phone", customerDto.getPhone())
                .bind("Status", customerDto.getStatus())
                .bind("CreatedBy", customerDto.getCreatedBy());
        return spec
                .filter((statement, executeFunction) -> statement.returnGeneratedValues("Id").execute())
                .fetch()
                .one()
                .map(row -> {
                    BigDecimal idValue =  (BigDecimal) row.get("Id");
                    customerDto.setId(idValue.longValue());
                    return customerDto;
                });
    }

}
