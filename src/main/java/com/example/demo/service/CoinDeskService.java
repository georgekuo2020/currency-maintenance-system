package com.example.demo.service;

import com.example.demo.client.CoinDeskClient;
import com.example.demo.entity.Currency;
import com.example.demo.util.Constants;
import com.example.demo.util.DateUtil;
import com.example.demo.vo.CoinDeskCurrencyInfoResponse;
import com.example.demo.vo.CoinDeskVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class CoinDeskService {

    private CoinDeskClient coinDeskClient;

    private CurrencyService currencyService;

    @Autowired
    public void setCoinDeskClient(CoinDeskClient coinDeskClient) {
        this.coinDeskClient = coinDeskClient;
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * 取得 CoinDesk API 回傳的資料
     *
     * @return CoinDeskVO
     */
    public CoinDeskVO getCoinDeskData() {
        CoinDeskCurrencyInfoResponse coinDeskCurrencyInfoResponse = coinDeskClient.getCoinDeskCurrencyInfo();
        if (coinDeskCurrencyInfoResponse == null) {
            log.error("CoinDesk API response is null");
            return null;
        }
        Map<String, Currency> currencyMap = currencyService.getAllCurrencyMap();
        return convertCoinDeskResponse2VO(coinDeskCurrencyInfoResponse, currencyMap);
    }

    /**
     * 將 CoinDesk API 回傳的資料 比對 幣別維護資料 並 轉換成 CoinDeskVO 回傳
     *
     * @param coinDeskCurrencyInfoResponse CoinDesk API 回傳的資料
     * @param currencyMap 幣別對應表
     * @return CoinDeskVO
     */
    private CoinDeskVO convertCoinDeskResponse2VO(CoinDeskCurrencyInfoResponse coinDeskCurrencyInfoResponse,
                                                  Map<String, Currency> currencyMap) {
        List<CoinDeskVO.CurrencyVO> currencyVOList = new ArrayList<>();
        for (CoinDeskCurrencyInfoResponse.CurrencyInfo currencyInfo : coinDeskCurrencyInfoResponse.getBpi().getAllCurrencyInfo()) {
            CoinDeskVO.CurrencyVO currencyVO = CoinDeskVO.CurrencyVO.createInstance(
                    currencyInfo.getCode(),
                    currencyMap.containsKey(currencyInfo.getCode()) ? currencyMap.get(currencyInfo.getCode()).getCurrencyName() : "查無此幣別，請至 幣別-維護 新增",
                    currencyInfo.getRate(),
                    currencyInfo.getRateFloat()
            );
            currencyVOList.add(currencyVO);
        }
        LocalDateTime isoLocalDateTime = DateUtil.parseISO8601Time2LocalDateTime(coinDeskCurrencyInfoResponse.getTime().getUpdatedISO());
        String isoDateString = isoLocalDateTime.format(DateTimeFormatter.ofPattern(Constants.WEB_DATE_FORMAT));
        return CoinDeskVO.createInstance(isoDateString, currencyVOList);
    }

}
