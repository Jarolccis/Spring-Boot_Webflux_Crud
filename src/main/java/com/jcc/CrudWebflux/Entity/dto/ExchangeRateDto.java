package com.jcc.CrudWebflux.Entity.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {
    private boolean success;
    private long timestamp;
    private String base;
    private String date;
    private Rates rates;

    public static class Rates {
        private Map<String, Double> currencyRates = new HashMap<>();

        public Rates() {}

        // Este método captura cualquier propiedad dinámica
        @JsonAnySetter
        public void setCurrencyRate(String currency, Double rate) {
            currencyRates.put(currency, rate);
        }

        public Map<String, Double> getCurrencyRates() {
            return currencyRates;
        }

        public void setCurrencyRates(Map<String, Double> currencyRates) {
            this.currencyRates = currencyRates;
        }

        // Método útil para obtener el rate de una moneda específica
        public Double getRateForCurrency(String currency) {
            return currencyRates.get(currency.toUpperCase());
        }
    }
}