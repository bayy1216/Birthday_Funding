package team.haedal.gifticionfunding.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import team.haedal.gifticionfunding.entity.user.Friendship;
import team.haedal.gifticionfunding.entity.user.QFriendship;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class FriendshipQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 사용자의 친구 목록을 페이징하여 조회한다.
     * [user1]과 친구인 [user2]를 조회한다.
     * [user2]를 fetch join하여 N+1 문제를 해결한다.
     */
    public Page<Friendship> getFriendshipPage(PageRequest pageRequest, Long userId) {
        Long count = jpaQueryFactory.from(QFriendship.friendship)
                .select(QFriendship.friendship.count())
                .where(
                        QFriendship.friendship.user.id.eq(userId)
                )
                .fetchOne();
        List<Friendship> friendships = jpaQueryFactory.from(QFriendship.friendship)
                .select(QFriendship.friendship)
                .join(QFriendship.friendship.friend).fetchJoin()
                .where(
                        QFriendship.friendship.user.id.eq(userId)
                )
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
        return new PageImpl<>(friendships, pageRequest, count);
    }


    public List<Long> getFriendOfFriendIds(Long userId) {
        return jpaQueryFactory.from(QFriendship.friendship)
                .select(QFriendship.friendship.friend.id)
                .where(
                        QFriendship.friendship.user.id.in(
                                jpaQueryFactory.from(QFriendship.friendship)
                                        .select(QFriendship.friendship.friend.id)
                                        .where(
                                                QFriendship.friendship.user.id.eq(userId)
                                        )
                        )
                )
                .fetch();
    }
}
