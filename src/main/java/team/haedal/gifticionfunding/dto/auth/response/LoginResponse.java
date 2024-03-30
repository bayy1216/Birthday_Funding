package team.haedal.gifticionfunding.dto.auth.response;

import lombok.Builder;
import team.haedal.gifticionfunding.core.jwt.JwtToken;

@Builder
public record LoginResponse(
        String accessToken,
        String refreshToken
) {
    public static LoginResponse from(JwtToken jwtToken) {
        return LoginResponse.builder()
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
    }
}
