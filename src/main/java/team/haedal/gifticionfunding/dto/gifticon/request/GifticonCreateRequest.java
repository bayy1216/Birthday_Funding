package team.haedal.gifticionfunding.dto.gifticon.request;

import team.haedal.gifticionfunding.entity.gifticon.GifticonCreate;

public record GifticonCreateRequest(
        Integer price,
        String name,
        String category,
        String imageUrl,
        String description,
        String brand,
        Integer expirationPeriod
) {
    public GifticonCreate toCommand(){
        return GifticonCreate.builder()
                .price(price)
                .name(name)
                .category(category)
                .imageUrl(imageUrl)
                .description(description)
                .brand(brand)
                .expirationPeriod(expirationPeriod)
                .build();
    }
}
