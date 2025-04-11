package com.example.demo.vo.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponse {

    @Schema(type = "java.lang.Integer",
            description = "狀態碼",
            example = "200")
    public int statusCode;

    @Schema(type = "java.lang.String",
            description = "系統回覆訊息",
            example = "success")
    public String message;

}
