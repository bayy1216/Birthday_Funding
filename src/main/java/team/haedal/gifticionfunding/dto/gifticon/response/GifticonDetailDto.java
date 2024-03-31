package team.haedal.gifticionfunding.dto.gifticon.response;

public record GifticonDetailDto(
        Long id,
        String name,
        Integer price,
        String imageUrl,
        Integer stock,
        String description,
        String category,
        String brand,
        Integer expirationDate
) {
}
