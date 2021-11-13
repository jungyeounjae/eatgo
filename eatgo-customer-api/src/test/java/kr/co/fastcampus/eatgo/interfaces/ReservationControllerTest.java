package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void create() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0.scvKEe3F2LM4753PXepfRdHulj6eQVVubH2lYg-cMbg";

        Reservation mockReservation = Reservation.builder().id(12L).build();

        given(reservationService.addReservation(any(),any(),any(),any(),any(),any()))
                .willReturn(mockReservation);

        Long userId = 1004L;
        String name = "John";
        String date = "2021-12-25";
        String time = "20:20";
        Integer partySize = 20;

        mvc.perform(post("/restaurants/369/reservations")
                .header("Authorization","Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\":\"2021-12-25\", " +
                        "\"time\": \"20:20\", \"partySize\":20}"))
                .andExpect(status().isCreated());

        verify(reservationService).addReservation(eq(369L), eq(userId), eq(name),
                eq(date), eq(time), eq(partySize));
    }

    @Test
    public void deletereservation() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0.scvKEe3F2LM4753PXepfRdHulj6eQVVubH2lYg-cMbg";

        mvc.perform(delete("/restaurants/369")
                .header("Authorization","Bearer " + token))
                .andExpect(status().isOk());
    }

}