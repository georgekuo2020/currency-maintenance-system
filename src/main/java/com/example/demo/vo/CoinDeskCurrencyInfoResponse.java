package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskCurrencyInfoResponse {

    /**
     *
     * {
     *   "time": {
     *     "updated": "Sep 2, 2024 07:07:20 UTC",
     *     "updatedISO": "2024-09-02T07:07:20+00:00",
     *     "updateduk": "Sep 2, 2024 at 08:07 BST"
     *   },
     *   "disclaimer": "just for test",
     *   "chartName": "Bitcoin",
     *   "bpi": {
     *     "USD": {
     *       "code": "USD",
     *       "symbol": "&#36;",
     *       "rate": "57,756.298",
     *       "description": "United States Dollar",
     *       "rate_float": 57756.2984
     *     },
     *     "GBP": {
     *       "code": "GBP",
     *       "symbol": "&pound;",
     *       "rate": "43,984.02",
     *       "description": "British Pound Sterling",
     *       "rate_float": 43984.0203
     *     },
     *     "EUR": {
     *       "code": "EUR",
     *       "symbol": "&euro;",
     *       "rate": "52,243.287",
     *       "description": "Euro",
     *       "rate_float": 52243.2865
     *     }
     *   }
     * }
     *
     */

    private Time time;

    private String disclaimer;

    private String chartName;

    private Bpi bpi;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Time {
        private String updated;
        private String updatedISO;
        private String updateduk;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bpi {
        @JsonProperty("USD")
        private CurrencyInfo usd;
        @JsonProperty("GBP")
        private CurrencyInfo gbp;
        @JsonProperty("EUR")
        private CurrencyInfo eur;

        public List<CurrencyInfo> getAllCurrencyInfo() {
            return Arrays.asList(usd, gbp, eur);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrencyInfo {
        private String code;
        private String symbol;
        private String rate;
        private String description;
        @JsonProperty("rate_float")
        private double rateFloat;
    }
}
