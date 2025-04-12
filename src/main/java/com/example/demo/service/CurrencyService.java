package com.example.demo.service;

import com.example.demo.config.exception.CustomizeException;
import com.example.demo.entity.Currency;
import com.example.demo.enums.WebError;
import com.example.demo.repository.CurrencyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CurrencyService {

    private CurrencyRepository currencyRepository;

    @Autowired
    public void setCurrencyRepository(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * 新增 幣別
     *
     * @param currencyCode 幣別 代號
     * @param currencyName 幣別 中文名
     * @return Currency
     */
    public Currency createCurrency(String currencyCode, String currencyName) {
        if (isCurrencyCodeExists(currencyCode)) {
            log.error("Currency with code {} already exists", currencyCode);
            throw new CustomizeException(WebError.CURRENCY_CODE_AlREADY_EXISTS);
        }

        Currency currency = new Currency();
        currency.setCurrencyCode(currencyCode);
        currency.setCurrencyName(currencyName);

        currency = currencyRepository.save(currency);
        log.info("Currency {} created successfully", currencyCode);

        return currency;
    }

    /**
     * 更新 幣別
     *
     * @param currencyId   幣別 id
     * @param currencyCode 幣別 代號
     * @param currencyName 幣別 中文名
     * @return Currency
     */
    public Currency updateCurrency(String currencyId, String currencyCode, String currencyName) {
        Currency currency = currencyRepository.findByIdAndIsDelete(currencyId, false);
        if (currency == null) {
            log.error("Currency with id {} not found", currencyId);
            throw new CustomizeException(WebError.CURRENCY_ID_NOT_FOUND);
        }
        if (!currency.getCurrencyCode().equals(currencyCode) && isCurrencyCodeExists(currencyCode)) {
            log.error("Currency with code {} already exists", currencyCode);
            throw new CustomizeException(WebError.CURRENCY_CODE_AlREADY_EXISTS);
        }
        currency.setCurrencyCode(currencyCode);
        currency.setCurrencyName(currencyName);
        currency = currencyRepository.save(currency);
        log.info("Currency id {} update successfully", currencyId);
        return currency;
    }

    /**
     * 根據 id 查詢 幣別
     *
     * @param currencyId 幣別 id
     * @return Currency
     */
    public Currency getCurrencyById(String currencyId) {
        Currency currency = currencyRepository.findByIdAndIsDelete(currencyId, false);
        if (currency == null) {
            log.error("Currency with id {} not found", currencyId);
            throw new CustomizeException(WebError.CURRENCY_ID_NOT_FOUND);
        }
        return currency;
    }

    /**
     * 查詢 所有幣別
     *
     * @param page 當前頁碼
     * @param size 每頁大小
     * @return Page<Currency>
     */
    public Page<Currency> getAllCurrencyList(int page, int size) {
        return currencyRepository.findByIsDelete(false, PageRequest.of(page, size));
    }

    /**
     * 刪除 幣別
     *
     * @param currencyId 幣別 id
     */
    public void deleteCurrencyById(String currencyId) {
        Currency currency = currencyRepository.findByIdAndIsDelete(currencyId, false);
        if (currency == null) {
            log.error("Currency with id {} not found", currencyId);
            throw new CustomizeException(WebError.CURRENCY_ID_NOT_FOUND);
        }
        currency.setDelete(true);
        currencyRepository.save(currency);
        log.info("Currency id {} deleted successfully", currencyId);
    }

    /**
     * 檢查 幣別代碼 是否存在
     *
     * @param currencyCode 幣別 代號
     * @return boolean
     */
    public boolean isCurrencyCodeExists(String currencyCode) {
        return currencyRepository.findByCurrencyCodeAndIsDelete(currencyCode, false) != null;
    }

    public Map<String, Currency> getAllCurrencyMap() {
        List<Currency> allCurrencies = currencyRepository.findAllByIsDelete(false);
        return allCurrencies.stream()
                .collect(Collectors.toMap(Currency::getCurrencyCode, currency -> currency));
    }
}
