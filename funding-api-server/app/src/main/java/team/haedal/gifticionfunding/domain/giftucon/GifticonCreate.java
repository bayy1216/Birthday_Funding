package team.haedal.gifticionfunding.domain.giftucon;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GifticonCreate {
    private final Integer price;
    private final String name;
    private final String category;
    private final String imageUrl;
    private final String description;
    private final String brand;
    private final Integer expirationPeriod;
}
