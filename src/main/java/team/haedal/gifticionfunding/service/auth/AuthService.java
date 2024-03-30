package team.haedal.gifticionfunding.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.core.exception.ResourceNotFoundException;
import team.haedal.gifticionfunding.core.jwt.JwtProvider;
import team.haedal.gifticionfunding.core.jwt.JwtToken;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional(readOnly = true)
    public JwtToken login(String email, String password) {
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", email));
        boolean isPasswordMatch = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (!isPasswordMatch) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return jwtProvider.createToken(user.getId(), user.getRole());
    }
}
