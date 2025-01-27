package com.ferro.mateus.ticketflowgrpcserver.repository;

import com.ferro.mateus.ticketflowgrpcserver.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCustomerId(Long customerId);
}
