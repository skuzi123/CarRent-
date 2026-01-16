package com.carrent.application.dto;

import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class RentalDTO {
    Long id;
    Long customerId;
    String customerName;
    Long carId;
    String carInfo;
    LocalDateTime startDate;
    LocalDateTime endDate;
    BigDecimal totalCostAmount;
    String totalCostCurrency;
    String status;
    LocalDateTime createdAt;
}
