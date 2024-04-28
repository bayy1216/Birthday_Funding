package team.haedal.gifticionfunding.service.funding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.dto.funding.request.FundingJoinRequest;
import team.haedal.gifticionfunding.domain.funding.FundingArticleCreate;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;
import team.haedal.gifticionfunding.entity.funding.FundingArticleGifticon;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.repository.funding.FundingArticleJpaRepository;
import team.haedal.gifticionfunding.repository.gifticon.GifticonJpaRepository;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundingService {
    private final UserJpaRepository userRepository;
    private final GifticonJpaRepository gifticonRepository;
    private final FundingArticleJpaRepository fundingArticleRepository;

    /**
     * 펀딩 생성
     * 1. 펀딩 게시글을 생성
     * 2. 희망 기프티콘을 giftcionIds로 조회하여 펀딩 게시글에 등록
     * 3. 펀딩 게시글 ID 반환
     */
    @Transactional
    public Long createFunding(FundingArticleCreate create, List<Long> gifticonIds, Long userId) {
        User user = userRepository.findByIdOrThrow(userId);
        FundingArticle fundingArticle = FundingArticle.create(create, user);
        List<Gifticon> gifticons = gifticonRepository.findAllByActiveAndIdIn(true, gifticonIds);

        if(gifticons.size() != gifticonIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 기프티콘 ID가 포함되어 있습니다.");
        }

        List<FundingArticleGifticon> fundingArticleGifticons
                = gifticons.stream()
                .map(gifticon -> FundingArticleGifticon.create(fundingArticle,gifticon)).toList();


        return fundingArticle.getId();
    }

    @Transactional
    public void joinFunding(Long fundingId, FundingJoinRequest request, Long userId) {

    }
}
