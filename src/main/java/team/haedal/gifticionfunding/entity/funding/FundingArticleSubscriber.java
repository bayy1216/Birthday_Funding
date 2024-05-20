package team.haedal.gifticionfunding.entity.funding;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team.haedal.gifticionfunding.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 펀딩 게시글 구독자 테이블 <br>
 * 게시글을 작성시 작성자와 친구인 사용자들의 최근 게시글을 조회하기 위해 사용 <p>
 * ex) A가 게시글을 작성하면 A의 친구인 B, C, D가 A의 게시글을 볼 수 있음 <br>
 *  - A의 게시글을 볼 수 있는 사용자들은 A, B, C, D <p>
 * 이메일의 경우를 생각해보면 된다. 사용자들은 이메일함에서 최근 이메일을 볼수 있다.
 * 조회 성능을 향상시키기 위해 사용하는 방법으로, 공간 복잡도를 늘리는 대신 시간 복잡도를 줄임
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FundingArticleSubscriber {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FundingArticle fundingArticle;

    private LocalDateTime articleCreatedAt;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    private User subscriber;

    public static FundingArticleSubscriber createById(FundingArticle fundingArticle, Long userId) {
        FundingArticleSubscriber fundingArticleSubscriber = new FundingArticleSubscriber();
        fundingArticleSubscriber.fundingArticle = fundingArticle;
        fundingArticleSubscriber.articleCreatedAt = fundingArticle.getCreatedAt();
        fundingArticleSubscriber.subscriber = User.mockById(userId);
        return fundingArticleSubscriber;
    }

    public static List<FundingArticleSubscriber> createByUserIds(FundingArticle fundingArticle, List<Long> userIds) {
        return userIds.stream()
                .map(userId -> FundingArticleSubscriber.createById(fundingArticle, userId))
                .toList();
    }
}
