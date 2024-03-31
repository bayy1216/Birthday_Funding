package team.haedal.gifticionfunding.entity.gifticon;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.haedal.gifticionfunding.entity.common.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Gifticon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length =50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Long stock;

    private String imageUrl;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private Integer expirationPeriod;


    @Builder
    private Gifticon(String name, Integer price, String category, Long stock, String imageUrl, String description,
                     String brand, Integer expirationPeriod) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.description = description;
        this.brand = brand;
        this.expirationPeriod = expirationPeriod;
    }

    public static Gifticon create(GifticonCreate gifticonCreate) {
        return Gifticon.builder()
                .name(gifticonCreate.getName())
                .price(gifticonCreate.getPrice())
                .category(gifticonCreate.getCategory())
                .stock(0L)
                .imageUrl(gifticonCreate.getImageUrl())
                .description(gifticonCreate.getDescription())
                .brand(gifticonCreate.getBrand())
                .expirationPeriod(gifticonCreate.getExpirationPeriod())
                .build();
    }

    public void update(GifticonUpdate gifticonUpdate) {
        this.name = gifticonUpdate.getName();
        this.price = gifticonUpdate.getPrice();
        this.category = gifticonUpdate.getCategory();
        this.imageUrl = gifticonUpdate.getImageUrl();
        this.description = gifticonUpdate.getDescription();
        this.brand = gifticonUpdate.getBrand();
        this.expirationPeriod = gifticonUpdate.getExpirationPeriod();
    }
}
