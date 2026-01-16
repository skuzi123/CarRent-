package com.carrent.application.dto;

import lombok.Value;
import java.math.BigDecimal;

@Value
public class CarDTO {
    Long id;
    String make;
    String model;
    Integer year;
    String color;
    String licensePlateNumber;
    String licensePlateCountry;
    Long mileage;
    String status;
}
