package com.jcc.CrudWebflux.Entity.dto;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class RateDto {
    private String base;
    private String symbols;

}
