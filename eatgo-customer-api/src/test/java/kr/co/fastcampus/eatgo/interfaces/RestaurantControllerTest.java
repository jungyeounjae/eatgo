package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.interfaces.RestaurantController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    public void list() throws Exception {

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
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
                .name("Joker House")
                .address("Seoul")
                .build();
        MenuItem menuItem = MenuItem.builder()
                .name("Kimchi")
                .build();

        restaurant.setMenuItem(Arrays.asList(menuItem));

        Review review = Review.builder()
                .name("JOKER")
                .score(5)
                .description("good taste")
                .build();

        restaurant.setReviews(Arrays.asList(review));

        // given 안의 내용이 실행 되면 restaurant1가 반환 된다
        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        //given()

        // 위에서 입력한 데이터들이 올바르게 입력 되었는지 확인한다.
        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Joker House\"")))
                .andExpect(content().string(containsString("\"address\":\"Seoul\"")))
                .andExpect(content().string(containsString("Kimchi")))
                .andExpect(content().string(containsString("good taste")));

    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(4040L))
                .willThrow(new RestaurantNotFoundException(4040L));

        mvc.perform(get("/restaurants/4040"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }
}