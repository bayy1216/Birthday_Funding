package team.haedal.gifticionfunding.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.haedal.gifticionfunding.entity.user.Friendship;

import java.util.List;

public interface FriendshipJpaRepository extends JpaRepository<Friendship, Long> {

    @Query(value = "SELECT f.friend_id FROM friendship f " +
            "WHERE f.user_id IN (" +
            "    SELECT ff.friend_id FROM friendship ff WHERE ff.user_id = :userId" +
            ") UNION SELECT fff.friend_id FROM friendship fff WHERE fff.user_id = :userId",
            nativeQuery = true)
    List<Long> getFriendOfFriendIds(@Param("userId") Long userId);
}
