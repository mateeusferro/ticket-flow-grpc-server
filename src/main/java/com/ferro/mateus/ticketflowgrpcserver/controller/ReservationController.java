package com.ferro.mateus.ticketflowgrpcserver.controller;

import com.ferro.mateus.ticketflowgrpcserver.*;
import com.ferro.mateus.ticketflowgrpcserver.domain.Reservation;
import com.ferro.mateus.ticketflowgrpcserver.service.ReservationService;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class ReservationController extends TicketReservationServiceGrpc.TicketReservationServiceImplBase {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void createReservation(CreateReservationRequest request, StreamObserver<CreateReservationResponse> responseObserver) {
        Reservation reservation = Reservation.builder()
                .customerId(request.getCustomerId())
                .eventId(request.getEventId())
                .seatIds(request.getSeatIdsList())
                .build();
        Reservation savedReservation = reservationService.create(reservation);
        CreateReservationResponse reservationResponse = CreateReservationResponse.newBuilder()
                .setReservationId(savedReservation.getId())
                .setStatus(String.valueOf(savedReservation.getStatus()))
                .setTotalPrice(Float.parseFloat(String.valueOf(savedReservation.getTotalPrice())))
                .build();
        responseObserver.onNext(reservationResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getReservation(GetReservationRequest request, StreamObserver<GetReservationResponse> responseObserver) {
        try {
            Long reservationId = request.getReservationId();
            Reservation reservation = reservationService.findById(reservationId);
            GetReservationResponse reservationResponse = GetReservationResponse.newBuilder()
                    .setReservation(com.ferro.mateus.ticketflowgrpcserver.Reservation.newBuilder()
                            .setId(reservation.getId())
                            .setStatus(String.valueOf(reservation.getStatus()))
                            .setTotalPrice(Float.parseFloat(String.valueOf(reservation.getTotalPrice())))
                            .setEventId(reservation.getEventId())
                            .setCustomerId(reservation.getCustomerId())
                            .addAllSeatIds(reservation.getSeatIds())
                            .build())
                    .build();
            responseObserver.onNext(reservationResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getReservationsByCustomer(GetReservationByCustomerRequest request, StreamObserver<GetReservationByCustomerResponse> responseObserver) {
        Long customerId = request.getCustomerId();
        List<Reservation> reservations = reservationService.findByCustomerId(customerId);
        List<com.ferro.mateus.ticketflowgrpcserver.Reservation> reservationResponse = reservations.stream()
                .map(reservation -> com.ferro.mateus.ticketflowgrpcserver.Reservation.newBuilder()
                        .setId(reservation.getId())
                        .setStatus(String.valueOf(reservation.getStatus()))
                        .setTotalPrice(Float.parseFloat(String.valueOf(reservation.getTotalPrice())))
                        .setEventId(reservation.getEventId())
                        .setCustomerId(reservation.getCustomerId())
                        .addAllSeatIds(reservation.getSeatIds())
                        .build())
                .toList();
        GetReservationByCustomerResponse getReservationByCustomerResponse = GetReservationByCustomerResponse.newBuilder()
                .addAllReservation(reservationResponse)
                .build();
        responseObserver.onNext(getReservationByCustomerResponse);
        responseObserver.onCompleted();
    }
}
