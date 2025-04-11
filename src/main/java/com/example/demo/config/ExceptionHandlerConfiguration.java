package com.example.demo.config;

import com.example.demo.config.exception.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerConfiguration {

    @ExceptionHandler(BaseException.class)
    public Object baseException(BaseException baseException) {

        return ResponseEntity.status(HttpStatus.OK).body(getResponseBody(baseException));
    }

    @ExceptionHandler(NullPointerException.class)
    public Object nullPointerExceptionHandler(NullPointerException nullPointerException) {

        log.error("NullPointerExceptionHandler => ", nullPointerException.fillInStackTrace());

        ObjectNode response = new ObjectNode(JsonNodeFactory.instance);

        response.put("statusCode", 9999);
        response.put("message", "系統執行錯誤");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private JsonNode getResponseBody(BaseException ex) {

        ObjectNode response = new ObjectNode(JsonNodeFactory.instance);

        response.put("statusCode", ex.getCode());
        response.put("message", ex.getMessage());

        return response;

    }

}
