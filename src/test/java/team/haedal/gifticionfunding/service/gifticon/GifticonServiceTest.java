package team.haedal.gifticionfunding.service.gifticon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import team.haedal.gifticionfunding.domain.GifticonSearch;
import team.haedal.gifticionfunding.dto.common.PagingResponse;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonModel;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;
import team.haedal.gifticionfunding.repository.gifticon.GifticonJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class GifticonServiceTest {
    @Autowired private GifticonService gifticonService;
    @Autowired private GifticonJpaRepository gifticonJpaRepository;


    @Test
    @DisplayName("기프티콘 페이징 조회가 성공한다.")
    void getGifticons() {
        // given
        Gifticon gifticon = Gifticon.builder()
                .name("테스트 기프티콘")
                .price(10000)
                .category("테스트 카테고리")
                .stock(100L)
                .imageUrl("테스트 이미지 URL")
                .description("테스트 설명")
                .brand("테스트 브랜드")
                .expirationPeriod(30)
                .build();
        Gifticon gifticon1 = Gifticon.builder()
                .name("테스트 기프티콘1")
                .price(10000)
                .category("테스트 카테고리")
                .stock(100L)
                .imageUrl("테스트 이미지 URL")
                .description("테스트 설명")
                .brand("테스트 브랜드")
                .expirationPeriod(30)
                .build();
        gifticonJpaRepository.save(gifticon);
        gifticonJpaRepository.save(gifticon1);

        PageRequest pageRequest = PageRequest.of(0, 10);
        GifticonSearch search = GifticonSearch.builder()
                .name("테스트 기프티콘")
                .brand("테스트 브랜드")
                .build();

        // when
        PagingResponse<GifticonModel> gifticons = gifticonService.pagingGifticons(pageRequest, search);

        // then
        assertThat(gifticons.data().size()).isEqualTo(1);
    }
}