package com.carrent.application.command;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class CreateCustomerCommand {
    String firstName;
    String lastName;
    String email;
    String phone;
    String licenseNumber;
    LocalDateTime licenseExpiryDate;
}
