package team.haedal.gifticionfunding.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.core.exception.ResourceNotFoundException;
import team.haedal.gifticionfunding.core.jwt.JwtProvider;
import team.haedal.gifticionfunding.core.jwt.JwtToken;
import team.haedal.gifticionfunding.dto.auth.response.LoginResponse;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.entity.user.UserEmailCreate;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserJpaRepository userRepository;

    @Transactional
    public Long createUser(UserEmailCreate userEmailCreate) {
        String bCryptPassword = bCryptPasswordEncoder.encode(userEmailCreate.getPassword());
        userEmailCreate = userEmailCreate.changePassword(bCryptPassword);

        userRepository.findByEmail(userEmailCreate.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 가입되어 있는 이메일입니다.");
                });
        User user = User.from(userEmailCreate);
        return userRepository.save(user).getId();
    }

    @Transactional(readOnly = true)
    public LoginResponse login(String email, String password) {
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", email));
        boolean isPasswordMatch = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (!isPasswordMatch) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        JwtToken token = jwtProvider.createToken(user.getId(), user.getRole());
        return LoginResponse.from(token, user);
    }
}
