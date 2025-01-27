package com.ferro.mateus.ticketflowgrpcserver.service;

import com.ferro.mateus.ticketflowgrpcserver.domain.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation create(Reservation reservation);
    Reservation findById(Long id) throws Exception;
    List<Reservation> findByCustomerId(Long customerId);
}
