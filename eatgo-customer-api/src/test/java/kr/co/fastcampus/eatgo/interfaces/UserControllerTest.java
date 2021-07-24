package kr.co.fastcampus.eatgo.interfaces;

import jdk.javadoc.internal.doclets.toolkit.taglets.UserTaglet;
import kr.co.fastcampus.eatgo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserControllerTest {

    @Autowired //自動Bean Object
    MockMvc mvc; // HTTP Servlet Request function

    @MockBean
    private UserService userService;

    @Test
    public void create() throws Exception {
        String email = "duswp220@gmail.com";
        String name = "yeounjae";
        String password = "test";

        // build object 生成
        // 直観的
        User mockUser = User.builder()
                .id(1004L)
                .email(email)
                .name(name)
                .password(password)
                .build();

        given(userService.registerUser("duswp220@gmail.com","yeounjae","test"))
                            .willReturn(mockUser);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"duswp220@gmail.com\",\"name\":\"yeounjae\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/users/1004"));

        verify(userService).registerUser(eq("duswp220@gmail.com"),eq("yeounjae"),eq("test"));
    }
}