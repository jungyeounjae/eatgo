package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest(

)

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock // Spring Boot Container 의 Bean 이라면 MockBean, 그 이 외에는 Mock을 사
    private RestaurantRepository restaurantRepository;

    @Mock // 가짜 객체 생성, 테스트 대상의 객체가 아니이기 때문
    private MenuItemRepository menuItemRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @Before // 테스트가 실행되기 전에 호출 되는 함
    public void setUp() {
        MockitoAnnotations.initMocks(this); // @Mock 객체의 초기화 수행

        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();

        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository, reviewRepository);
    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(MenuItem.builder()
                .name("Sushi")
                .build());

        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob Zip")
                .address("Seoul")
                .build();

        restaurants.add(restaurant);
        // 정의!
        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L))
                .willReturn(Optional.of(restaurant));
    }

    private void mockReviewRepository() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder()
                .name("BeRyong")
                .score(1)
                .description("Bad")
                .build());

        given(reviewRepository.findAllByRestaurantId(1004L)).willReturn(reviews);
    }

    @Test
    public void getRestaurantWithNotExisted() {

        Restaurant restaurant = restaurantService.getRestaurant(404L);
    }

    @Test
    public void getRestaurantWithExisted() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(eq(1004L)); // 위의 코드에서 왼쪽과 같은 실행을 기대한다!
        verify(reviewRepository).findAllByRestaurantId(eq(1004L));

        assertThat(restaurant.getId(), is(1004L));

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertThat(menuItem.getName(), is("Sushi"));

        Review review = restaurant.getReviews().get(0);
        assertThat(review.getDescription(), is("Bad"));

    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();


        Restaurant restaurant = restaurants.get(0);

        assertThat(restaurant.getId(), is(1004L));
    }



    @Test
    public void addRestaurant() {

        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder()
                .name("Beryoung")
                .address("Busan")
                .build();

        Restaurant saved = Restaurant.builder()
                .id(1004L)
                .name("Bob Zip")
                .address("Seoul")
                .build();

        Restaurant created = restaurantService.addRestaurant(restaurant);


        assertThat(created.getId(), is(1234L));

    }

    @Test
    public void updateRestaurant() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob Zip")
                .address("Seoul")
                .build();

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "Bob zip","Busan");

        assertThat(restaurant.getAddress(), is("Busan"));

    }
}