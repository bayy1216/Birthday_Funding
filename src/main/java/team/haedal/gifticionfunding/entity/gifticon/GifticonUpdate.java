package team.haedal.gifticionfunding.entity.gifticon;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GifticonUpdate {
    private final Integer price;
    private final String name;
    private final String category;
    private final String imageUrl;
    private final String description;
    private final String brand;
    private final Integer expirationPeriod;
}
