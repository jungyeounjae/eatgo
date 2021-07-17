package kr.co.fastcampus.eatgo.interfaces;

import com.sun.tools.javac.comp.Resolve;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> list() {
        List<User> user = userService.getUsers();

        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(
            @RequestBody User resource
    ) throws URISyntaxException {
        String email = resource.getEmail();
        String name = resource.getName();

        User user = userService.addUser(email, name);

        String url = "/users/" + user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PatchMapping("/users/{userId}")
    public String update(
            @PathVariable("userId") Long userId,
            @RequestBody User resource
    ) {
        String email = resource.getEmail();
        String name = resource.getName();
        Long level = resource.getLevel();

        userService.updateUser(userId, email, name, level);
        return "{}";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deactivate(id);

        return "{}";
    }
}
