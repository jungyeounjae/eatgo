package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.EmailNotExistedException;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.application.PasswordWrongException;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Test
    public void createWithNotExistedEmail() throws Exception {
        given(userService.authenticate("duswp220@gmail.com","test"))
                .willThrow(EmailNotExistedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"duswp220@gmail.com\",\"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("duswp220@gmail.com"), eq("test"));
    }

    @Test
    public void createWithValidAttributes() throws Exception {
        Long id = 1004L;
        String name = "Tester";

        User mockUser = User.builder().id(id).name(name).level(1L).build();

        given(userService.authenticate("duswp220@gmail.com","test")).willReturn(mockUser);

        given(jwtUtil.createToken(id, name, null)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"duswp220@gmail.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
                .andExpect(content().string(
                        containsString("{\"accessToken\":\"header.payload.signature")
                ));

        verify(userService).authenticate(eq("duswp220@gmail.com"), eq("test"));
    }

    @Test
    public void createRestaurantOwner() throws Exception {
        Long id = 1004L;
        String name = "Tester";

        User mockUser = User.builder()
                .id(id)
                .name(name)
                .level(50L)
                .restaurantId(369L)
                .build();

        given(userService.authenticate("duswp220@gmail.com","test")).willReturn(mockUser);

        given(jwtUtil.createToken(id, name, 369L)).willReturn("header.payload.signature");
        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"duswp220@gmail.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
                .andExpect(content().string(
                        containsString("{\"accessToken\":\"header.payload.signature")
                ));

        verify(userService).authenticate(eq("duswp220@gmail.com"), eq("test"));
    }

    @Test
    public void createWithWrongPassword() throws Exception {
        given(userService.authenticate("duswp220@gmail.com","x"))
                .willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"duswp220@gmail.com\",\"password\":\"x\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("duswp220@gmail.com"), eq("x"));

    }
}