package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinDeskVO {

    @JsonProperty("update_time")
    private String updateTime;

    List<CurrencyVO> currencyList;

    public static CoinDeskVO createInstance(String updateTime, List<CurrencyVO> currencyList) {
        CoinDeskVO coinDeskVO = new CoinDeskVO();
        coinDeskVO.setUpdateTime(updateTime);
        coinDeskVO.setCurrencyList(currencyList);
        return coinDeskVO;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrencyVO {
        private String code;
        @JsonProperty("chinese_name")
        private String chineseName;
        private String rate;
        @JsonProperty("rate_float")
        private double rateFloat;

        public static CurrencyVO createInstance(String code, String chineseName, String rate, double rateFloat) {
            CurrencyVO currencyVO = new CurrencyVO();
            currencyVO.setCode(code);
            currencyVO.setChineseName(chineseName);
            currencyVO.setRate(rate);
            currencyVO.setRateFloat(rateFloat);
            return currencyVO;
        }
    }

}
