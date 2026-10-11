package com.carrent.boundedcontext.rental.domain.model.aggregate;

import com.carrent.boundedcontext.rental.domain.model.valueobject.Money;
import com.carrent.boundedcontext.rental.domain.model.valueobject.RentalPeriod;
import com.carrent.boundedcontext.rental.domain.model.valueobject.RentalStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Rental {

    private Long id;
    private final Long customerId;
    private final Long carId;
    private final RentalPeriod rentalPeriod;
    private RentalStatus status;
    private final Money totalCost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Rental(Long customerId, Long carId, RentalPeriod rentalPeriod, Money totalCost) {
        this(null, customerId, carId, rentalPeriod, RentalStatus.PENDING, totalCost, null, null);
    }

    public static Rental restore(
            Long id,
            Long customerId,
            Long carId,
            RentalPeriod rentalPeriod,
            RentalStatus status,
            Money totalCost,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        return new Rental(id, customerId, carId, rentalPeriod, status, totalCost, createdAt, updatedAt);
    }

    private Rental(
            Long id,
            Long customerId,
            Long carId,
            RentalPeriod rentalPeriod,
            RentalStatus status,
            Money totalCost,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.carId = carId;
        this.rentalPeriod = rentalPeriod;
        this.status = status;
        this.totalCost = totalCost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void activate() {
        if (this.status != RentalStatus.PENDING) {
            throw new IllegalStateException("Only pending rentals can be activated");
        }
        this.status = RentalStatus.ACTIVE;
    }

    public void complete() {
        if (this.status != RentalStatus.ACTIVE) {
            throw new IllegalStateException("Only active rentals can be completed");
        }
        this.status = RentalStatus.COMPLETED;
    }

    public void cancel() {
        if (this.status == RentalStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed rental");
        }
        this.status = RentalStatus.CANCELLED;
    }
}
