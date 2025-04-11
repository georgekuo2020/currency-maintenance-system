package com.example.demo.config.exception;

import com.example.demo.enums.WebError;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomizeException extends BaseException {
    public CustomizeException(WebError webError) {
        this.setCode(webError.getCode());
        this.setMessage(webError.getMessage());
    }
}
