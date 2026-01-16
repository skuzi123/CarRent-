package com.carrent.domain.model.aggregate;

import com.carrent.domain.model.valueobject.CustomerStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String licenseNumber;
    private LocalDateTime licenseExpiryDate;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Rental> rentals = new ArrayList<>();

    public Customer(String firstName, String lastName, String email, String phone, String licenseNumber, LocalDateTime licenseExpiryDate) {
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
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.licenseExpiryDate = licenseExpiryDate;
        this.status = CustomerStatus.ACTIVE;
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
