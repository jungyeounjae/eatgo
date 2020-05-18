package kr.co.fastcampus.eatgo.domain;

import java.util.List;

public interface MenuItemRepository {
    List<MenuItem> findAllByRestaurantById(Long restaurantId);
}
