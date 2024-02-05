package com.example.moa.service.reservation;

import com.example.moa.domain.Recruit;
import com.example.moa.domain.Reservation;
import com.example.moa.domain.User;
import com.example.moa.dto.reservation.AvailableScheduleDto;
import com.example.moa.dto.reservation.ReservationDetailsDto;
import com.example.moa.exception.NotFindException;
import com.example.moa.exception.PlaceNotFoundException;
import com.example.moa.exception.UserNoIngredientException;
import com.example.moa.repository.RecruitRepository;
import com.example.moa.repository.ReservationRepository;
import com.example.moa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final RecruitRepository recruitRepository;

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

    @Override
    public List<String> findAllKitchen() {
        List<String> kitchens = new ArrayList<>();
        kitchens.add("공유주방&파티룸 꾸메주방");
        kitchens.add("공유주방 꾸치나디밀라노");

        return kitchens;
    }

    @Override
    public List<AvailableScheduleDto> findAvailableDateTime(String place) {
        List<String> urls = new ArrayList<>();

        String start = LocalDate.now().plusDays(1).toString();
        String end = LocalDate.now().plusDays(7).toString();

        if(place.equals("공유주방&파티룸 꾸메주방")){
            urls.add("https://api.booking.naver.com/v3.0/businesses/625190/biz-items/4211772/hourly-schedules?lang=ko&endDateTime="+end+"T00:00:00&startDateTime="+start+"T00:00:00");
        }else if(place.equals("공유주방 꾸치나디밀라노")){
            urls.add("https://api.booking.naver.com/v3.0/businesses/265512/biz-items/3185050/hourly-schedules?lang=ko&endDateTime="+end+"T00:00:00&startDateTime="+start+"T00:00:00");
            urls.add("https://api.booking.naver.com/v3.0/businesses/265512/biz-items/4738135/hourly-schedules?lang=ko&endDateTime="+end+"T00:00:00&startDateTime="+start+"T00:00:00");
            urls.add("https://api.booking.naver.com/v3.0/businesses/265512/biz-items/3185127/hourly-schedules?lang=ko&endDateTime="+end+"T00:00:00&startDateTime="+start+"T00:00:00");
        }else{
            throw new PlaceNotFoundException(place + "not found");
        }

        List<AvailableScheduleDto> availableScheduleDtoList = new ArrayList<>();
        for(String url : urls){
            try {
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                String responseString = EntityUtils.toString(httpEntity);

                JSONArray jsonArray = new JSONArray(responseString);

                for(int i=0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    boolean isUnitBusinessDay = jsonObject.getBoolean("isUnitBusinessDay");
                    boolean isSaleDay = jsonObject.getBoolean("isSaleDay");
                    int unitBookingCount = jsonObject.getInt("unitBookingCount");

                    if (isUnitBusinessDay && isSaleDay && unitBookingCount == 0) {
                        String unitStartTime = jsonObject.getString("unitStartTime");
                        LocalDate date = LocalDate.parse(unitStartTime.split(" ")[0]);
                        LocalTime time = LocalTime.parse(unitStartTime.split(" ")[1]);

                        AvailableScheduleDto availableScheduleDto = AvailableScheduleDto.builder()
                                .reservationDate(date)
                                .reservationTime(time)
                                .build();
                        availableScheduleDtoList.add(availableScheduleDto);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return availableScheduleDtoList;

    }

    @Override
    public String getReservationUrl(String place) throws PlaceNotFoundException{

        return switch (place) {
            case "공유주방&파티룸 꾸메주방" -> "https://m.booking.naver.com/booking/10/bizes/625190";
            case "공유주방 꾸치나디밀라노" -> "https://m.booking.naver.com/booking/10/bizes/265512";
            default -> throw new PlaceNotFoundException(place + "not found");
        };
    }
}