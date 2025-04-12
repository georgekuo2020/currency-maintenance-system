package com.example.demo;

import com.example.demo.entity.Currency;
import com.example.demo.service.CurrencyService;
import com.example.demo.vo.CurrencyCreationVO;
import com.example.demo.vo.CurrencyUpdateVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class CurrencyServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    /**
     * 1.查詢 幣別對應表資料 API 功能測試
     */
    @Test
    @DisplayName("QUERY_CURRENCY")
    void getCurrencyById() throws Exception {

        // =========    Arrange     ==========
        String id = UUID.randomUUID().toString().replace("-", "");
        Currency mockCurrency = new Currency();
        mockCurrency.setId(id);
        mockCurrency.setCurrencyCode("USD");
        mockCurrency.setCurrencyName("美金");
        mockCurrency.setDelete(false);
        // 設定 Mock Service 的行為
        when(currencyService.getCurrencyById(id)).thenReturn(mockCurrency);

        // ==========      Act      ===========
        // ==========     Assert    ===========
        // 執行及比對
        mockMvc
                .perform(
                        get("/currency/v1/query")
                                .param("id", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(mockCurrency.getId()));
    }

    /**
     * 2.新增 幣別對應表資料 API 功能測試
     */
    @Test
    @DisplayName("CREATE_CURRENCY")
    void createCurrency() throws Exception {

        // =========    Arrange     ==========
        CurrencyCreationVO creationRequest = new CurrencyCreationVO();
        creationRequest.setCurrencyCode("USD");
        creationRequest.setCurrencyName("美金");
        Currency mockCurrency = new Currency();
        mockCurrency.setId(UUID.randomUUID().toString().replace("-", ""));
        mockCurrency.setCurrencyCode("USD");
        mockCurrency.setCurrencyName("美金");
        mockCurrency.setDelete(false);
        // 設定 Mock Service 的行為
        when(currencyService.createCurrency(creationRequest.getCurrencyCode(), creationRequest.getCurrencyName())).thenReturn(mockCurrency);

        // ==========      Act      ===========
        // ==========     Assert    ===========
        // 執行及比對
        mockMvc
                .perform(
                        post("/currency/v1/create")
                                .content(asJsonString(creationRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(mockCurrency.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.currencyCode").value("USD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.currencyName").value("美金"));
    }

    /**
     * 3.更新 幣別對應表資料 API 功能測試
     */
    @Test
    @DisplayName("UPDATE_CURRENCY")
    void updateCurrency() throws Exception {

        // =========    Arrange     ==========
        String id = UUID.randomUUID().toString().replace("-", "");
        CurrencyUpdateVO updateRequest = new CurrencyUpdateVO();
        updateRequest.setCurrencyId(id);
        updateRequest.setCurrencyCode("USD");
        updateRequest.setCurrencyName("美金");
        Currency mockCurrency = new Currency();
        mockCurrency.setId(id);
        mockCurrency.setCurrencyCode("USD");
        mockCurrency.setCurrencyName("美金");
        mockCurrency.setDelete(false);
        // 設定 Mock Service 的行為
        when(currencyService.updateCurrency(updateRequest.getCurrencyId(), updateRequest.getCurrencyCode(), updateRequest.getCurrencyName())).thenReturn(mockCurrency);

        // ==========      Act      ===========
        // ==========     Assert    ===========
        // 執行及比對
        mockMvc
                .perform(
                        patch("/currency/v1/update")
                                .content(asJsonString(updateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(mockCurrency.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.currencyCode").value("USD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.currencyName").value("美金"));
    }

    /**
     * 4.刪除 幣別對應表資料 API 功能測試
     */
    @Test
    @DisplayName("DELETE_CURRENCY")
    void deleteCurrency() throws Exception {

        // =========    Arrange     ==========
        String id = UUID.randomUUID().toString().replace("-", "");

        // ==========      Act      ===========
        // ==========     Assert    ===========
        // 執行及比對
        mockMvc
                .perform(
                        delete("/currency/v1/delete")
                                .param("currency_id", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"));
    }

//    @Test
//    void getCurrencyById_ExistingIdAndNotDeleted_ReturnsCurrency() {
//        // =========    Arrange     ==========
//        // 準備測試資料
//        String currencyId = "testId";
//        Currency mockCurrency = new Currency();
//        mockCurrency.setId(currencyId);
//        mockCurrency.setCurrencyCode("USD");
//        mockCurrency.setCurrencyName("美元");
//        mockCurrency.setDelete(false);
//        // 設定 Mock Repository 的 行為
//        when(currencyRepository.findByIdAndIsDelete(currencyId, false)).thenReturn(mockCurrency);
//
//        // ==========      Act      ===========
//        // 執行測試方法
//        Currency actualCurrency = currencyService.getCurrencyById(currencyId);
//
//        // ==========     Assert    ===========
//        // 驗證結果
//        assertEquals(mockCurrency.getId(), actualCurrency.getId());
//        assertEquals(mockCurrency.getCurrencyCode(), actualCurrency.getCurrencyCode());
//        assertEquals(mockCurrency.getCurrencyName(), actualCurrency.getCurrencyName());
//    }
//
//    @Test
//    void getCurrencyById_NonExistingId_ThrowsCurrencyNotFoundException() {
//        // =========    Arrange     ==========
//        // 準備測試資料
//        String currencyId = "nonExistingId";
//        // 設定 Mock Repository 的行為，當找不到時返回 exception
//        when(currencyRepository.findByIdAndIsDelete(currencyId, false))
//                .thenThrow(new CustomizeException(WebError.CURRENCY_ID_NOT_FOUND));
//
//        // ==========      Act      ===========
//        // 執行測試方法並驗證是否拋出預期的異常
//        CustomizeException exception = assertThrows(CustomizeException.class, () -> {
//            currencyService.getCurrencyById(currencyId);
//        });
//
//        // ==========     Assert    ===========
//        // 驗證異常的錯誤代碼是否正確
//        assertEquals(WebError.CURRENCY_ID_NOT_FOUND.getCode(), exception.getCode());
//    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
