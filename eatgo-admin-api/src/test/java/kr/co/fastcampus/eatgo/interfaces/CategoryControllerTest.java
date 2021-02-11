package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.CategoryService;
import kr.co.fastcampus.eatgo.domain.Category;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired //Dependency Injection
    private MockMvc mvc;

    @MockBean
    CategoryService CategoryService;

    @Test
    public void list() throws Exception {
        List<Category> Categories = new ArrayList<>();
        Categories.add((Category.builder().name("Korean Food").build()));

        // It is not related to Real Source!!
        given(CategoryService.getCategories()).willReturn(Categories);

        mvc.perform(get("/Categories"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Seoul")));
    }

    @Test
    public void create() throws Exception{
        Category category = Category.builder().name("Korean Food").build();

        given(CategoryService.addCategory("Korean Food")).willReturn(category);

        mvc.perform(post("/Categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Korean Food\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("{1}"));

        // do verify
        // call addCategory(Param)
        // Param = Korean Food
        verify(CategoryService).addCategory("Korean Food");
    }


}