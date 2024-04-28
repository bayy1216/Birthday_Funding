package team.haedal.gifticionfunding.service.funding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.dto.funding.request.FundingJoinRequest;
import team.haedal.gifticionfunding.domain.funding.FundingArticleCreate;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundingService {
    private final UserJpaRepository userRepository;

    @Transactional
    public Long createFunding(FundingArticleCreate create, Long userId) {
        User user = userRepository.findByIdOrThrow(userId);
        return 1L;
    }

    @Transactional
    public void joinFunding(Long fundingId, FundingJoinRequest request, Long userId) {

    }
}
