package com.example.moa.controller;

import com.example.moa.domain.Reservation;
import com.example.moa.dto.reservation.AvailableScheduleDto;
import com.example.moa.dto.reservation.ReservationDetailsDto;
import com.example.moa.service.reservation.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/list")
    public ResponseEntity<?> showListOfKitchen() {
        List<String> list = reservationService.findAllKitchen();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/status/{place}")
    public ResponseEntity<List<AvailableScheduleDto>> showStatusOfKitchen(@PathVariable String place) {
        List<AvailableScheduleDto> lists = reservationService.findAvailableDateTime(place);

        return ResponseEntity.ok().body(lists);
    }

    @GetMapping("/{place}")
    public ResponseEntity<String> showReservationLink(@PathVariable String place){
        String reservationUrl = reservationService.getReservationUrl(place);

        return ResponseEntity.ok().body(reservationUrl);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmReservation(@RequestBody ReservationDetailsDto reservationDetailsDto){
        reservationService.saveReservation(reservationDetailsDto);
        return ResponseEntity.ok()
                .body(new ApiResponse("예약이 완료되었습니다.", "201"));
    }
}
