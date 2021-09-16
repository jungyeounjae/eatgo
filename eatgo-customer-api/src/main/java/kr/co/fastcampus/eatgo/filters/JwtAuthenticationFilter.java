package kr.co.fastcampus.eatgo.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;

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

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        if (authentication != null) {
            SecurityContextHolder.getContext().getAuthentication(); // SecurityContextHolder 에는 사용자 정보가 담겨져 있다.
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

        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length())); // Bearer auth 방

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(claims, null);

        return authentication;
    }
}
