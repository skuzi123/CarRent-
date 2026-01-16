package com.carrent.domain.model.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Value;

@Embeddable
@Value
public class CarSpecification {
    @Column(name = "make", nullable = false)
    String make;
    
    @Column(name = "model", nullable = false)
    String model;
    
    @Column(name = "year", nullable = false)
    Integer year;
    
    @Column(name = "color")
    String color;

    public CarSpecification(String make, String model, Integer year, String color) {
        if (make == null || make.isBlank()) {
            throw new IllegalArgumentException("Make cannot be null or empty");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        if (year == null || year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Year must be between 1900 and 2100");
        }
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
    }
}
