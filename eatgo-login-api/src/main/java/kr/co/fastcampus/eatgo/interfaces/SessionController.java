package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.interfaces.SessionDto;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/session")
    public ResponseEntity<SessionDto> create(
            @RequestBody SessionRequestDto resource
    ) throws URISyntaxException {
        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = userService.authenticate(email, password);

        // 토큰에 넣을 정보
        String accessToken = jwtUtil.createToken(
                user.getId(),
                user.getName(),
                user.isRestaurantOwner() ? user.getRestaurantId() : null);

        SessionDto sessionResponseDto = SessionDto.builder()
                .accessToken(accessToken)
                .build();

        String url = "/session";
        return ResponseEntity.created(new URI(url)).body(
                sessionResponseDto.builder()
                .accessToken(accessToken)
                .build());
    }
}
