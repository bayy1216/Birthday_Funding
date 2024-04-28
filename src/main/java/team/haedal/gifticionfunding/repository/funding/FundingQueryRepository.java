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
                .join(fundingArticle.user).fetchJoin()
                .join(fundingArticle.fundingArticleGifticons, fundingArticleGifticon).fetchJoin()
                .join(fundingArticleGifticon.gifticon).fetchJoin()
                .join(fundingArticle.fundingContributes, fundingContribute).fetchJoin()
                .join(fundingContribute.userGifticon, userGifticon).fetchJoin()
                .join(userGifticon.gifticon, gifticon).fetchJoin()
                .where(fundingArticle.id.eq(fundingId))
                .fetchOne();
        if(article == null) {
            throw new IllegalArgumentException("해당 펀딩이 존재하지 않습니다.");
        }
        return article;
    }
}
