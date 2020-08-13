package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

// CrudRepository jap 연동
public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {
    List<MenuItem> findAllByRestaurantId(Long restaurantId);
}
