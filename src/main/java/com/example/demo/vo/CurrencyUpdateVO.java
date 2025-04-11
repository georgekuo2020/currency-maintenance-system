package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CurrencyUpdateVO {

    @NotNull
    @Schema(name = "currency_id",
            description = "幣別 id",
            type = "java.lang.String",
            example = "77bfcf73cfb24b76ae5137506f4a9529")
    @JsonProperty("currency_id")
    private String currencyId;

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
