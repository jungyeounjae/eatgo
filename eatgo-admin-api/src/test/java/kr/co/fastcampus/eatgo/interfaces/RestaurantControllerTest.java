package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.*;
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

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("JOKER House")
                .address("Seoul")
                .build());

        // given 안의 함수가 호출이 된다면, willReturn()안의 갹체가 리턴이 된다.
        // given 안의 할수를 실제로 호출 한다는 의미가 아님!
        // 따라서, 실제 함수를 호출 하지 않기 때문에 willReturn()안의 객체는 가짜 객체!
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("\"id\":1004")))
            .andExpect(content().string(containsString("\"name\":\"JOKER House\"")))
            .andExpect(content().string(containsString("\"address\":\"Seoul\"")));
    }

    @Test
    public void detailWithExisted() throws Exception {
        // 데이터 준비를 한다
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Joker House")
                .address("Seoul")
                .build();

        // given 안의 내용이 실행 되면 restaurant1가 반환 된다
        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        // 위에서 입력한 데이터들이 올바르게 입력 되었는지 확인한다.
        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Joker House\"")));
    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(4040L))
                .willThrow(new RestaurantNotFoundException(4040L));

        mvc.perform(get("/restaurants/4040"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

    @Test
    public void createWithValidData() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .categoryId(1L)
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .build();
        });

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON) // JSON type
                .content("{\"categoryId\":1,\"name\":\"beryong\",\"address\":\"busan\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any()); // 해당 객체에 올바른 객체를 넣었 는지 validate

    }

    @Test
    public void createWithInvalidData() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .build();
        });

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON) // JSON type
                .content("{\"categoryId\":1,\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateWithValidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryId\":1,\"name\":\"JOKER Bar\",\"address\":\"Busan\"}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L, "JOKER Bar", "Busan");
        // restaurantService.updateRestaurant(~)가 호출 되었는지 validate check
    }

    @Test
    public void updateWithInvalidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryId\":1,\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());

    }

}