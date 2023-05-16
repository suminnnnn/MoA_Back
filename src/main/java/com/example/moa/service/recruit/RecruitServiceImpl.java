package com.example.moa.service.recruit;

import com.example.moa.domain.*;
import com.example.moa.dto.recruit.RecruitModifyDto;
import com.example.moa.dto.recruit.RecruitCreateRequestDto;
import com.example.moa.dto.recruit.RecruitCreateResponseDto;
import com.example.moa.exception.NotFindException;
import com.example.moa.repository.ChatRoomRepository;
import com.example.moa.repository.RecruitRepository;
import com.example.moa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitServiceImpl implements RecruitService {
    @Autowired
    private final RecruitRepository recruitRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Recruit saveRecruit(RecruitCreateRequestDto requestDto) {
        User writer = userRepository.findByEmail(requestDto.getWriterEmail())
                .orElse(null);

        Recruit recruit = requestDto.toEntity(writer);

        recruit.setChatRoomId("recruit-"+recruit.getId());
        chatRoomRepository.save(
                ChatRoom.builder()
                        .id(recruit.getChatRoomId())
                        .build()
        );
        return recruitRepository.save(recruit);
    }


    @Override
    public Recruit update(RecruitModifyDto recruitModifyDto){
        Long id = recruitModifyDto.getRecruitId();
        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(()->new NotFindException(id + " recruit not found"));

        return recruit.update(recruitModifyDto);
    }

    @Override
    public void delete(Long id){
        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(()->new NotFindException(id + " recruit not found"));
        recruitRepository.delete(recruit);
    }

    @Override
    public List<RecruitCreateResponseDto> findAllDesc() {
        return recruitRepository.findAll()
                .stream()
                .map(RecruitCreateResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public RecruitCreateResponseDto showRecruit(Long id) {
        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(()->new NotFindException(id + " recruit not found"));
        return RecruitCreateResponseDto.from(recruit);
    }

    @Override
    public String getChatRoomId(Long id){
        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(()->new NotFindException(id + " recruit not found"));
        return recruit.getChatRoomId();
    }

}

