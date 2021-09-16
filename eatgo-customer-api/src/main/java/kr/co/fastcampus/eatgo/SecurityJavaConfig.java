package kr.co.fastcampus.eatgo;

import kr.co.fastcampus.eatgo.filters.JwtAuthenticationFilter;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}") // application.ymlファイルデータ参照
    private String secret;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter filter = new JwtAuthenticationFilter(
                authenticationManager(), jwtUtil());

        http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()//initial
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션에 대해 관리 하지 않는다. (필터를 사용 한다는 의미)
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(secret);
    }
}
