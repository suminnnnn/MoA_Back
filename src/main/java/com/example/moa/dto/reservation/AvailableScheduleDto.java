package com.example.moa.dto.reservation;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AvailableScheduleDto {
    private LocalDate reservationDate;
    private LocalTime reservationTime;
}
