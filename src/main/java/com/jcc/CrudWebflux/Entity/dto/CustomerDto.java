package com.jcc.CrudWebflux.Entity.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;
    private String code;
    private String firstName;
    private String lastName;
    private String phone;
    private String status;

    // Campos de auditoría
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
    private String deletedBy;
    private LocalDateTime deletedDate;
}
