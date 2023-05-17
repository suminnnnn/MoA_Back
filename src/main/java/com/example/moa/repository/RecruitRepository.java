package com.example.moa.repository;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.RecruitUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    Optional<Recruit> findById(Long id);
    List<Recruit> findByWriterEmail(String userEmail);

}