package kr.co.fastcampus.eatgo.utils;

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
        String token = Jwts.builder()
                .claim("userId",1004L) // claim : 実際に使うデータ、payloadに入っているデータ
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.HS256) //データが偽造されていないのを証明する
                .compact();

        return token;
    }
}
