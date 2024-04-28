package team.haedal.gifticionfunding.service.gifticon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import team.haedal.gifticionfunding.domain.giftucon.GifticonSearch;
import team.haedal.gifticionfunding.domain.giftucon.GifticonSort;
import team.haedal.gifticionfunding.domain.Status;
import team.haedal.gifticionfunding.dto.common.PagingResponse;
import team.haedal.gifticionfunding.dto.gifticon.response.GifticonModel;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;
import team.haedal.gifticionfunding.repository.gifticon.GifticonJpaRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class GifticonServiceTest {
    @Autowired private GifticonService gifticonService;
    @Autowired private GifticonJpaRepository gifticonJpaRepository;

    @AfterEach
    void tearDown() {
        gifticonJpaRepository.deleteAllInBatch();

    }


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
                .build();

        // when
        PagingResponse<GifticonModel> gifticons = gifticonService.pagingGifticons(pageRequest, search);

        // then
        assertThat(gifticons.data().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("기프티콘 페이징 가격 필터링 조회가 성공한다.")
    public void getGifticonsByPrice() {
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
                .price(20000)
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
                .priceMax(15000)
                .priceMin(5000)
                .build();

        // when
        PagingResponse<GifticonModel> gifticons = gifticonService.pagingGifticons(pageRequest, search);

        // then
        assertThat(gifticons.data().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("기프티콘 페이징 이름 필터링 조회가 성공한다.")
    public void getGifticonsByName() {
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
                .price(20000)
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
                .build();

        // when
        PagingResponse<GifticonModel> gifticons = gifticonService.pagingGifticons(pageRequest, search);

        // then
        assertThat(gifticons.data().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("기프티콘 페이징 카테고리 필터링 조회가 성공한다.")
    public void getGifticonsByCategory() {
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
                .price(20000)
                .category("테스트 카테고리1")
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
                .category("테스트 카테고리")
                .build();

        // when
        PagingResponse<GifticonModel> gifticons = gifticonService.pagingGifticons(pageRequest, search);

        // then
        assertThat(gifticons.data().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("기프티콘 페이징 브랜드 필터링 조회가 성공한다.")
    public void getGifticonsByBrand() {
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
                .price(20000)
                .category("테스트 카테고리1")
                .stock(100L)
                .imageUrl("테스트 이미지 URL")
                .description("테스트 설명")
                .brand("테스트 브랜드1")
                .expirationPeriod(30)
                .build();
        gifticonJpaRepository.save(gifticon);
        gifticonJpaRepository.save(gifticon1);

        PageRequest pageRequest = PageRequest.of(0, 10);
        GifticonSearch search = GifticonSearch.builder()
                .brand("테스트 브랜드")
                .build();

        // when
        PagingResponse<GifticonModel> gifticons = gifticonService.pagingGifticons(pageRequest, search);

        // then
        assertThat(gifticons.data().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("기프티콘 페이징 정렬 조회가 성공한다.")
    public void getGifticonsBySort() {
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
                .price(20000)
                .category("테스트 카테고리1")
                .stock(100L)
                .imageUrl("테스트 이미지 URL")
                .description("테스트 설명")
                .brand("테스트 브랜드1")
                .expirationPeriod(30)
                .build();
        gifticonJpaRepository.save(gifticon);
        gifticonJpaRepository.save(gifticon1);

        PageRequest pageRequest = PageRequest.of(0, 10);
        GifticonSearch search = GifticonSearch.builder()
                .gifticonSort(GifticonSort.PRICE_DESC)
                .build();

        // when
        PagingResponse<GifticonModel> gifticons = gifticonService.pagingGifticons(pageRequest, search);

        // then
        assertThat(gifticons.data().size()).isEqualTo(2);
        assertThat(gifticons.data().get(0).price()).isEqualTo(20000);
    }


    @Test
    @DisplayName("기프티콘 재고 추가가 성공한다.")
    @WithMockUser(username = "dasdas",roles = "ADMIN")
    public void addGifticonStock() {
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
        gifticonJpaRepository.save(gifticon);

        // when
        Long newStock = gifticonService.addGifticonStock(gifticon.getId(), 100);

        // then
        assertThat(newStock).isEqualTo(200);
    }

    @Test
    @DisplayName("기프티콘 재고 동시성이 발생하더라도 재고 추가가 성공한다.")
    @WithMockUser(roles = "ADMIN")
    public void addGifticonStockConcurrent() throws InterruptedException {
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
        gifticonJpaRepository.save(gifticon);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                // 각 스레드에 대한 보안 컨텍스트를 설정
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "password", AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
                SecurityContextHolder.setContext(context);

                // 보안 컨텍스트를 설정한 스레드에서 메서드 실행
                gifticonService.addGifticonStock(gifticon.getId(), 10);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        // when
        Gifticon findGifticon = gifticonJpaRepository.findById(gifticon.getId()).orElseThrow();

        // then
        assertThat(findGifticon.getStock()).isEqualTo(200);
    }



    @Test
    @DisplayName("기프티콘 soft delete가 성공한다.")
    @WithMockUser(roles = "ADMIN")
    public void deleteGifticon() {
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
        gifticonJpaRepository.save(gifticon);

        // when
        gifticonService.deleteGifticon(gifticon.getId());

        // then
        Gifticon deletedGifticon = gifticonJpaRepository.findById(gifticon.getId()).orElseThrow();
        assertThat(deletedGifticon.getStatus()).isEqualTo(Status.DELETED);
    }



}