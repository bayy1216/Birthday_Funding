package team.haedal.gifticionfunding.entity.funding;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.haedal.gifticionfunding.entity.common.BaseTimeEntity;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FundingArticleGifticon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_article_id")
    private FundingArticle fundingArticle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gifticon_id")
    private Gifticon gifticon;

}
