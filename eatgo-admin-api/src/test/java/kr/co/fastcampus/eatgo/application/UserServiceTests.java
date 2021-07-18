package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserServiceTests {
    
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
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

    @Test
    public void updateUser() {
        String email = "duswp220@gmail.com";
        String name = "SubAdministrator";
        Long id = 1004L;
        Long level = 100L;

        User mockUsers = User.builder()
                .email(email)
                .name("Administrator")
                .level(1L)
                .build();

        // userRepository.findByIdが呼び出されるとwillReturnが返却される。
        given(userRepository.findById(id)).willReturn(Optional.of(mockUsers));
        // updateUser()でfindById()が呼出される。
        User user = userService.updateUser(id, email, name, level);

        verify(userRepository).findById(eq(id));

        assertThat(user.getName(), is("SubAdministrator"));
        assertThat(user.isAdmin(), is(true));
    }
    @Test
    public void addUser() {
        String email = "duswp220@gmail.com";
        String name = "Administrator";

        User mockUser = User.builder()
                .email(email)
                .name(name)
                .build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(email, name);

        assertThat(user.getName(), is(name));
    }

    @Test
    public void deactiveUser() {
        String email = "duswp220@gmail.com";
        String name = "Administrator";
        Long id = 1004L;
        Long level = 100L;

        User mockUsers = User.builder()
                .id(id)
                .email(email)
                .name(name)
                .level(level)
                .build();

        // userRepository.findByIdが呼び出されるとwillReturnが返却される想定。
        given(userRepository.findById(id)).willReturn(Optional.of(mockUsers));

        User user = userService.deactivate(1004L);

        verify(userRepository).findById(1004L);

        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActive(), is(false));
    }
}