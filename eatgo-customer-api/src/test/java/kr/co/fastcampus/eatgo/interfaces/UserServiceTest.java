package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void registerUser() {
        String email = "duswp220@gmail.com";
        String name = "yeounjae";
        String password = "test";

        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

}