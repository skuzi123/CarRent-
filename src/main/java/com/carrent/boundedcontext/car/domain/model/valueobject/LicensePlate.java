package com.carrent.boundedcontext.car.domain.model.valueobject;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class LicensePlate {
    String number;
    String country;

    public LicensePlate(String number, String country) {
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("License plate number cannot be null or empty");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
        this.number = number;
        this.country = country;
    }
}
