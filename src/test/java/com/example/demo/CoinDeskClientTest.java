package com.example.demo;

import com.example.demo.client.CoinDeskClient;
import com.example.demo.vo.CoinDeskCurrencyInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CoinDeskClientTest {

    @InjectMocks
    private CoinDeskClient coinDeskClient; // 假設您的 Service 類別名稱是 CoinDeskService

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(coinDeskClient, "coinDeskHost", "https://kengp3.github.io");
        ReflectionTestUtils.setField(coinDeskClient, "coinDeskApiVersion", "blog");
    }

    /**
     * 測試 coin desk api
     */
    @Test
    void getCoinDeskCurrencyInfo_ApiCallSuccess_ReturnsResponse() {
        // =========    Arrange     ==========
        String serviceUrl = coinDeskClient.getBaseUrl() + "/coindesk.json";
        CoinDeskCurrencyInfoResponse mockResponse = new CoinDeskCurrencyInfoResponse();
        CoinDeskCurrencyInfoResponse.Time time = new CoinDeskCurrencyInfoResponse.Time();
        time.setUpdated("Sep 2, 2024 07:07:20 UTC");
        mockResponse.setTime(time);
        CoinDeskCurrencyInfoResponse.Bpi bpi = new CoinDeskCurrencyInfoResponse.Bpi();
        CoinDeskCurrencyInfoResponse.CurrencyInfo usd = new CoinDeskCurrencyInfoResponse.CurrencyInfo();
        usd.setCode("USD");
        bpi.setUsd(usd);
        mockResponse.setBpi(bpi);

        ResponseEntity<CoinDeskCurrencyInfoResponse> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(serviceUrl, CoinDeskCurrencyInfoResponse.class)).thenReturn(responseEntity);

        // ==========      Act      ===========
        CoinDeskCurrencyInfoResponse actualResponse = coinDeskClient.getCoinDeskCurrencyInfo();

        // ==========     Assert    ===========
        assertNotNull(actualResponse);
        assertEquals("Sep 2, 2024 07:07:20 UTC", actualResponse.getTime().getUpdated());
        assertNotNull(actualResponse.getBpi().getUsd());
        assertEquals("USD", actualResponse.getBpi().getUsd().getCode());
    }
}
