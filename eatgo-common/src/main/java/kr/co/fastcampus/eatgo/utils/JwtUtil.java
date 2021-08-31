package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;

public class JwtUtil {
    public String createToken(long userId, String name) {
        String secret = "123456789012345678901234567890123";
        Key key = Keys.hmacShaKeyFor(secret.getBytes()); //signのキーは256以上である必要がある

        String token = Jwts.builder()
                .claim("userId",1004L) // claim : 実際に使うデータ、payloadに入っているデータ
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.HS256) //データが偽造されていないのを証明する
                .compact();

        return token;
    }
}
