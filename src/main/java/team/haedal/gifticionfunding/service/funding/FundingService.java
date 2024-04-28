package team.haedal.gifticionfunding.service.funding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.dto.funding.request.FundingCreateRequest;
import team.haedal.gifticionfunding.dto.funding.request.FundingJoinRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundingService {

    @Transactional
    public Long createFunding(FundingCreateRequest request, Long userId) {
        return null;
    }

    @Transactional
    public void joinFunding(Long fundingId, FundingJoinRequest request, Long userId) {

    }
}
