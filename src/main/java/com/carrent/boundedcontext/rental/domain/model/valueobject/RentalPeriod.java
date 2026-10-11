package com.carrent.boundedcontext.rental.domain.model.valueobject;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class RentalPeriod {
    LocalDateTime startDate;
    LocalDateTime endDate;

    public RentalPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getDays() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public boolean overlaps(RentalPeriod other) {
        return this.startDate.isBefore(other.endDate) && this.endDate.isAfter(other.startDate);
    }
}
