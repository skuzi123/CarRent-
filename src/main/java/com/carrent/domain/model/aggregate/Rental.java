package com.carrent.domain.model.aggregate;

import com.carrent.domain.model.valueobject.Money;
import com.carrent.domain.model.valueobject.RentalPeriod;
import com.carrent.domain.model.valueobject.RentalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
public class Rental {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Embedded
    private RentalPeriod rentalPeriod;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "total_cost_amount")),
        @AttributeOverride(name = "currencyCode", column = @Column(name = "total_cost_currency"))
    })
    private Money totalCost;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Rental(Customer customer, Car car, RentalPeriod rentalPeriod, Money totalCost) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }
        if (rentalPeriod == null) {
            throw new IllegalArgumentException("Rental period cannot be null");
        }
        if (totalCost == null) {
            throw new IllegalArgumentException("Total cost cannot be null");
        }
        if (!customer.isActive()) {
            throw new IllegalStateException("Customer is not active");
        }
        if (!customer.isLicenseValid()) {
            throw new IllegalStateException("Customer license is expired or invalid");
        }
        if (!car.isAvailable()) {
            throw new IllegalStateException("Car is not available");
        }
        
        this.customer = customer;
        this.car = car;
        this.rentalPeriod = rentalPeriod;
        this.totalCost = totalCost;
        this.status = RentalStatus.PENDING;
    }

    public void activate() {
        if (this.status != RentalStatus.PENDING) {
            throw new IllegalStateException("Only pending rentals can be activated");
        }
        this.status = RentalStatus.ACTIVE;
        this.car.rent();
    }

    public void complete() {
        if (this.status != RentalStatus.ACTIVE) {
            throw new IllegalStateException("Only active rentals can be completed");
        }
        this.status = RentalStatus.COMPLETED;
        this.car.returnCar(null);
    }

    public void cancel() {
        if (this.status == RentalStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed rental");
        }
        RentalStatus previousStatus = this.status;
        this.status = RentalStatus.CANCELLED;
        if (previousStatus == RentalStatus.ACTIVE) {
            this.car.returnCar(null);
        }
    }
}
