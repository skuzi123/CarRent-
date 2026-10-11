package com.carrent.boundedcontext.rental.domain.service;

import com.carrent.boundedcontext.rental.domain.model.valueobject.Money;
import com.carrent.boundedcontext.rental.domain.model.valueobject.RentalPeriod;

import java.math.BigDecimal;
import java.util.Currency;

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
