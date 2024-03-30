package team.haedal.gifticionfunding.core.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import team.haedal.gifticionfunding.core.jwt.JwtProvider;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtAuthenticationFilter");
        String rawToken;
        /**
         * Authorization 헤더에서 토큰을 추출한다.
         * 토큰이 없거나 올바르지 filterChain을 통해 다음 필터로 넘긴다.
         */
        try{
            rawToken = jwtProvider.parseHeader(request.getHeader("Authorization"),true);
        }catch (Exception e){
            filterChain.doFilter(request, response);
            return;
        }

        /**
         * 토큰이 존재하다면 토큰을 검증하고 Authentication 객체를 SecurityContext에 저장한다.
         */
        if (jwtProvider.validateToken(rawToken)) {
            Authentication authentication = jwtProvider.getAuthentication(rawToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}