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

    private final RecruitRepository recruitRepository;

    private final UserRepository userRepository;

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Recruit saveRecruit(RecruitCreateRequestDto requestDto) {
        User writer = userRepository.findByEmail(requestDto.getWriterEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + requestDto.getWriterEmail()));

        Recruit recruit = requestDto.toEntity(writer);
        recruitRepository.save(recruit);

        String chatRoomId = "recruit-"+recruit.getId();
        writer.getChatRoomId().add(chatRoomId);
        recruit.setChatRoomId(chatRoomId);

       chatRoomRepository.save(
                ChatRoom.builder()
                        .id(chatRoomId)
                        .name(recruit.getTitle())
                        .build()
        );
        return recruit;
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

    @Override
    public List<RecruitCreateResponseDto> findMyList(String email){
        List<Recruit> recruits = recruitRepository.findByWriterEmail(email);
        return recruits.stream()
                .map(RecruitCreateResponseDto::from)
                .collect(Collectors.toList());
    }
}

