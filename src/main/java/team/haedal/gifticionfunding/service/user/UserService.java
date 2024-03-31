package team.haedal.gifticionfunding.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.core.exception.ResourceNotFoundException;
import team.haedal.gifticionfunding.dto.user.response.UserInfoDto;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.entity.user.UserEmailCreate;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserJpaRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", email));
    }

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
    public UserInfoDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        return UserInfoDto.from(user);
    }
}
