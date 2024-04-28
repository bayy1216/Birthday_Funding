package team.haedal.gifticionfunding.service.funding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.domain.Status;
import team.haedal.gifticionfunding.domain.funding.FundingArticleCreate;
import team.haedal.gifticionfunding.entity.funding.FundingArticle;
import team.haedal.gifticionfunding.entity.funding.FundingContribute;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;
import team.haedal.gifticionfunding.entity.gifticon.UserGifticon;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.repository.funding.FundingArticleJpaRepository;
import team.haedal.gifticionfunding.repository.funding.FundingContributeJpaRepository;
import team.haedal.gifticionfunding.repository.gifticon.GifticonJpaRepository;
import team.haedal.gifticionfunding.repository.gifticon.UserGifticonJpaRepository;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundingService {
    private final UserJpaRepository userRepository;
    private final GifticonJpaRepository gifticonRepository;
    private final FundingArticleJpaRepository fundingArticleRepository;
    private final UserGifticonJpaRepository userGifticonRepository;
    private final FundingContributeJpaRepository fundingContributeRepository;

    /**
     * 펀딩 생성
     * 1. 펀딩 게시글을 생성
     * 2. 희망 기프티콘을 giftcionIds로 조회하여 펀딩 게시글에 등록
     * 3. 펀딩 게시글 ID 반환
     */
    @Transactional
    public Long createFunding(FundingArticleCreate create, List<Long> gifticonIds, Long userId) {
        User user = userRepository.findByIdOrThrow(userId);

        List<Gifticon> gifticons = gifticonRepository.findAllByStatusAndIdIn(Status.ACTIVE, gifticonIds);

        if(gifticons.size() != gifticonIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 기프티콘 ID가 포함되어 있습니다.");
        }
        FundingArticle fundingArticle = FundingArticle.create(create, user, gifticons);
        fundingArticleRepository.save(fundingArticle);
        return fundingArticle.getId();
    }

    /**
     * 펀딩 참여
     * 1. 펀딩 게시글을 조회
     * 2. 희망 기프티콘을 giftcionIds로 조회하여 펀딩 게시글에 기여합니다.
     * (소유자 변경을 위해 gifticonIds로 UserGifticon을 조회할 때는 PESSIMISTIC_WRITE Lock을 사용)
     */
    @Transactional
    public void joinFunding(Long fundingId, List<Long> userGifticonIds, Long userId) {
        User funder = userRepository.findByIdOrThrow(userId);
        FundingArticle fundingArticle = fundingArticleRepository.findByIdOrThrow(fundingId);
        List<UserGifticon> userGifticons
                = userGifticonRepository.findAllByGifticonIdsInForUpdate(userGifticonIds);
        if(userGifticons.size() != userGifticonIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 기프티콘 ID가 포함되어 있습니다.");
        }
        fundingArticle.joinFundingContribute(userGifticons, funder);
    }
}
