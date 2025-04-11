package com.example.demo.entity;

import com.example.demo.config.CustomIdentifierGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "currency")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Currency {

    @Id
    @GeneratedValue(generator = "customIdentifierGenerator")
    @GenericGenerator(name = "customIdentifierGenerator", strategy = "com.example.demo.config.CustomIdentifierGenerator")
    @Column(name = "id", unique = true, nullable = false, length = 32)
    private String id;

    @Column(name = "currency_code", unique = true, nullable = false, length = 16)
    private String currencyCode;

    @Column(name = "currency_name", nullable = false, length = 64)
    private String currencyName;

    @Column(name = "is_delete")
    private boolean isDelete = false;

    @Column(name = "create_datetime")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    private LocalDateTime updateDatetime;
}
