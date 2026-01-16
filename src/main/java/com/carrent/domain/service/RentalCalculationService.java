package com.carrent.domain.service;

import com.carrent.domain.model.valueobject.Money;
import com.carrent.domain.model.valueobject.RentalPeriod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
public class RentalCalculationService {

    private static final BigDecimal DAILY_RATE = new BigDecimal("50.00");
    private static final String DEFAULT_CURRENCY = "USD";

    public Money calculateRentalCost(RentalPeriod rentalPeriod) {
        long days = rentalPeriod.getDays();
        if (days <= 0) {
            throw new IllegalArgumentException("Rental period must be at least 1 day");
        }
        
        BigDecimal totalAmount = DAILY_RATE.multiply(BigDecimal.valueOf(days));
        return new Money(totalAmount, Currency.getInstance(DEFAULT_CURRENCY));
    }

    public Money calculateRentalCost(RentalPeriod rentalPeriod, BigDecimal dailyRate) {
        long days = rentalPeriod.getDays();
        if (days <= 0) {
            throw new IllegalArgumentException("Rental period must be at least 1 day");
        }
        if (dailyRate == null || dailyRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Daily rate must be positive");
        }
        
        BigDecimal totalAmount = dailyRate.multiply(BigDecimal.valueOf(days));
        return new Money(totalAmount, Currency.getInstance(DEFAULT_CURRENCY));
    }
}
