package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // mock initialize

        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void registerUser() {
        String email = "duswp220@gmail.com";
        String name = "yeounjae";
        String password = "test";

        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

    @Test(expected= EmailExistedException.class)
    public void registerUserWithExistedEmail() {
        String email = "duswp220@gmail.com";
        String name = "yeounjae";
        String password = "test";

        User mockUser = User.builder().build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        userService.registerUser(email, name, password);

        verify(userRepository, never()).save(any()); // Authenticate that the function has never been executed
    }

    @Test
    public void authenticateWithValidAttributes() {
        String email = "duswp220@gmail.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email).build();

        given(userRepository.findByEmail(email)).
                willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);

        assertThat(user.getEmail(), is(email));
    }

    /**
     * 存在しないe-mailチェック
     */
    @Test(expected = EmailNotExistedException.class)
    public void authenticateWithNotExistedEmail() {
        String email = "x@gmail.com";
        String password = "test";

        given(userRepository.findByEmail(email)).
                willReturn(Optional.empty());

        userService.authenticate(email, password);
    }

    /**
     * 間違ったパスワードcheck
     */
    @Test(expected = PasswordWrongException.class)
    public void authenticateWithWrongPassword() {
        String email = "duswp220@gmail.com";
        String password = "x";

        User user = userService.authenticate(email, password);

        given(userRepository.findByEmail(email)).
                willReturn(Optional.empty());

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        userService.authenticate(email, password);
    }
}