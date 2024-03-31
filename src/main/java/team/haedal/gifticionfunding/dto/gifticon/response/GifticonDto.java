package team.haedal.gifticionfunding.dto.gifticon.response;

public record GifticonDto(
        Long id,
        String name,
        Integer price,
        String imageUrl
) {
}
