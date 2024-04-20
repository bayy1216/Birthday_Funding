package team.haedal.gifticionfunding.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.core.jwt.JwtProvider;
import team.haedal.gifticionfunding.core.jwt.JwtToken;
import team.haedal.gifticionfunding.core.jwt.JwtUser;
import team.haedal.gifticionfunding.domain.Vendor;
import team.haedal.gifticionfunding.domain.VendorUserInfo;
import team.haedal.gifticionfunding.dto.auth.response.LoginResponse;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.entity.user.UserEmailCreate;
import team.haedal.gifticionfunding.repository.auth.OAuthClient;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserJpaRepository userRepository;
    private final List<OAuthClient> oAuthClients;
    private final OAuthLoginUseCase oAuthLoginUseCase;

    @Deprecated(since = "OAUTH 로그인에 포함될 예정입니다.")
    @Transactional
    public Long createUser(UserEmailCreate userEmailCreate) {
        String bCryptPassword = bCryptPasswordEncoder.encode(userEmailCreate.getPassword());
        userEmailCreate = userEmailCreate.changePassword(bCryptPassword);

        userRepository.findByEmail(userEmailCreate.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 가입되어 있는 이메일입니다.");
                });
        if(userRepository.existsByNickname(userEmailCreate.getNickname())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }
        User user = User.from(userEmailCreate);
        return userRepository.save(user).getId();
    }

    public LoginResponse oAuthLogin(Vendor vendor, String code){
        OAuthClient oAuthClient = oAuthClients.stream()
                .filter(client -> client.canHandle(vendor))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 OAuth입니다."));

        String accessToken = oAuthClient.getAccessToken(code);
        VendorUserInfo vendorUserInfo = oAuthClient.getVendorUserInfo(accessToken);
        return oAuthLoginUseCase.invoke(vendorUserInfo);
    }
}

@Component
@RequiredArgsConstructor
class OAuthLoginUseCase {
    private final UserJpaRepository userRepository;
    private final JwtProvider jwtProvider;

    /**
     * OAuth 로그인을 처리하는 UseCase
     * 사용자 정보가 존재하지 않으면 새로운 사용자를 생성하고 JWT 토큰을 발급한다.
     */
    @Transactional
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
