package com.example.demo.client;

import com.example.demo.vo.CoinDeskCurrencyInfoResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class CoinDeskClient {

    @Value("${coin.desk.host}")
    private String coinDeskHost;

    @Value("${coin.desk.api.version}")
    private String coinDeskApiVersion;

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 取的 幣別 & 匯率 資訊
     *
     * @return CoinDeskCurrencyInfoResponse
     */
    public CoinDeskCurrencyInfoResponse getCoinDeskCurrencyInfo() {
        String serviceUrl = getBaseUrl() + "/coindesk.json";
        try {
            log.info("sending request to {} ", serviceUrl);
            ResponseEntity<CoinDeskCurrencyInfoResponse> response = restTemplate.getForEntity(serviceUrl, CoinDeskCurrencyInfoResponse.class);
            if (!(response.getStatusCode() == HttpStatus.OK)) {
                log.error("[{}] receive response with error status code", response.getStatusCode());
                return null;
            }
            CoinDeskCurrencyInfoResponse coinDeskCurrencyInfoResponse = response.getBody();
            if (coinDeskCurrencyInfoResponse == null) {
                log.error("[{}] receive response with empty body", response.getStatusCode());
                return null;
            }
            log.info("[{}] receive response success", response.getStatusCode());
            return coinDeskCurrencyInfoResponse;
        } catch (Exception e) {
            log.error("sending request to {} fail, error message: {}", serviceUrl, e.getMessage());
            return null;
        }
    }

    public String getBaseUrl() {
        return coinDeskHost + "/" + coinDeskApiVersion;
    }
}
