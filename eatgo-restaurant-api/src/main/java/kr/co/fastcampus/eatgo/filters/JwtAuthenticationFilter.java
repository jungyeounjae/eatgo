package kr.co.fastcampus.eatgo.filters;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * BasicAuthenticationFilter은 http요청이 발생할 때마다 인증을 시도한다
 * 이 요청은 헤더를 이용한 인증 방식이다!
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {

        //　http通信が行われると、このdoFilterInternal()がそれをキャッチして認証処理を実施する！
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        if (authentication != null) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication); // SecurityContextHolder 에는 사용자 정보가 담겨져 있다.
        }

        chain.doFilter(request, response);
    }

    /**
     * 스프링 프로젝트에서 사용하는 authentication
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return null;
        }

        // jwt token 의 userID 와 name 을 취득한다.
        // 만약, 토큰이 사용자의 토큰과 다를 경우 에러를 일으킨다.
        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length())); // Bearer auth 방식

        // authentication은 principal(아이디) credentials(비밀번호)를 리턴 받는다
        // 여기서는 principal을 claims로서 넣고 있음.
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(claims, null);

        return authentication;
    }
}
