package com.example.moa.service.reservation;

import com.example.moa.dto.reservation.ReservationDetailsDto;

public interface ReservationService {
    void saveReservation(ReservationDetailsDto reservationDetailsDto);
}
