package team.haedal.gifticionfunding.dto.gifticon.response;

import lombok.Builder;
import team.haedal.gifticionfunding.entity.gifticon.Gifticon;

@Builder
public record GifticonDto(
        Long id,
        String name,
        Integer price,
        String imageUrl
) {
    public static GifticonDto from(Gifticon gifticon){
        return GifticonDto.builder()
                .id(gifticon.getId())
                .name(gifticon.getName())
                .price(gifticon.getPrice())
                .imageUrl(gifticon.getImageUrl())
                .build();
    }

}
