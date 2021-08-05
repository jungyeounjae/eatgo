package kr.co.fastcampus.eatgo.interfaces;

import jdk.javadoc.internal.doclets.toolkit.taglets.UserTaglet;
import kr.co.fastcampus.eatgo.application.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    public void create() throws Exception {
        mvc.perform(post("/session")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\":\"duswp220@gmail.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session/1004"))
                .andExpect(content().string("{\"accessToken\":\"ACCESSTOKEN\"}"));

    }
}