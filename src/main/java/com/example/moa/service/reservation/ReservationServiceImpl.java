package com.example.moa.service.reservation;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.Reservation;
import com.example.moa.domain.User;
import com.example.moa.dto.reservation.ReservationDetailsDto;
import com.example.moa.exception.NotFindException;
import com.example.moa.exception.UserNoIngredientException;
import com.example.moa.repository.RecruitRepository;
import com.example.moa.repository.ReservationRepository;
import com.example.moa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final RecruitRepository recruitRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public void saveReservation(ReservationDetailsDto reservationDetailsDto) {
        String chatroomid = reservationDetailsDto.getChatRoomId();

        //채팅방 id로 recruit 찾기
        Recruit recruit = recruitRepository.findByChatRoomId(chatroomid)
                .orElseThrow(() -> new NotFindException(chatroomid + " recruit not found"));

        //예약 정보 객체 저장
        Reservation saved_reservation = Reservation.builder()
                .recruit(recruit)
                .reservationDate(reservationDetailsDto.getReservationDate())
                .reservationTime(reservationDetailsDto.getReservationTime())
                .reservationLocation(reservationDetailsDto.getReservationLocation())
                //.users(recruit.getUsers())
                .build();

        //예약 테이블에 저장
        reservationRepository.save(saved_reservation);

        //해당 예약의 모집글에 포함되어있는 사용자 각각의 예약 내역에 예약 추가
        for (User user : recruit.getUsers()) {
            user.getReservations().add(saved_reservation);
        }
    }
}