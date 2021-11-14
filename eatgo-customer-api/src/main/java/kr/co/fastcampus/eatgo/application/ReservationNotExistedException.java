package kr.co.fastcampus.eatgo.application;

public class ReservationNotExistedException extends RuntimeException {

    public ReservationNotExistedException(Long restaurantId, Long userId) {
        super(userId + "の" + restaurantId + "に対する予約情報は存在しません。");
    }
}
