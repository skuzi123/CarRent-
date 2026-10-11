package com.carrent.boundedcontext.rental.application.dto;

import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class RentalDTO {
    Long id;
    Long customerId;
    Long carId;
    LocalDateTime startDate;
    LocalDateTime endDate;
    BigDecimal totalCostAmount;
    String totalCostCurrency;
    String status;
    LocalDateTime createdAt;
}
