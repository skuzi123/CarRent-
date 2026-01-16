package com.carrent.application.command;

import lombok.Value;

@Value
public class ReturnCarCommand {
    Long rentalId;
    Long newMileage;
}
