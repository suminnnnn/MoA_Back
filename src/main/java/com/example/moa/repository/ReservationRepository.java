package com.example.moa.repository;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.Reservation;
import com.example.moa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByRecruit(Recruit recruit);
    List<User> findUsersByRecruitId(Long recruitId);

}
