package team.haedal.gifticionfunding.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.haedal.gifticionfunding.domain.user.FriendShipActionStatus;
import team.haedal.gifticionfunding.entity.user.FriendshipAction;

public interface FriendshipActionJpaRepository extends JpaRepository<FriendshipAction, Long> {
    Page<FriendshipAction> findAllByToUserIdAndStatus(Long toUserId, FriendShipActionStatus status, Pageable pageable);
    Page<FriendshipAction> findAllByFromUserIdAndStatus(Long fromUserId, FriendShipActionStatus status, Pageable pageable);
}
