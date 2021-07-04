package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> list() {
        List<User> user = userService.getUsers();

        return user;
    }
}
