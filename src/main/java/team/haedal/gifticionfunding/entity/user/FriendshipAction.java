package team.haedal.gifticionfunding.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team.haedal.gifticionfunding.domain.FriendShipActionStatus;
import team.haedal.gifticionfunding.domain.Pair;
import team.haedal.gifticionfunding.entity.common.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FriendshipAction extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User toUser;

    @Enumerated(EnumType.STRING)
    private FriendShipActionStatus status;

    @Builder
    private FriendshipAction(User fromUser, User toUser, FriendShipActionStatus status) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.status = status;
    }

    public static FriendshipAction create(User fromUser, User toUser, FriendShipActionStatus status) {
        return FriendshipAction.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .status(status)
                .build();
    }

    /**
     * FriendshipAction을 Friendship으로 변환합니다.
     * Pair로 반환하여 두 개의 Friendship을 반환하여 A->B, B->A의 관계를 만듭니다.
     */
    public Pair<Friendship,Friendship> makeFriendship() {
        canAcceptCheck();
        status = FriendShipActionStatus.ACCEPT;
        Friendship first = Friendship.create(fromUser, toUser);
        Friendship second = Friendship.create(toUser, fromUser);
        return Pair.of(first, second);
    }

    private void canAcceptCheck() {
        if(!status.canAccept()){
            throw new IllegalStateException("수락 가능한 상태가 아닙니다.");
        }
    }

    public void reject() {
        canAcceptCheck();
        status = FriendShipActionStatus.REJECT;
    }
}
