package team.haedal.gifticionfunding.service.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import team.haedal.gifticionfunding.domain.user.FriendShipActionStatus;
import team.haedal.gifticionfunding.entity.user.Friendship;
import team.haedal.gifticionfunding.entity.user.FriendshipAction;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.fixture.EntityGenerator;
import team.haedal.gifticionfunding.repository.user.FriendshipActionJpaRepository;
import team.haedal.gifticionfunding.repository.user.FriendshipJpaRepository;
import team.haedal.gifticionfunding.repository.user.FriendshipQueryRepository;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserFriendServiceTest extends EntityGenerator {
    @Autowired private UserJpaRepository userRepository;
    @Autowired private FriendshipJpaRepository friendshipRepository;
    @Autowired private FriendshipActionJpaRepository friendshipActionRepository;
    @Autowired private UserFriendService userFriendService;
    @Autowired private EntityManager em;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
        friendshipRepository.deleteAllInBatch();
        friendshipActionRepository.deleteAllInBatch();
    }

    @DisplayName("친구 목록 페이징 조회가 정상적으로 동작하는지 확인")
    @Test
    void getFriendPaging() {
    }

    @Test
    void getSentFriendRequestPaging() {
    }

    @Test
    void requestFriend() {
    }

    @Test
    void getReceivedFriendRequestPaging() {
    }

    @Test
    void acceptFriendRequest() {
        // given
        User userA = super.createUser("eamiltest1", "nickname1");
        User userB = super.createUser("eamiltest2", "nickname2");
        userRepository.save(userA);
        userRepository.save(userB);

        FriendshipAction friendshipAction = FriendshipAction.create(userA, userB, FriendShipActionStatus.REQUEST);

        friendshipActionRepository.save(friendshipAction);


        em.clear();

        // when
        userFriendService.acceptFriendRequest(userB.getId(), friendshipAction.getId());

        // then
        Friendship savedFriendship = friendshipRepository.findAll().get(0);

        assertThat(savedFriendship.getUser().getId()).isEqualTo(userA.getId());
        assertThat(savedFriendship.getFriend().getId()).isEqualTo(userB.getId());

        
    }




}