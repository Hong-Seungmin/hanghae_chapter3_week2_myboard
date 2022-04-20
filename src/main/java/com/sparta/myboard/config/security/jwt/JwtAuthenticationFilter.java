package com.sparta.myboard.config.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("request = " + request);

        String jwt = JwtTokenProvider.getJwtFromRequest(request); //request에서 jwt 토큰을 꺼낸다.
        try {
            log.info("1jwt = " + jwt);
            if (StringUtils.hasLength(jwt) && JwtTokenProvider.validateToken(jwt)) {
                String userId = JwtTokenProvider.getUsernameFromJWT(jwt); //jwt에서 사용자 id를 꺼낸다.

                Authentication authentication = new UsernamePasswordAuthenticationToken(userId, "", null); //id를 인증한다.
                System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
                SecurityContextHolder.getContext()
                                     .setAuthentication(
                                             authentication); //세션에서 계속 사용하기 위해 securityContext에 Authentication 등록

                response.setHeader(JwtTokenProvider.JWT_HEADER_KEY_NAME, jwt);
                log.info("2jwt = " + jwt);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        log.info("3jwt = " + jwt);
        filterChain.doFilter(request, response);
    }
}