package team.haedal.gifticionfunding.repository.funding;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;

import java.util.List;

import static team.haedal.gifticionfunding.entity.funding.QFundingArticle.fundingArticle;
import static team.haedal.gifticionfunding.entity.funding.QFundingArticleGifticon.fundingArticleGifticon;
import static team.haedal.gifticionfunding.entity.funding.QFundingContribute.*;
import static team.haedal.gifticionfunding.entity.gifticon.QGifticon.*;
import static team.haedal.gifticionfunding.entity.gifticon.QUserGifticon.*;

@Repository
@RequiredArgsConstructor
public class FundingQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 펀딩 아이디로 펀딩을 조회합니다. <br>
     * OneToMany 관계인 FundingArticleGifticon과 FundingArticleGifticon의 Gifticon을 fetchJoin합니다.
     * quesydsl에서 에러가 계속 떠서 짜기 힘들었다...
     */
    public FundingArticle findByIdWithUserOrThrow(Long fundingId) {
        FundingArticle article = queryFactory
                .select(fundingArticle)
                .from(fundingArticle)
                .join(fundingArticle.user).fetchJoin()
                .join(fundingArticle.fundingArticleGifticons, fundingArticleGifticon).fetchJoin()
                .join(fundingArticleGifticon.gifticon).fetchJoin()
                .where(fundingArticle.id.eq(fundingId))
                .fetchOne();


        if(article == null) {
            throw new IllegalArgumentException("해당 펀딩이 존재하지 않습니다.");
        }
        return article;
    }

    /**
     * 펀딩에 현재까지 모금된 금액을 조회합니다.
     * 이거도 짜기 힘들었음 레전드
     */
    public Integer getFundingArticleCurrentMoney(Long fundingId) {
        Integer i =queryFactory.select(fundingContribute.userGifticon.gifticon.price.sum())
            .from(fundingContribute)
            .join(fundingContribute.userGifticon, userGifticon)
            .join(userGifticon.gifticon)
            .where(fundingContribute.fundingArticle.id.eq(fundingId))
            .fetchOne();
        return i == null ? 0 : i;
    }
}
