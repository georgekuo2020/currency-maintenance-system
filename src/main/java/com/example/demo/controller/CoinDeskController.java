package com.example.demo.controller;

import com.example.demo.service.CoinDeskService;
import com.example.demo.vo.CoinDeskVO;
import com.example.demo.vo.web.SuccessfullyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Coin Desk API - 幣別查詢服務")
@RestController
@RequestMapping("/coinDesk/v1/")
public class CoinDeskController {

    private CoinDeskService coinDeskService;

    @Autowired
    public void setCoinDeskService(CoinDeskService coinDeskService) {
        this.coinDeskService = coinDeskService;
    }

    @Operation(summary = "取得 幣別資訊 轉換結果", description = "")
    @GetMapping("/getData")
    public ResponseEntity<?> getCoinDeskData() {

        CoinDeskVO coinDeskVO = coinDeskService.getCoinDeskData();

        return ResponseEntity.ok(
                new SuccessfullyResponse<>("查詢成功", coinDeskVO));
    }
}
