package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> create(
            @RequestBody User resource
    ) throws URISyntaxException {
        String email = resource.getEmail();
        String name = resource.getName();
        String password = resource.getPassword();

        // JPA Entity　生成
        // 不要なConstructor x
        // 明示
//         User user = user.builder()
//                .id(1004L)
//                .email(email)
//                .name(name)
//                .password(password)
//                .build();

        User user = userService.registerUser(email, name, password);

        String url = "/users/" + user.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
