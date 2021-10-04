package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;

public class JwtUtil {

    private Key key;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes()); //signのキーは256byte以上である必要がある
    }

    public String createToken(long userId, String name) {
        // jwt token 생성
        String token = Jwts.builder()
                .claim("userId",userId) // claim : 実際に使うデータ、payloadに入っているデータ
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.HS256) //データが偽造されていないのを証明する
                .compact();

        return token;
    }

    public Claims getClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)  // 인코딩된 header 와 payload 를 합쳐 header 의 서명 알고리즘 정보를 가져와 암호화하여 생성한다
                .parseClaimsJws(token)  // JWT signature와 로컬의 signature가 일치하지 않으면 에러를 일으킨다.
                .getBody();

         return claims;
    }
}
