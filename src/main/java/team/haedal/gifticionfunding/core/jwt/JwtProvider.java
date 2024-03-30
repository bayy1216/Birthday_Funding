package team.haedal.gifticionfunding.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import team.haedal.gifticionfunding.domain.Role;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expire-time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;
    @Value("${jwt.refresh-token-expire-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;
    private static final String ROLE = "role";
    private static final String IS_ACCESS_TOKEN = "isAccessToken";

    public String parseHeader(String header, boolean isJwt){
        String prefix = isJwt ? "Bearer " : "Basic ";
        if(header == null || header.isEmpty()){
            throw new IllegalArgumentException("Authorization 헤더가 없습니다.");
        } else if(!header.startsWith(prefix)){
            throw new IllegalArgumentException("Authorization 올바르지 않습니다.");
        } else if(header.split(" ").length != 2){
            throw new IllegalArgumentException("Authorization 올바르지 않습니다.");
        }

        return header.split(" ")[1];
    }

    public JwtToken createToken(Long userId, Role userRole){
        String accessToken = generateToken(userId, userRole,true);
        String refreshToken = generateToken(userId, userRole,false);
        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * [validateToken] 이후 호출하는 메서드.
     * UserId를 추출한다.
     */
    public Long extractMemberId(String rawToken){
        Claims claims = extractClaims(rawToken);
        return Long.parseLong(claims.getSubject());
    }

    /**
     * Jwt가 유효한지 검사하는 메서드.
     * 만료시간, 토큰의 유효성을 검사한다.
     */
    public boolean validateToken(String rawToken){
        try{
            Claims claims = extractClaims(rawToken);
            return !claims.getExpiration().before(new Date());
        }catch (Exception e){//JwtException, ExpiredJwtException, NullPointerException
            return false;
        }
    }

    /**
     * [validateToken] 이후 호출하는 메서드.
     * refreshToken을 통해, accessToken을 재발급하는 메서드.
     * refreshToken의 유효성을 검사하고, isAccessToken이 true일때만 accessToken을 재발급한다.
     */
    public String reissueAccessToken(String refreshToken){
        Claims claims = extractClaims(refreshToken);
        if(claims.get(IS_ACCESS_TOKEN, Boolean.class)){
            throw new JwtException("RefreshToken이 유효하지 않습니다.");
        }
        Long userId = Long.parseLong(claims.getSubject());
        Role userRole = Role.valueOf(claims.get(ROLE, String.class));
        return generateToken(userId, userRole, true);

    }

    /**
     * filter에서 사용하는 메서드.
     * 시큐리티 User를 생성하고, Authentication을 생성한다.
     * Authentication에 userId와 role이 저장된다.
     */
    public Authentication getAuthentication(String rawToken){
        Claims claims = extractClaims(rawToken);
        Role userRole = Role.valueOf(claims.get(ROLE, String.class));
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(userRole.getValue()));
        User user = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(user, rawToken, authorities);
    }



    /**
     * Jwt 토큰생성
     * accessToken과 refreshToken의 다른점은 만료시간과, isAccessToken이다.
     */
    private String generateToken(Long userId, Role userRole, boolean isAccessToken){
        Key secretKey = generateKey();
        long expireTime = isAccessToken ? ACCESS_TOKEN_EXPIRE_TIME : REFRESH_TOKEN_EXPIRE_TIME;
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        return Jwts.builder()
                .signWith(secretKey)
                .claim(ROLE, userRole.getValue())
                .claim(IS_ACCESS_TOKEN, isAccessToken)
                .setSubject(userId.toString())
                .setExpiration(expireDate)
                .compact();
    }


    /**
     *HS256방식의 키를 생성한다.
     */
    private Key generateKey(){
        return new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    private Claims extractClaims(String rawToken){
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(rawToken)
                .getBody();
    }
}
