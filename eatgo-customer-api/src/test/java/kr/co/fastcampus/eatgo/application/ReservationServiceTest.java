package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
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
        reservationService = new ReservationService();
    }

    @Test
    public void addReservation() {
        Long userId = 1004L;
        String name = "John";
        String date = "2021-12-25";
        String time = "20:20";
        Integer partySize = 20;
        Long restaurantId = 369L;

        Reservation reservation = reservationService.addReservation(restaurantId, userId, name,
                date, time, partySize);

        assertThat(reservation.getName(), is(name));

        verify(reservationRepository).save(any(Reservation.class));
    }
}