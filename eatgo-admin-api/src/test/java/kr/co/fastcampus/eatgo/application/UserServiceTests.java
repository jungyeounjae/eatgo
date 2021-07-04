package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.CategoryRepository;
import kr.co.fastcampus.eatgo.domain.User;
import net.bytebuddy.build.ToStringPlugin;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

public class UserServiceTests {
    
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void getUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .email("duswp220@gmail.com")
                .name("tester")
                .level(1L)
                .build());

        given(userRepository.findAll()).willReturn(mockUsers);
        
        List<User> users = userService.getUsers();

        User user = users.get(0);
        
        assertThat(user.getName(), is("tester"));
    }
}