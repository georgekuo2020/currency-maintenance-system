package com.example.demo.vo.web;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SuccessfullyResponse<T> extends BaseResponse {

    public T data;

    public SuccessfullyResponse(T data) {
        this.statusCode = 200;
        this.message = "success";
        this.data = data;
    }
}
