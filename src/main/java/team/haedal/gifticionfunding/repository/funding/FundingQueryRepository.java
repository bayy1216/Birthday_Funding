package team.haedal.gifticionfunding.repository.funding;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;
import team.haedal.gifticionfunding.entity.funding.FundingArticleGifticon;
import team.haedal.gifticionfunding.entity.funding.FundingContribute;
import team.haedal.gifticionfunding.entity.funding.QFundingContribute;

import java.util.List;

import static team.haedal.gifticionfunding.entity.funding.QFundingArticle.fundingArticle;
import static team.haedal.gifticionfunding.entity.funding.QFundingArticleGifticon.fundingArticleGifticon;
import static team.haedal.gifticionfunding.entity.funding.QFundingContribute.fundingContribute;
import static team.haedal.gifticionfunding.entity.gifticon.QGifticon.gifticon;
import static team.haedal.gifticionfunding.entity.gifticon.QUserGifticon.userGifticon;

@Repository
@RequiredArgsConstructor
public class FundingQueryRepository {
    private final JPAQueryFactory queryFactory;

    public FundingArticle findByIdWithUserOrThrow(Long fundingId) {
        FundingArticle article = queryFactory
                .selectFrom(fundingArticle)
                .join(fundingArticle.user)
                .fetchJoin()
                .where(fundingArticle.id.eq(fundingId))
                .fetchOne();
        if(article == null) {
            throw new IllegalArgumentException("해당 펀딩이 존재하지 않습니다.");
        }
        return article;
    }

    public List<FundingContribute> getFundingContributes(Long fundingId) {

        return queryFactory
                .selectFrom(fundingContribute)
                .join(fundingContribute.userGifticon, userGifticon).fetchJoin()
                .join(userGifticon.gifticon, gifticon).fetchJoin()
                .where(fundingContribute.fundingArticle.id.eq(fundingId))
                .fetch();
    }

    public List<FundingArticleGifticon> getFundingArticleGifticons(Long fundingId) {
        return queryFactory
                .selectFrom(fundingArticleGifticon)
                .join(fundingArticleGifticon.gifticon)
                .fetchJoin()
                .where(fundingArticleGifticon.fundingArticle.id.eq(fundingId))
                .fetch();
    }
}
