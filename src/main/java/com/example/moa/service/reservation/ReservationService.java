package com.example.moa.service.reservation;

import com.example.moa.dto.reservation.AvailableScheduleDto;
import com.example.moa.dto.reservation.ReservationDetailsDto;

import java.util.List;

public interface ReservationService {
    void saveReservation(ReservationDetailsDto reservationDetailsDto);
    List<String> findAllKitchen();
    List<AvailableScheduleDto> findAvailableDateTime(String place);
    String getReservationUrl(String place);
}
