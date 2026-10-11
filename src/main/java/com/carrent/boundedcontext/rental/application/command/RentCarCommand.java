package com.carrent.boundedcontext.rental.application.command;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class RentCarCommand {
    Long customerId;
    Long carId;
    LocalDateTime startDate;
    LocalDateTime endDate;
}
