package team.haedal.gifticionfunding.entity.gifticon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.haedal.gifticionfunding.domain.Status;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;


class GifticonTest {

    @DisplayName("재고 추가 메서드가 정상적으로 동작한다.")
    @Test
    void addStock() {

        Gifticon gifticon = Gifticon.builder()
                .name("test")
                .price(1000)
                .category("test")
                .stock(10L)
                .imageUrl("test")
                .description("test")
                .brand("test")
                .expirationPeriod(10)
                .build();

        gifticon.addStock(10);

        assertThat(gifticon.getStock()).isEqualTo(20L);
    }

    @DisplayName("음수값을 입력하면 재고 추가 메서드가 예외를 던진다.")
    @Test
    void addStock_negative() {

        Gifticon gifticon = Gifticon.builder()
                .name("test")
                .price(1000)
                .category("test")
                .stock(10L)
                .imageUrl("test")
                .description("test")
                .brand("test")
                .expirationPeriod(10)
                .build();

        assertThat(catchThrowable(() -> gifticon.addStock(-10)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("기프티콘 삭제 메서드가 정상적으로 동작한다.")
    @Test
    void delete() {

        Gifticon gifticon = Gifticon.builder()
                .name("test")
                .price(1000)
                .category("test")
                .stock(10L)
                .imageUrl("test")
                .description("test")
                .brand("test")
                .expirationPeriod(10)
                .build();

        gifticon.delete();

        assertThat(gifticon.getStatus()).isEqualTo(Status.DELETED);
    }

    @DisplayName("이미 삭제된 기프티콘을 삭제하면 예외를 던진다.")
    @Test
    void delete_already_deleted() {

        Gifticon gifticon = Gifticon.builder()
                .name("test")
                .price(1000)
                .category("test")
                .stock(10L)
                .imageUrl("test")
                .description("test")
                .brand("test")
                .expirationPeriod(10)
                .build();

        gifticon.delete();

        assertThat(catchThrowable(gifticon::delete))
                .isInstanceOf(IllegalArgumentException.class);
    }
}