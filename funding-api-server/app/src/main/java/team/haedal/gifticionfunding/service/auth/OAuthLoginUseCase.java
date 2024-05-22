package team.haedal.gifticionfunding.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.core.jwt.JwtProvider;
import team.haedal.gifticionfunding.core.jwt.JwtToken;
import team.haedal.gifticionfunding.core.jwt.JwtUser;
import team.haedal.gifticionfunding.domain.user.VendorUserInfo;
import team.haedal.gifticionfunding.dto.auth.response.LoginResponse;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;
import team.haedal.gifticionfunding.service.common.UseCase;

@Component
@RequiredArgsConstructor
class OAuthLoginUseCase implements UseCase<VendorUserInfo, LoginResponse> {
    private final UserJpaRepository userRepository;
    private final JwtProvider jwtProvider;

    /**
     * OAuth 로그인을 처리하는 UseCase
     * 사용자 정보가 존재하지 않으면 새로운 사용자를 생성하고 JWT 토큰을 발급한다.
     */
    @Transactional
    @Override
    public LoginResponse invoke(VendorUserInfo vendorUserInfo) {
        User user = userRepository.findByVendorEmailAndVendor(vendorUserInfo.getVendorEmail(), vendorUserInfo.getVendor()).orElseGet(() -> {
            User newUser = User.create(vendorUserInfo);
            return userRepository.save(newUser);
        });
        JwtUser jwtUser = JwtUser.of(user.getId(), user.getRole());
        JwtToken token = jwtProvider.createToken(jwtUser);
        return LoginResponse.from(token, user);
    }

}
