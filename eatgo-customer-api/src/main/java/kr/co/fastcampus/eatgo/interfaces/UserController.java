package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UserController {

    @PostMapping("/users")
    public ResponseEntity<?> create() throws URISyntaxException {
        String email = "duswp220@gmail.com";
        String name = "yeounjae";
        String password = "test";

        // JPA Entity　生成
        // 不要なConstructor x
        // 明示
        User user = User.builder()
                .id(1004L)
                .email(email)
                .name(name)
                .password(password)
                .build();

        String url = "/users/" + user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
