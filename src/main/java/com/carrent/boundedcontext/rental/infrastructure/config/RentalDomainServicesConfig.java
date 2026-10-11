package com.carrent.boundedcontext.rental.infrastructure.config;

import com.carrent.boundedcontext.rental.domain.repository.RentalRepository;
import com.carrent.boundedcontext.rental.domain.service.RentalCalculationService;
import com.carrent.boundedcontext.rental.domain.service.RentalValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentalDomainServicesConfig {

    @Bean
    public RentalCalculationService rentalCalculationService() {
        return new RentalCalculationService();
    }

    @Bean
    public RentalValidationService rentalValidationService(RentalRepository rentalRepository) {
        return new RentalValidationService(rentalRepository);
    }
}
