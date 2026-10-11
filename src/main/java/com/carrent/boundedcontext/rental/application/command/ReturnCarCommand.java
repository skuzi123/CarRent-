package com.carrent.boundedcontext.rental.application.command;

import lombok.Value;

@Value
public class ReturnCarCommand {
    Long rentalId;
    Long newMileage;
}
