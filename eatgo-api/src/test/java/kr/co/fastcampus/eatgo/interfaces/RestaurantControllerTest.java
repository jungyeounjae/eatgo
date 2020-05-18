package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) // 스프링을 이용해서 테스트를 진행
@WebMvcTest(RestaurantController.class) //해당 컨트롤러를 테스트 대상으로 한다
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean  // 가짜 객체 생성, 테스트 대상인 컨트롤러는 서비스를 단지 활용만 할 뿐 서비스가 실제로 어떤 동작을 하는 지는 관심을 가지지 않는다.
    private RestaurantService restaurantService;

//    @SpyBean(RestaurantRepositoryImpl.class)  // 컨트롤러에 원하는 객체를 주입 가능
//    private RestaurantRepository restaurantRepository;
//
//    @SpyBean(MenuItemRepositoryImpl.class)  // 컨트롤러에 원하는 객체를 주입 가능
//    private MenuItemRepository menuItemRepository;


    @Test
    public void list() throws Exception {

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1004L,"Bob zip", "Seoul"));

        // given안의 함수가 호출이 된다면, willReturn()안의 갹체가 리턴이 된다.
        // given안의 할수를 실제로 호출 한다는 의미가 아님!
        // 따라서, 실제 함수를 호출하지 않기 때문에 willReturn()안의 객체는 가짜 객체!
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("\"id\":1004")))
            .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
            .andExpect(content().string(containsString("\"address\":\"Seoul\"")));
    }

    @Test
    public void detail() throws Exception {
        Restaurant restaurant1 = new Restaurant(1004L,"Bob zip", "Seoul");
        Restaurant restaurant2 = new Restaurant(2020L,"Cyber food", "Seoul");
        restaurant1.addMenuItem(new MenuItem("Kimchi"));
        restaurant2.addMenuItem(new MenuItem("Cyber Food"));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);
        given(restaurantService.getRestaurant(2020L)).willReturn(restaurant2);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
                .andExpect(content().string(containsString("\"address\":\"Seoul\"")))
                .andExpect(content().string(containsString("Kimchi")));

        mvc.perform(get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":2020")))
                .andExpect(content().string(containsString("\"name\":\"Cyber Food\"")));
    }

    @Test
    public void create() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON) // JSON type
                .content("{\"name\":\"beryong\",\"address\":\"busan\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());

    }

}