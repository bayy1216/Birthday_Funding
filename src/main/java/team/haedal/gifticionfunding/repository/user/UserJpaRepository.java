package team.haedal.gifticionfunding.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import team.haedal.gifticionfunding.core.exception.ResourceNotFoundException;
import team.haedal.gifticionfunding.entity.user.User;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByNickname(String nickname);

    default User findByIdOrThrow(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
    }
}
