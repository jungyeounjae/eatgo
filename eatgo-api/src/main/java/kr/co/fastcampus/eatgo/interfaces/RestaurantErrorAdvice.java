package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice // 전역
public class RestaurantErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException.class) // @Controller Bean내에서 발생하는 예외를 처리해주는 역활. 인자로서 캐치하고 싶은 예외 클래스를 지정
    public String handleNotFound() {
        // RestaurantNotFoundException 이 발생할 때 쓰는 에러 핸들러
        return "{}";
    }

}
