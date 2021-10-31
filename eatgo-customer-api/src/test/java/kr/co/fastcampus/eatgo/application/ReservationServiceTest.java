package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ReservationServiceTest {

    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // reservationService는 빈 객체가 아니기 때문에 테스트
        // 실시 전에 객체를 만들어 주어야 한다!
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void addReservation() {
        Long userId = 1004L;
        String name = "John";
        String date = "2021-12-25";
        String time = "20:20";
        Integer partySize = 20;
        Long restaurantId = 369L;

        // reservationRepository의 save함수가 호출되면 will의 값이 리턴 될 것이다.
        // will은 동적인 값을 확
        given(reservationRepository.save(any()))
                .will(invocation -> {
                    Reservation reservation = invocation.getArgument(0);
                    return reservation;
                });

        // willReturn()은 정해진 값을 확인
        Reservation mockReservation = Reservation.builder().name(name).build();
        given(reservationRepository.save(any()))
                .willReturn(mockReservation);

        Reservation reservation = reservationService.addReservation(restaurantId, userId, name,
                date, time, partySize);

        assertThat(reservation.getName(), is(name));

        verify(reservationRepository).save(any(Reservation.class));
    }
}