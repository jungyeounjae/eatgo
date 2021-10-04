package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

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
     * 間違ったパスワードcheck
     */
    @Test(expected = PasswordWrongException.class)
    public void authenticateWithWrongPassword() {
        String email = "duswp220@gmail.com";
        String password = "x";

        User user = userService.authenticate(email, password);

        given(userRepository.findByEmail(email)).
                willReturn(Optional.empty());

        given(passwordEncoder.matches(any(), any())).willReturn(false);

        userService.authenticate(email, password);
    }
}