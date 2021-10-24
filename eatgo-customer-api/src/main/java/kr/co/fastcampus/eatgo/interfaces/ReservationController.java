package kr.co.fastcampus.eatgo.interfaces;

import java.net.URI;

import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity<?> create(
            @PathVariable Long restaurantId,
            @RequestBody Reservation resource
    ) throws URISyntaxException {
        Long userId = 1L;
        String name = "Tester";
        String date = resource.getDate();
        String time = resource.getTime();
        Integer partySize = resource.getPartySize();

        reservationService.addReservation(restaurantId, userId, name,
                date, time, partySize);

        String url = "/restaurants/"+ restaurantId + "/reservations/1";
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
