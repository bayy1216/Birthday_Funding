package team.haedal.gifticionfunding.entity.funding;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.haedal.gifticionfunding.domain.funding.FundingArticleCreate;
import team.haedal.gifticionfunding.entity.common.BaseTimeEntity;
import team.haedal.gifticionfunding.entity.user.User;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FundingArticle extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    @Builder
    public FundingArticle(String title, String content, User user, LocalDateTime startAt, LocalDateTime endAt) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static FundingArticle create(FundingArticleCreate fundingArticleCreate, User user) {
        return FundingArticle.builder()
                .title(fundingArticleCreate.getTitle())
                .content(fundingArticleCreate.getContent())
                .user(user)
                .startAt(fundingArticleCreate.getStartAt())
                .endAt(fundingArticleCreate.getEndAt())
                .build();
    }



}
