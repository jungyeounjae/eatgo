package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findAll();

    Optional<Restaurant> findById(Long id); // Null 을 처리 하지 않고 객체가 존재의 유무를 체크

    List<Restaurant> findAllByAddressContainingByCategoryId(
            String region, long categoryId);

    List<Restaurant> findAllByAddressContaining(String region);

    Restaurant save(Restaurant restaurant);

}
