package com.carrent.boundedcontext.customer.application.dto;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class CustomerDTO {
    Long id;
    String firstName;
    String lastName;
    String email;
    String phone;
    String licenseNumber;
    LocalDateTime licenseExpiryDate;
    String status;
    LocalDateTime createdAt;
}
