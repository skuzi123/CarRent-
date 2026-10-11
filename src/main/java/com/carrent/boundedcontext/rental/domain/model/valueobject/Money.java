package com.carrent.boundedcontext.rental.domain.model.valueobject;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Currency;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Money {
    BigDecimal amount;
    String currencyCode;

    public Money(BigDecimal amount, Currency currency) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        this.amount = amount;
        this.currencyCode = currency.getCurrencyCode();
    }

    public Money(BigDecimal amount, String currencyCode) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        if (currencyCode == null || currencyCode.isBlank()) {
            throw new IllegalArgumentException("Currency code cannot be null or empty");
        }
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public Currency getCurrency() {
        return Currency.getInstance(currencyCode);
    }

    public Money add(Money other) {
        if (!this.currencyCode.equals(other.currencyCode)) {
            throw new IllegalArgumentException("Cannot add different currencies");
        }
        return new Money(this.amount.add(other.amount), this.currencyCode);
    }

    public Money multiply(BigDecimal multiplier) {
        return new Money(this.amount.multiply(multiplier), this.currencyCode);
    }
}
