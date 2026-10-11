package com.carrent.boundedcontext.rental.infrastructure.repository;

import com.carrent.boundedcontext.rental.domain.model.aggregate.Rental;
import com.carrent.boundedcontext.rental.domain.model.valueobject.Money;
import com.carrent.boundedcontext.rental.domain.model.valueobject.RentalPeriod;
import com.carrent.boundedcontext.rental.infrastructure.repository.persistence.RentalJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RentalPersistenceMapper {

    public Rental toDomain(RentalJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return Rental.restore(
                entity.getId(),
                entity.getCustomerId(),
                entity.getCarId(),
                new RentalPeriod(entity.getStartDate(), entity.getEndDate()),
                entity.getStatus(),
                new Money(entity.getTotalCostAmount(), entity.getTotalCostCurrency()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public RentalJpaEntity toEntity(Rental rental) {
        if (rental == null) {
            return null;
        }
        var entity = new RentalJpaEntity();
        entity.setId(rental.getId());
        entity.setCustomerId(rental.getCustomerId());
        entity.setCarId(rental.getCarId());
        entity.setStartDate(rental.getRentalPeriod().getStartDate());
        entity.setEndDate(rental.getRentalPeriod().getEndDate());
        entity.setStatus(rental.getStatus());
        entity.setTotalCostAmount(rental.getTotalCost().getAmount());
        entity.setTotalCostCurrency(rental.getTotalCost().getCurrencyCode());
        entity.setCreatedAt(rental.getCreatedAt());
        entity.setUpdatedAt(rental.getUpdatedAt());
        return entity;
    }
}
