package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyCreationVO {

    @NotNull
    @Schema(name = "currency_code",
            description = "幣別代號",
            type = "java.lang.String",
            example = "USD")
    @JsonProperty("currency_code")
    private String currencyCode;

    @NotNull
    @Schema(name = "currency_name",
            description = "幣別名稱",
            type = "java.lang.String",
            example = "美元")
    @JsonProperty("currency_name")
    private String currencyName;

}
