package team.haedal.gifticionfunding.repository.funding;

import org.springframework.data.jpa.repository.JpaRepository;
import team.haedal.gifticionfunding.entity.funding.FundingContribute;

public interface FundingContributeJpaRepository extends JpaRepository<FundingContribute, Long> {
}
