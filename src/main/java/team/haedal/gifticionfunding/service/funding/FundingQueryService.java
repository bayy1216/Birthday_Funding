package team.haedal.gifticionfunding.service.funding;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.dto.common.PagingRequest;
import team.haedal.gifticionfunding.dto.common.PagingResponse;
import team.haedal.gifticionfunding.dto.funding.response.FundingArticleDetailModel;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonModel;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;
import team.haedal.gifticionfunding.entity.funding.FundingArticleGifticon;
import team.haedal.gifticionfunding.entity.funding.FundingContribute;
import team.haedal.gifticionfunding.repository.funding.FundingQueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingQueryService {
    private final FundingQueryRepository fundingQueryRepository;

    @Transactional(readOnly = true)
    public PagingResponse<GifticonModel> pagingFundings(PagingRequest pagingRequest, Long userId) {
        return null;
    }

    @Transactional(readOnly = true)
    public FundingArticleDetailModel getFundingDetail(Long fundingId) {
        FundingArticle fundingArticle = fundingQueryRepository.findByIdWithUserOrThrow(fundingId);

        return FundingArticleDetailModel.from(fundingArticle);
    }
}
