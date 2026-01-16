package com.carrent.application.command;

import lombok.Value;

@Value
public class CreateCarCommand {
    String make;
    String model;
    Integer year;
    String color;
    String licensePlateNumber;
    String licensePlateCountry;
    Long mileage;
}
