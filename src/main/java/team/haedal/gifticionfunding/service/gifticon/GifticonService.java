package team.haedal.gifticionfunding.service.gifticon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.dto.common.PagingResponse;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonDetailDto;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonDto;
import team.haedal.gifticionfunding.entity.gifticon.GifticonCreate;
import team.haedal.gifticionfunding.entity.gifticon.GifticonSearch;
import team.haedal.gifticionfunding.entity.gifticon.GifticonUpdate;
import team.haedal.gifticionfunding.repository.gifticon.GifticonJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifticonService {
    private final GifticonJpaRepository gifticonJpaRepository;

    @Transactional
    public Long createGifticon(GifticonCreate gifticonCreate) {
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }

    @Transactional
    public PagingResponse<GifticonDto> getGifticons(PageRequest pageRequest, GifticonSearch gifticonSearch) {
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }

    @Transactional
    public GifticonDetailDto getGifticon(Long gifticonId) {
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }


    @Transactional
    public void updateGifticon(Long gifticonId, GifticonUpdate gifticonUpdate) {
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }


    @Transactional
    public Integer addGifticonStock(Long gifticonId, Integer stock) {
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }



    @Transactional
    public void deleteGifticon(Long gifticonId) {
        gifticonJpaRepository.deleteById(gifticonId);
    }

}
