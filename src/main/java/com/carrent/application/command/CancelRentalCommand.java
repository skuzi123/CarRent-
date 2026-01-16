package com.carrent.application.command;

import lombok.Value;

@Value
public class CancelRentalCommand {
    Long rentalId;
}
