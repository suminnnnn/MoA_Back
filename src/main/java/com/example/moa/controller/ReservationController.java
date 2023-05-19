package com.example.moa.controller;

import com.example.moa.domain.Reservation;
import com.example.moa.dto.reservation.ReservationDetailsDto;
import com.example.moa.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private final ReservationService reservationService;

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmReservation(@RequestBody ReservationDetailsDto reservationDetailsDto){
        reservationService.saveReservation(reservationDetailsDto);
        return ResponseEntity.ok()
                .body(new ApiResponse("예약이 완료되었습니다.", "201"));
    }
}
