package kr.co.fastcampus.eatgo.application;

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
