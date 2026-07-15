package com.jamarlesf.plazoletatrace.infrastructure.configuration;

import com.jamarlesf.plazoletatrace.domain.api.IOrderLogServicePort;
import com.jamarlesf.plazoletatrace.domain.spi.IOrderLogPersistencePort;
import com.jamarlesf.plazoletatrace.domain.usecase.OrderLogUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IOrderLogPersistencePort orderLogPersistencePort;

    @Bean
    public IOrderLogServicePort orderLogServicePort() {
        return new OrderLogUseCase(orderLogPersistencePort);
    }
}
