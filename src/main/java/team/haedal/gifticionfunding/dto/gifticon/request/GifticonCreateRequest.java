package team.haedal.gifticionfunding.dto.gifticon.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import team.haedal.gifticionfunding.entity.gifticon.GifticonCreate;

public record GifticonCreateRequest(
        @Min(value = 1,message = "가격은 1원 이상이어야 합니다.")
        Integer price,
        @NotBlank(message = "상품명은 필수 입력 값입니다.")
        String name,
        @NotBlank(message = "카테고리는 필수 입력 값입니다.")
        String category,
        String imageUrl,
        @NotBlank(message = "설명은 필수 입력 값입니다.")
        String description,
        @NotBlank(message = "브랜드는 필수 입력 값입니다.")
        String brand,
        @Min(1)
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
