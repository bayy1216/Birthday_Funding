package team.haedal.gifticionfunding.service.gifticon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.core.exception.ResourceNotFoundException;
import team.haedal.gifticionfunding.dto.common.PagingResponse;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonDetailModel;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonModel;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;
import team.haedal.gifticionfunding.domain.giftucon.GifticonCreate;
import team.haedal.gifticionfunding.domain.giftucon.GifticonSearch;
import team.haedal.gifticionfunding.domain.giftucon.GifticonUpdate;
import team.haedal.gifticionfunding.repository.gifticon.GifticonJpaRepository;
import team.haedal.gifticionfunding.repository.gifticon.GifticonQueryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifticonService {
    private final GifticonJpaRepository gifticonJpaRepository;
    private final GifticonQueryRepository gifticonQueryRepository;

    @Transactional
    public Long createGifticon(GifticonCreate gifticonCreate) {
        Gifticon gifticon = Gifticon.create(gifticonCreate);
        gifticonJpaRepository.save(gifticon);
        return gifticon.getId();
    }

    @Transactional(readOnly = true)
    public PagingResponse<GifticonModel> pagingGifticons(PageRequest pageRequest, GifticonSearch gifticonSearch) {
        Page<Gifticon> gifticonPage = gifticonQueryRepository.getGifticonPage(pageRequest, gifticonSearch);
        return PagingResponse.from(gifticonPage, GifticonModel::from);
    }

    @Transactional(readOnly = true)
    public GifticonDetailModel getGifticon(Long gifticonId) {
        Gifticon gifticon = gifticonJpaRepository.getById(gifticonId);
        return GifticonDetailModel.from(gifticon);
    }

    @Transactional
    public void updateGifticon(Long gifticonId, GifticonUpdate gifticonUpdate) {
        Gifticon gifticon = gifticonJpaRepository.getById(gifticonId);
        gifticon.update(gifticonUpdate);
    }


    /**
     * 기프티콘 재고 추가
     * 충돌방지를 위해 비관적 락인 PESSIMISTIC_WRITE 사용
     */
    @Transactional
    public Long addGifticonStock(Long gifticonId, Integer stock) {
        Gifticon gifticon = gifticonJpaRepository.findByIdForUpdate(gifticonId)
                .orElseThrow(() -> new ResourceNotFoundException("Gifticon", gifticonId));
        gifticon.addStock(stock);
        return gifticon.getStock();
    }


    @Transactional
    public void deleteGifticon(Long gifticonId) {
        Gifticon gifticon = gifticonJpaRepository.getById(gifticonId);
        gifticon.delete();
    }

}
