package team.haedal.gifticionfunding.repository.funding;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;

import java.util.List;

import static team.haedal.gifticionfunding.entity.funding.QFundingArticle.fundingArticle;
import static team.haedal.gifticionfunding.entity.funding.QFundingArticleGifticon.fundingArticleGifticon;

@Repository
@RequiredArgsConstructor
public class FundingQueryRepository {
    private final JPAQueryFactory queryFactory;

    public FundingArticle findByIdWithUserOrThrow(Long fundingId) {
        FundingArticle article = queryFactory
                .select(fundingArticle)
                .from(fundingArticle)
                .join(fundingArticle.user).fetchJoin()
                .leftJoin(fundingArticle.fundingArticleGifticons, fundingArticleGifticon).fetchJoin()
                .where(fundingArticle.id.eq(fundingId))
                .fetchOne();


        if(article == null) {
            throw new IllegalArgumentException("해당 펀딩이 존재하지 않습니다.");
        }
        return article;
    }
}
