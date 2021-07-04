package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .email("duswp220@gmail.com")
                .name("tester")
                .level(1L)
                .build());

        return users;
    }
}
