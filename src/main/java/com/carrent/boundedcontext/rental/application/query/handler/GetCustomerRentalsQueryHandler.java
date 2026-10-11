package com.carrent.boundedcontext.rental.application.query.handler;

import com.carrent.boundedcontext.rental.application.dto.RentalDTO;
import com.carrent.boundedcontext.rental.application.mapper.RentalMapper;
import com.carrent.boundedcontext.rental.application.query.GetCustomerRentalsQuery;
import com.carrent.boundedcontext.rental.domain.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCustomerRentalsQueryHandler {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    public GetCustomerRentalsQueryHandler(RentalRepository rentalRepository, RentalMapper rentalMapper) {
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
    }

    public List<RentalDTO> handle(GetCustomerRentalsQuery query) {
        return rentalRepository.findByCustomerId(query.getCustomerId())
                .stream()
                .map(rentalMapper::toDTO)
                .collect(Collectors.toList());
    }
}
