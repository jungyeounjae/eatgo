package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.reviewRepository = reviewRepository;
    }

    public Restaurant getRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));

        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItem(menuItems);

        List<Review> reviewList = reviewRepository.findAllByRestaurantId(id);
        restaurant.setReviews(reviewList);

        return restaurant;
    }

    public List<Restaurant> getRestaurants(String region, long categoryId) {
        List<Restaurant> restaurants
                = restaurantRepository.findAllByAddressContainingAndCategoryId(
                        region, categoryId);

//        ArrayList<Restaurant> rest = new ArrayList();
//        rest.add(Restaurant.builder()
//                .id(1004L)
//                .name("JOKER House")
//                .address("Seoul")
//                .build());

        return restaurants;
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants
                = restaurantRepository.findAll();

        return restaurants;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional // 이 함수에서 일어난 엔티티 데이터 변경들이 함수가 끝나고 난 뒤 적용이 된다.
    public Restaurant updateRestaurant(Long id, String name, String address) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        restaurant.updateInformation(name, address);
        return restaurant;
    }
}
