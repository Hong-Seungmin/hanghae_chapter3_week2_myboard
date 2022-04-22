package com.sparta.myboard.config.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    public static final String JWT_HEADER_KEY_NAME = "Authorization";
    private static final String JWT_SECRET = "secretKey";

    // Jwt 토큰 유효시간
    private static final long JWT_EXPIRATION_MS = 1 * 60 * 1000L;

    private final UserDetailsService userDetailsService;


    // jwt 토큰 생성
    public static String generateToken(String username) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                   .setSubject(username) // 사용자
                   .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
                   .setExpiration(expiryDate) // 만료 시간 세팅
                   .signWith(SignatureAlgorithm.HS256, JWT_SECRET) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                   .compact();
    }

    // Jwt 토큰에서 아이디 추출
    public static String getUsernameFromJWT(String token) {
        if (validateToken(token)) {

            Claims claims = Jwts.parser()
                                .setSigningKey(JWT_SECRET)
                                .parseClaimsJws(token)
                                .getBody();

            return claims.getSubject();
        }
        return null;
    }

    // Jwt 토큰 유효성 검사
    public static boolean validateToken(String token) {
        String msg = "";
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            msg = "Invalid JWT token";
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            msg = "Expired JWT token";
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            msg = "Unsupported JWT token";
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            msg = "JWT claims string is empty.";
        } catch (SignatureException exception) {
            log.error("JWT validity cannot be asserted and should not be trusted.");
            msg = "JWT validity cannot be asserted and should not be trusted.";
        } catch (Exception e) {
            log.error(e.getMessage());
            msg = "JWT error";
        }

        // 이걸 던지면 프론트단에서 처리를 해줘야한다.
        // 이걸 안던지면.. 백단에서 처리를 해야한다.
        // 내생각에는 이걸 던져서 프론트에서 방향을 유도하는게 맞다고 생각한다. (예, 예외를 먹으면. 토큰삭제, 메세지 출력 후 홈으로 이동한다던가?)
        // 현재는 팀원과 통일을 위해 안던지기로 하였다.
//        throw new JwtException("사용자 인증이 만료되었습니다.");

        return false;
    }

    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtTokenProvider.JWT_HEADER_KEY_NAME);
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    /**
     * 토큰이 정상적으로 있다면 username, 없다면 ResponseException("로그인 필요");
     */
    public static String getUsernameFromRequest(HttpServletRequest request) {
        String jwt = JwtTokenProvider.getJwtFromRequest(request);
        if (jwt == null) {
            return null;
        }
        return JwtTokenProvider.getUsernameFromJWT(jwt);
    }

    public Authentication getAuthenticationFromUsername(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());
    }
}
