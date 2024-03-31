package team.haedal.gifticionfunding.dto.gifticon.request;

import team.haedal.gifticionfunding.entity.gifticon.GifticonUpdate;

public record GifticonUpdateRequest(
        Integer price,
        String name,
        String category,
        String imageUrl,
        String description,
        String brand,
        Integer expirationPeriod) {
    public GifticonUpdate toCommand(){
        return GifticonUpdate.builder()
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
