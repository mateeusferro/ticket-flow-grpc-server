package com.ferro.mateus.ticketflowgrpcserver.controller;

import com.ferro.mateus.ticketflowgrpcserver.*;
import com.ferro.mateus.ticketflowgrpcserver.service.ReservationService;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class ReservationController extends TicketReservationServiceGrpc.TicketReservationServiceImplBase {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void createReservation(CreateReservationRequest request, StreamObserver<CreateReservationResponse> responseObserver) {
        CreateReservationResponse userResList = CreateReservationResponse.newBuilder()
                .setReservationId(1)
                .setStatus("CREATED")
                .setTotalPrice(100.00f)
                .build();
        responseObserver.onNext(userResList);
        responseObserver.onCompleted();
    }

    @Override
    public void checkSeatAvailability(SeatAvailabilityRequest request, StreamObserver<SeatAvailabilityResponse> responseObserver) {
        super.checkSeatAvailability(request, responseObserver);
    }

    @Override
    public void getReservation(GetReservationRequest request, StreamObserver<GetReservationResponse> responseObserver) {
        super.getReservation(request, responseObserver);
    }
}
