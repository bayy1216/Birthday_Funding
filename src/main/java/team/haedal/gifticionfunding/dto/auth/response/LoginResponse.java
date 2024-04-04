package team.haedal.gifticionfunding.dto.auth.response;

import lombok.Builder;
import team.haedal.gifticionfunding.core.jwt.JwtToken;
import team.haedal.gifticionfunding.dto.user.response.UserInfoModel;
import team.haedal.gifticionfunding.entity.user.User;

@Builder
public record LoginResponse(
        String accessToken,
        String refreshToken,
        UserInfoModel userInfo
) {
    public static LoginResponse from(JwtToken jwtToken, User user) {
        return LoginResponse.builder()
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .userInfo(UserInfoModel.from(user))
                .build();
    }
}
