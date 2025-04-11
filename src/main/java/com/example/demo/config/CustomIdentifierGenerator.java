package com.example.demo.config;

import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.UUID;

@Configuration
public class CustomIdentifierGenerator extends UUIDGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
