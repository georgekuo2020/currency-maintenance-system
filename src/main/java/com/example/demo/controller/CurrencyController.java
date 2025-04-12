package com.example.demo.controller;

import com.example.demo.entity.Currency;
import com.example.demo.service.CurrencyService;
import com.example.demo.vo.CurrencyUpdateVO;
import com.example.demo.vo.web.BaseResponse;
import com.example.demo.vo.CurrencyCreationVO;
import com.example.demo.vo.web.CurrencyListResponse;
import com.example.demo.vo.web.SuccessfullyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "幣別 - 維護")
@RestController
@RequestMapping("/currency/v1/")
public class CurrencyController {

    private CurrencyService currencyService;

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Operation(summary = "新增幣別", description = "")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse> create(
            @RequestBody CurrencyCreationVO currencyCreationVO) {

        Currency currency = currencyService.createCurrency(currencyCreationVO.getCurrencyCode(),
                currencyCreationVO.getCurrencyName());

        return ResponseEntity.ok(
                new SuccessfullyResponse<>("幣別新增成功", currency));
    }

    @Operation(summary = "使用 id 查詢幣別", description = "")
    @GetMapping("/query")
    public ResponseEntity<BaseResponse> query(
            @RequestParam(name = "id", defaultValue = "77bfcf73cfb24b76ae5137506f4a9529") String id) {

        Currency currency = currencyService.getCurrencyById(id);

        return ResponseEntity.ok(
                new SuccessfullyResponse<>("查詢成功", currency));
    }

    @Operation(summary = "幣別清單", description = "")
    @GetMapping("/list")
    public ResponseEntity<BaseResponse> list(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<Currency> currencyPage = currencyService.getAllCurrencyList(page - 1, size);

        return ResponseEntity.ok(
                new CurrencyListResponse(currencyPage));
    }

    @Operation(summary = "更新幣別", description = "")
    @PatchMapping("/update")
    public ResponseEntity<BaseResponse> update(
            @RequestBody CurrencyUpdateVO currencyUpdateVO) {

        Currency currency = currencyService.updateCurrency(currencyUpdateVO.getCurrencyId(),
                currencyUpdateVO.getCurrencyCode(),
                currencyUpdateVO.getCurrencyName());

        return ResponseEntity.ok(
                new SuccessfullyResponse<>("幣別更新成功", currency));
    }

    @Operation(summary = "刪除幣別", description = "")
    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> delete(
            @RequestParam(name = "currency_id") String currencyId) {

        currencyService.deleteCurrencyById(currencyId);

        return ResponseEntity.ok(
                new SuccessfullyResponse<>("刪除幣別成功", ""));
    }

}
