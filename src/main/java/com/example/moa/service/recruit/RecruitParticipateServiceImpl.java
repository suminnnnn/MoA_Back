package com.example.moa.service.recruit;

import com.example.moa.domain.*;
import com.example.moa.dto.ingredient.IngredientResponseDto;
import com.example.moa.dto.recruit.RecruitUserRequestDto;
import com.example.moa.dto.recruit.RecruitUserResponseDto;
import com.example.moa.exception.DuplicateEmailException;
import com.example.moa.exception.NotFindException;
import com.example.moa.exception.UserNoIngredientException;
import com.example.moa.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitParticipateServiceImpl implements RecruitParticipateService{

    @Autowired
    private final RecruitRepository recruitRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RecruitUserRepository recruitUserRepository;
    @Autowired
    private final IngredientRepository ingredientRepository;

    @Autowired
    private final ChatRoomRepository chatRoomRepository;


    @Override
    public void saveRecruitUser(RecruitUserRequestDto recruitUserDto) {

        Recruit recruit = recruitRepository.findById(recruitUserDto.getRecruitId())
                .orElseThrow( () -> new IllegalArgumentException("Invalid recruit Id"));

        User user = userRepository.findByEmail(recruitUserDto.getUserEmail())
                .orElseThrow( () -> new IllegalArgumentException("Invalid user email"));

        List<Ingredient> ingredients = new ArrayList<>();

        participationDuplicate(recruit,user);

        for(Long i : recruitUserDto.getIngredient_id()){
            ingredients.add(ingredientRepository.findById(i)
                    .orElseThrow(()->new UserNoIngredientException("No have ingredient")));
        }

        recruit.getRecruitUsers().add(
                RecruitUser.builder()
                .recruit(recruit)
                .user(user)
                .ingredients(ingredients)
                        .build()
        );
    }

    @Override
    public List<RecruitUserResponseDto> showParticipateList(Long id) {
        //recruit id
        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(()->new NotFindException(id + " recruit not found"));
        List<RecruitUser> recruitUsers = recruitUserRepository.findByRecruit(recruit);

        return recruitUsers.stream()
                .map(RecruitUserResponseDto::from)
                .collect(Collectors.toList());
    }
    @Override
    public void allowRecruitUser(Long id) {
        RecruitUser recruitUser = recruitUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not find recruitUser"));

        Recruit recruit = recruitUser.getRecruit();
        User user = recruitUser.getUser();

        recruit.addUsers(user);

        recruit.getRecruitUsers().remove(recruitUser);
        recruitUserRepository.delete(recruitUser);

        String chatRoomId = recruit.getChatRoomId();
        user.getChatRoomId().add(chatRoomId);

        for(Ingredient ingredient : recruitUser.getIngredients()){
            recruit.getIngredients().add(ingredient);
            Optional<Ingredient> remove_ingredient = ingredientRepository.findByName(ingredient.getName());

            if(remove_ingredient.isPresent()) {
                recruit.getNeedIngredients().remove(remove_ingredient.get().getName());
            }
        }
    }

    @Override
    public void participationDuplicate(Recruit recruit,User user) {
        List<RecruitUser> recruitUsers= recruitUserRepository.findByRecruit(recruit);
        recruitUsers.stream()
                .filter(recruitUser -> recruitUser.getUser().equals(user))
                .findFirst()
                .ifPresent(m -> {
                    throw new DuplicateEmailException("Duplicate participate");
                });
    }

    @Override
    public boolean isMaxPeople(Long id){
        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(()->new NotFindException(id + " recruit not found"));;
        return recruit.getMaxPeople() - 1 > recruit.getUsers().size() ? true : false ;
    }


    @Override
    public List<IngredientResponseDto> getIngredientsByEmail(String email) {
        User user= userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNoIngredientException("No have ingredient"));

        System.out.println("user : "+ user.getIngredients().size());

        for(Ingredient i : user.getIngredients()){
            System.out.println(i.getName());
        }
        return user.getIngredients()
                .stream()
                .map(IngredientResponseDto::from)
                .collect(Collectors.toList());
    }

}
