package team.haedal.gifticionfunding.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team.haedal.gifticionfunding.domain.FriendShipActionStatus;
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
}
