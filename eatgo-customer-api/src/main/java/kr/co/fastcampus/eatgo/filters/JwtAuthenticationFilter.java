package kr.co.fastcampus.eatgo.filters;

import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
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

        Authentication authentication = getAuthentication(request);

        if (authentication != null) {
            SecurityContextHolder.getContext();
        }
        chain.doFilter(request, response);
    }

    /**
     * 스프링 프로젝트에서 사용하는 authentication
     */
    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return null;
        }
    }
}
