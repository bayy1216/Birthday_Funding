package team.haedal.gifticionfunding.dto.gifticon.response;

import lombok.Builder;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;

@Builder
public record GifticonDetailDto(
        Long id,
        String name,
        Integer price,
        String imageUrl,
        Long stock,
        String description,
        String category,
        String brand,
        Integer expirationPeriod
) {
    public static GifticonDetailDto from(Gifticon gifticon){
        return GifticonDetailDto.builder()
                .id(gifticon.getId())
                .name(gifticon.getName())
                .price(gifticon.getPrice())
                .imageUrl(gifticon.getImageUrl())
                .stock(gifticon.getStock())
                .description(gifticon.getDescription())
                .category(gifticon.getCategory())
                .brand(gifticon.getBrand())
                .expirationPeriod(gifticon.getExpirationPeriod())
                .build();
    }
}
