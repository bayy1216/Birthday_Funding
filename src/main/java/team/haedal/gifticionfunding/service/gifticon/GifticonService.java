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
import team.haedal.gifticionfunding.entity.gifticon.GifticonCreate;
import team.haedal.gifticionfunding.domain.GifticonSearch;
import team.haedal.gifticionfunding.entity.gifticon.GifticonUpdate;
import team.haedal.gifticionfunding.repository.gifticon.GifticonJpaRepository;
import team.haedal.gifticionfunding.repository.gifticon.GifticonQueryRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifticonService {
    private final GifticonJpaRepository gifticonJpaRepository;
    private final GifticonQueryRepository gifticonQueryRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public Long createGifticon(GifticonCreate gifticonCreate) {
        Gifticon gifticon = Gifticon.create(gifticonCreate);
        gifticonJpaRepository.save(gifticon);
        return gifticon.getId();
    }

    @Transactional(readOnly = true)
    public PagingResponse<GifticonModel> getGifticons(PageRequest pageRequest, GifticonSearch gifticonSearch) {
        Page<Gifticon> gifticonPage = gifticonQueryRepository.getGifticonPage(pageRequest, gifticonSearch);
        List<GifticonModel> gifticonModels = gifticonPage.getContent().stream()
                .map(GifticonModel::from).toList();

        return PagingResponse.<GifticonModel>builder()
                .hasNext(gifticonPage.hasNext())
                .data(gifticonModels)
                .build();
    }

    @Transactional(readOnly = true)
    public GifticonDetailModel getGifticon(Long gifticonId) {
        Gifticon gifticon = gifticonJpaRepository.getById(gifticonId);
        return GifticonDetailModel.from(gifticon);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void updateGifticon(Long gifticonId, GifticonUpdate gifticonUpdate) {
        Gifticon gifticon = gifticonJpaRepository.getById(gifticonId);
        gifticon.update(gifticonUpdate);
    }


    /**
     * 기프티콘 재고 추가
     * 충돌방지를 위해 비관적 락인 PESSIMISTIC_WRITE 사용
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public Long addGifticonStock(Long gifticonId, Integer stock) {
        Gifticon gifticon = gifticonJpaRepository.findByIdForUpdate(gifticonId)
                .orElseThrow(() -> new ResourceNotFoundException("Gifticon", gifticonId));
        Long newStock = gifticon.addStock(stock);
        return newStock;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void deleteGifticon(Long gifticonId) {
        gifticonJpaRepository.deleteById(gifticonId);
    }

}
