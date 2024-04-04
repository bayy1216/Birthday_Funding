package team.haedal.gifticionfunding.dto.gifticon.response;

import lombok.Builder;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;

@Builder
public record GifticonModel(
        Long id,
        String name,
        Integer price,
        String imageUrl
) {
    public static GifticonModel from(Gifticon gifticon){
        return GifticonModel.builder()
                .id(gifticon.getId())
                .name(gifticon.getName())
                .price(gifticon.getPrice())
                .imageUrl(gifticon.getImageUrl())
                .build();
    }

}
