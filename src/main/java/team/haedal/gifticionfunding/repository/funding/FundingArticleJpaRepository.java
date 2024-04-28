package team.haedal.gifticionfunding.repository.funding;

import org.springframework.data.jpa.repository.JpaRepository;
import team.haedal.gifticionfunding.core.exception.ResourceNotFoundException;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;

public interface FundingArticleJpaRepository extends JpaRepository<FundingArticle, Long> {
    default FundingArticle findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("FundingArticle", id));
    }
}
