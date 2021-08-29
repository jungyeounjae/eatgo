package kr.co.fastcampus.eatgo.utils;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    public String createToken(long id, String name) {
        return "header.payload.signature";
    }
}
