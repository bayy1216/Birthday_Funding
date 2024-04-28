package team.haedal.gifticionfunding.entity.funding;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.haedal.gifticionfunding.domain.funding.FundingArticleCreate;
import team.haedal.gifticionfunding.entity.common.BaseTimeEntity;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;
import team.haedal.gifticionfunding.entity.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fundingArticle",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FundingArticleGifticon> fundingArticleGifticons = new ArrayList<>();

    @Builder
    public FundingArticle(String title, String content, User user, LocalDateTime startAt, LocalDateTime endAt, List<FundingArticleGifticon> fundingArticleGifticons) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.startAt = startAt;
        this.endAt = endAt;
        this.fundingArticleGifticons = fundingArticleGifticons;
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

    /**
     * 기프티콘을 인자로 받아서 OneToMany 관계인 FundingArticleGifticon을 생성합니다.
     */
    public static FundingArticle create(FundingArticleCreate fundingArticleCreate, User user, List<Gifticon> gifticons) {
        FundingArticle newArticle =  FundingArticle.builder()
                .title(fundingArticleCreate.getTitle())
                .content(fundingArticleCreate.getContent())
                .user(user)
                .startAt(fundingArticleCreate.getStartAt())
                .endAt(fundingArticleCreate.getEndAt())
                .build();
        List<FundingArticleGifticon> fundingArticleGifticons = gifticons
                .stream()
                .map(gifticon -> FundingArticleGifticon.create(newArticle, gifticon))
                .toList();
        newArticle.setFundingArticleGifticons(fundingArticleGifticons);
        return newArticle;
    }
    private void setFundingArticleGifticons(List<FundingArticleGifticon> fundingArticleGifticons) {
        this.fundingArticleGifticons = fundingArticleGifticons;
    }

    public int getTotalPrice(){
        return fundingArticleGifticons.stream()
                .mapToInt(FundingArticleGifticon::getPrice)
                .sum();
    }



}
