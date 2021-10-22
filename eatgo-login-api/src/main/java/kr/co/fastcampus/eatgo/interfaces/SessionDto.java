package kr.co.fastcampus.eatgo.interfaces;

import lombok.Builder;
import lombok.Data;

/*
 DTO class
 */
@Data
@Builder
public class SessionDto {

    private String accessToken;

}
