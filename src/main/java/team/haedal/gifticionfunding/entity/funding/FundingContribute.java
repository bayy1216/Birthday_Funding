package team.haedal.gifticionfunding.entity.funding;

import jakarta.persistence.*;
import lombok.*;
import team.haedal.gifticionfunding.entity.common.BaseTimeEntity;
import team.haedal.gifticionfunding.entity.gifticon.UserGifticon;
import team.haedal.gifticionfunding.entity.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FundingContribute extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funding_participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_gifticon_id")
    private UserGifticon userGifticon;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "funding_article_id")
    private FundingArticle fundingArticle;

    private Integer point;

}
