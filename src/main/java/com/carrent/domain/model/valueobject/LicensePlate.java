package com.carrent.domain.model.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Value;

@Embeddable
@Value
public class LicensePlate {
    @Column(name = "license_plate_number", nullable = false, unique = true)
    String number;
    
    @Column(name = "license_plate_country", nullable = false)
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
