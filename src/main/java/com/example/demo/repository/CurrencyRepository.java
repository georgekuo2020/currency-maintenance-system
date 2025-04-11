package com.example.demo.repository;

import com.example.demo.entity.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

    Currency findByIdAndIsDelete(String id, boolean isDelete);

    Currency findByCurrencyCodeAndIsDelete(String currencyCode, boolean isDelete);

    Page<Currency> findByIsDelete(boolean isDelete, Pageable pageable);
}
