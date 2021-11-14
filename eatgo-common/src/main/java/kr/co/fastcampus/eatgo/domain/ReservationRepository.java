package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    Reservation save(Reservation reservation);

    List<Reservation> findAllByRestaurantId(Long restaurantId);

    Optional<Reservation> findAllByRestaurantIdAndUserId(
            long restaurantId, long userId);

    void deleteByUserId(long userId);
}
