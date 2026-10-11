package com.carrent.boundedcontext.rental.application.query.handler;

import com.carrent.boundedcontext.rental.application.dto.RentalDTO;
import com.carrent.boundedcontext.rental.application.mapper.RentalMapper;
import com.carrent.boundedcontext.rental.application.query.GetRentalQuery;
import com.carrent.boundedcontext.rental.domain.repository.RentalRepository;
import org.springframework.stereotype.Service;

@Service
public class GetRentalQueryHandler {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    public GetRentalQueryHandler(RentalRepository rentalRepository, RentalMapper rentalMapper) {
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
    }

    public RentalDTO handle(GetRentalQuery query) {
        return rentalRepository.findById(query.getRentalId())
                .map(rentalMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Rental not found: " + query.getRentalId()));
    }
}
