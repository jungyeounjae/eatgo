package kr.co.fastcampus.eatgo.interfaces;

import java.net.URI;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity<?> create(
            Authentication authentication,
            @PathVariable Long restaurantId,
            @Valid @RequestBody Reservation resource
    ) throws URISyntaxException {
        // 사용자 정보를 JwtAuthenticationFilter.SecurityContextHolder에 저장하였기 때문에 authentication을 이용해 사용자 정보를 취득
        Claims claims = (Claims) authentication.getPrincipal();

        String name = claims.get("name", String.class);
        Long userId = claims.get("userId", Long.class);
        String date = resource.getDate();
        String time = resource.getTime();
        Integer partySize = resource.getPartySize();

        Reservation reservation = reservationService.addReservation(restaurantId, userId, name,
                date, time, partySize);

        String url = "/restaurants/"+ restaurantId + "/reservations/" + reservation.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @DeleteMapping("/restaurants/{restaurantId}/reservations")
    public String update(
            Authentication authentication,
            @PathVariable("restaurantId") Long restaurantId
    ) {
        // 사용자 정보를 JwtAuthenticationFilter.SecurityContextHolder 에 저장하였기 때문에 authentication을 이용해 사용자 정보를 취득
        // 인증에 성공하면 사용자의 정보(authentication)는 securitycontext에 담기기 때문에 securitycontext를 사용해 사용자 정보 취득이 가능.
        Claims claims = (Claims) authentication.getPrincipal();

        Long userId = claims.get("userId", Long.class);

        reservationService.deleteReservations(userId, restaurantId);
        return "{}";
    }
}
