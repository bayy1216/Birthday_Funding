package team.haedal.gifticionfunding.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.core.exception.ResourceNotFoundException;
import team.haedal.gifticionfunding.core.jwt.JwtProvider;
import team.haedal.gifticionfunding.core.jwt.JwtToken;
import team.haedal.gifticionfunding.core.jwt.JwtUser;
import team.haedal.gifticionfunding.domain.user.Vendor;
import team.haedal.gifticionfunding.domain.user.VendorUserInfo;
import team.haedal.gifticionfunding.dto.auth.response.LoginResponse;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.domain.user.UserEmailCreate;
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
    private final JwtProvider jwtProvider;

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

    @Transactional(readOnly = true)
    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", email));
        boolean isPasswordMatch = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (!isPasswordMatch) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        JwtUser jwtUser = JwtUser.of(user.getId(), user.getRole());
        JwtToken token = jwtProvider.createToken(jwtUser);
        return LoginResponse.from(token, user);
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


