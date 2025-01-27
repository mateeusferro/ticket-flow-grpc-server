package com.ferro.mateus.ticketflowgrpcserver.service.impl;

import com.ferro.mateus.ticketflowgrpcserver.domain.Reservation;
import com.ferro.mateus.ticketflowgrpcserver.domain.ReservationStatus;
import com.ferro.mateus.ticketflowgrpcserver.repository.ReservationRepository;
import com.ferro.mateus.ticketflowgrpcserver.service.ReservationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation create(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setTotalPrice(BigDecimal.valueOf(150.00));
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) throws Exception {
        return reservationRepository.findById(id).orElseThrow(() -> new Exception("Reservation not found"));
    }

    @Override
    public List<Reservation> findByCustomerId(Long customerId) {
        return reservationRepository.findByCustomerId(customerId);
    }
}
