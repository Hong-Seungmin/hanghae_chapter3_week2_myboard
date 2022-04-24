package com.sparta.myboard.config.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

//        String jwt = JwtTokenProvider.getJwtFromRequest(request); //request의 헤더에서 jwt를 꺼낸다.
        String jwt = JwtTokenProvider.getJwtFromCookiesInRequest(request); // request의 쿠키에서 jwt를 꺼낸다.

        // 로그보는 곳
        String id = JwtTokenProvider.getUsernameFromJWT(jwt);
        log.info("=====Conntected IpAddress = {}", request.getHeader("X-FORWARDED-FOR"));
        log.info("=====Conntected IpAddress = {}", request.getRemoteAddr());
        log.info("=====Request URL = {} {} {}",request.getMethod(), request.getRequestURI(), request.getQueryString());
        log.info("=====JWT = {}", jwt);
        log.info("=====username = {}", id);
        ///////

        try {
            if (StringUtils.hasLength(jwt) && JwtTokenProvider.validateToken(jwt)) {
                String username = JwtTokenProvider.getUsernameFromJWT(jwt); //jwt에서 사용자 id를 꺼낸다.

                Authentication authentication = jwtTokenProvider.getAuthenticationFromUsername(username);
//                Authentication authentication = new UsernamePasswordAuthenticationToken(username, "", null); //id를 인증한다.

                log.info("authentication = " + authentication.getCredentials());
                //세션에서 계속 사용하기 위해 securityContext에 Authentication 등록
                SecurityContextHolder.getContext().setAuthentication(authentication);

                response.setHeader(JwtTokenProvider.JWT_HEADER_KEY_NAME, jwt);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }
}