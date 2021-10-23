package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class JwtUtilTest {

    private static final String SECRET = "123456789012345678901234567890123";

    private JwtUtil jwtUtil;

    @Before
    public void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }


    @Test
    public void createToken() {
        String token = jwtUtil.createToken(1004L, "John", null);
        String i = jwtUtil.createToken(1004L, "Tester", 369L);
        assertThat(token, containsString("."));
    }

    @Test
    public void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0.scvKEe3F2LM4753PXepfRdHulj6eQVVubH2lYg-cMbg";
        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("name"),is("John"));
    }
}