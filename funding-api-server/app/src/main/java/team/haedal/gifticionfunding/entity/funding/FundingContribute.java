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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_article_id")
    private FundingArticle fundingArticle;

    private Integer point;

    private void validCheck(){
        if(point !=null && point <=0){
            throw new IllegalArgumentException("point는 0보다 커야합니다.");
        }
        if(point == null && fundingArticle == null){
            throw new RuntimeException("point와 fundingArticle 둘다 null일 수 없습니다.");
        }
        if(point != null && fundingArticle != null){
            throw new RuntimeException("point와 fundingArticle 둘다 null이 아닐 수 없습니다.");
        }
    }

    @Builder
    public FundingContribute(User user, UserGifticon userGifticon, FundingArticle fundingArticle, Integer point) {
        this.user = user;
        this.userGifticon = userGifticon;
        this.fundingArticle = fundingArticle;
        this.point = point;
        validCheck();
    }


    public static FundingContribute create(User user, UserGifticon userGifticon, FundingArticle fundingArticle) {
        return FundingContribute.builder()
                .user(user)
                .userGifticon(userGifticon)
                .fundingArticle(fundingArticle)
                .build();
    }

    public static FundingContribute create(User user, Integer point, FundingArticle fundingArticle) {
        return FundingContribute.builder()
                .user(user)
                .point(point)
                .fundingArticle(fundingArticle)
                .build();
    }

    public int getPrice(){
        if(point != null){
            return point;
        }else{
            return userGifticon.getPrice();
        }
    }
}
