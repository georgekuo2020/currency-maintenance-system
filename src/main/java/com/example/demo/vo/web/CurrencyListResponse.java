package com.example.demo.vo.web;

import com.example.demo.entity.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CurrencyListResponse extends BaseResponse {

    @Schema(type = "java.lang.Integer",
            description = "總頁數",
            example = "8")
    public int totalPage;

    @Schema(type = "java.lang.Long",
            description = "總數",
            example = "80")
    public long totalCount;

    public List<Currency> data;

    public CurrencyListResponse(Page<Currency> pageResult) {
        this.statusCode = 200;
        this.message = "success";
        this.totalPage = pageResult.getTotalPages();
        this.totalCount = pageResult.getTotalElements();
        this.data = pageResult.getContent();
    }

}
