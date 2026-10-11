package com.carrent.boundedcontext.customer.domain.model.aggregate;

import com.carrent.boundedcontext.customer.domain.model.valueobject.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Customer {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String licenseNumber;
    private LocalDateTime licenseExpiryDate;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer(
            String firstName,
            String lastName,
            String email,
            String phone,
            String licenseNumber,
            LocalDateTime licenseExpiryDate) {
        this(
                null,
                firstName,
                lastName,
                email,
                phone,
                licenseNumber,
                licenseExpiryDate,
                CustomerStatus.ACTIVE,
                null,
                null);
    }

    public static Customer restore(
            Long id,
            String firstName,
            String lastName,
            String email,
            String phone,
            String licenseNumber,
            LocalDateTime licenseExpiryDate,
            CustomerStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        return new Customer(
                id, firstName, lastName, email, phone, licenseNumber,
                licenseExpiryDate, status, createdAt, updatedAt);
    }

    private Customer(
            Long id,
            String firstName,
            String lastName,
            String email,
            String phone,
            String licenseNumber,
            LocalDateTime licenseExpiryDate,
            CustomerStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        validateProfile(firstName, lastName, email, licenseNumber);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.licenseExpiryDate = licenseExpiryDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private static void validateProfile(
            String firstName, String lastName, String email, String licenseNumber) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        if (licenseNumber == null || licenseNumber.isBlank()) {
            throw new IllegalArgumentException("License number cannot be null or empty");
        }
    }

    public void suspend() {
        if (this.status == CustomerStatus.SUSPENDED) {
            throw new IllegalStateException("Customer is already suspended");
        }
        this.status = CustomerStatus.SUSPENDED;
    }

    public void activate() {
        this.status = CustomerStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = CustomerStatus.INACTIVE;
    }

    public boolean isActive() {
        return this.status == CustomerStatus.ACTIVE;
    }

    public boolean isLicenseValid() {
        return licenseExpiryDate != null && licenseExpiryDate.isAfter(LocalDateTime.now());
    }
}
