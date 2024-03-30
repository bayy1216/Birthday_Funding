package team.haedal.gifticionfunding.repository.funding;

import org.springframework.data.jpa.repository.JpaRepository;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;

public interface FundingArticleJpaRepository extends JpaRepository<FundingArticle, Long> {
}
